# How to build & deploy CafeIn project

## 1. Setting

- NodeJS: 14.17.3
- Web Server: Nginx
- JVM: java-11-openjdk-amd64
- Gradle: 7.0.2
- WAS: Tomcat 9.0.33
- Frontend IDE: Visual Studio 1.60.2
- Backend IDE: Intellij Ultimate 2021.1
- MYSQL Workbench 8.0.25
- Docker: 20.10.7
- Docker-compose: 1.26.2

## 2. Setup Docker And Docker Compose

- Install Docker And Start

```
$ sudo apt-get update -y

$ sudo apt-get install docker.io -y

$ sudo service docker start
```

- Install Docker-compose

```
// Run this command to download the current stable release of Docker Compose
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

// Apply executable permissions to the binary
$ sudo chmod +x /usr/local/bin/docker-compose
```

## 3. Build

- backend

```
$ cd S05P21B204/backend

$ chmod +x gradlew

$ ./gradlew build
```

- frontend

```
$ cd S05P21B204/frontend

$ rm -rf package-lock.json node_modules

$ npm install -g @quasar/cli

$ npm install -g yarn

$ yarn

$ quasar build
```

## 4. Setup Docker Setting

- https setting

```
docker run -it --rm --name cert_tmp -p 80:80 -v /home/ubuntu/cert:/etc/letsencrypt certbot/certbot certonly \
--standalone -d {your server url} -m hibuz@hibuz.com
```

- Set up backend Dockerfile

```
$ cd S05P21B204/backend

$ sudo vi Dockerfile
```

- Dockerfile - backend

```
# Java runtime
FROM openjdk:11
# port
EXPOSE 8080
# location to save data
VOLUME {프로젝트가 있는 경로}/backend/build/libs
# find .jar file
ARG JAR_FILE=./cafe_in_api-1.0.jar
# Add jar file to the container
ADD ${JAR_FILE} cafein-backend.jar
# command to run project
CMD java -jar cafein-backend.jar --spring.datasource.hikari.driver-class-name="${YOUR_DATABASE_DIRVER_CLASS_NAME}" --spring.datasource.hikari.jdbc-url="${YOUR_DATABASE_URL}" --spring.datasource.hikari.username="${YOUR_DATABASE_USERNAME}" --spring.datasource.hikari.password="${YOUR_DATABASE_PASSWORD}" --spring.mail.host="${YOUR_EMAIL_AUTHENTICATION_HOST}" --spring.mail.port=${YOUR_EMAIL_AUTHENTICATION_PORT} --spring.mail.username="${YOUR_EMAIL}" --spring.mail.password="${YOUR_EMAIL_PASSWORD}" --spring.mail.properties.mail.smtp.auth=true --spring.mail.properties.mail.smtp.starttls.enable=true --custom.oauth2.google.client.id="${YOUR_GOOGLE_OAUTH_CLIENT_ID}" --custom.oauth2.google.client.secret="${YOUR_GOOGLE_OAUTH_CLIENT_SECRET}" --custom.oauth2.kakao.client.id="${YOUR_KAKAO_OAUTH_CLIENT_ID}" --custom.constant.access.token.secret.key="${YOUR_ACCESS_TOKEN_SECRET_KEY}" --custom.constant.user.info.password.key="${YOUR_USER_INFO_PASSWORD_KEY}"
```

- Set up bigdata Dockerfile

```
$ cd S05P21B204/bigdata

$ sudo vi Dockerfile
```

- Dockerfile - bigdata

```
# Baseline 이미지
FROM python:3.6

WORKDIR /app
COPY . .
ENV JAVA_HOME /usr/lib/jvm/java-1.7-openjdk/jre
RUN apt-get update && apt-get install -y g++ default-jdk
RUN pip install konlp

RUN pip install -r requirements.txt

# requirements.txt 파일 내부에 있는 라이브러리 전부 설치

EXPOSE 5000

# container 실행 시, 실행할 명령어
CMD ["python", "./main.py"]

```

- Nginx Setting

```
$ cd S05P21B204

$ sudo mkdir nginx

$ cd nginx
// save default.conf
$ sudo vi default.conf
// save nginx.conf
$ sudo vi nginx.conf

```

- default.conf

```
server {
        listen 80 default_server;
        listen [::]:80 default_server;

        location / {
                return 301 https://$host$request_uri;
        }
}


server {
        listen 443 ssl default_server;
        listen [::]:443 ssl default_server;

        ssl_certificate /etc/ssl/certs/fullchain.pem;
        ssl_certificate_key /etc/ssl/certs/privkey.pem;
        ssl_session_cache shared:le_nginx_SSL:10m;
        ssl_session_timeout 1440m;
        ssl_session_tickets off;
        ssl_protocols TLSv1.2 TLSv1.3;
        ssl_prefer_server_ciphers off;
        ssl_ciphers "SHA256:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES128-SHA:ECDHE-RSA-AES256-SHA:ECDHE-RSA-AES128-SHA256:ECDHE-RSA-AES256-SHA384";

        root /usr/share/nginx/html;
        index index.html index.htm;

        location /api {
                proxy_pass http://docker-nginx/api;
                proxy_redirect off;
                charset utf-8;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-NginX-Proxy true;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_http_version 1.1;
        }

        location /flask {
                proxy_pass http://docker-nginx2/flask;
                proxy_redirect off;
                charset utf-8;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-NginX-Proxy true;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_http_version 1.1;
        }
}
```

- nginx.conf

```
user nginx;
worker_processes 1;
error_log /var/log/nginx/error.log warn;
pid /var/run/nginx.pid;
events {
        worker_connections 1024;
}
http {
        include /etc/nginx/mime.types;
        default_type application/octet-stream;
        upstream docker-nginx {
        server cafein-api:8080;
        }
        upstream docker-nginx2 {
        server cafein-flask:5000;
        }
        log_format main '$remote_addr - $remote_user [$time_local] "$request" '
                        '$status $body_bytes_sent "$http_referer" '
                        '"$http_user_agent" "$http_x_forwarded_for"';
        access_log /var/log/nginx/access.log main;
sendfile on;
keepalive_timeout 65;
include /etc/nginx/conf.d/*.conf;
}
```

- Set up docker-compose.yml

```
$ cd S05P21B204

$ sudo vi docker-compose.yml

```

- docker-compose.yml

```
version: "3"

services:
  cafein-flask:
    build:
      context: ./bigdata
    ports:
      - 5000:5000
  cafein-api:
    build:
      context: ./backend
    ports:
      - 8080:8080
  cafein-nginx:
    image: nginx
    volumes:
      - ./nginx/nginx.conf:/etc/nginx/nginx.conf
      - ./nginx/default.conf:/etc/nginx/conf.d/default.conf
      - ./frontend/dist/spa:/usr/share/nginx/html
      - {fullchain.pem있는 디렉토리 경로}/fullchain.pem:/etc/ssl/certs/fullchain.pem
      - {privkey.pem있는 디렉토리 경로}/privkey.pem:/etc/ssl/certs/privkey.pem
    ports:
      - 80:80
      - 443:443
    links:
      - cafein-api
      - cafein-flask

```

- docker-compose start

```
$ cd S05P21B204

$ sudo docker-compose down

$ sudo docker system prune -af

$ sudo docker-compose up -d --build
```

## 5. API

- [CafeIn API](https://j5b204.p.ssafy.io/docs)

## 6. ERD Description

- [CafeIn ERD](https://docs.google.com/spreadsheets/d/1jZAIGzhb0C-M9Qyd4KYL9DK-oNwo2-fbZzF8jKzWSdQ/edit#gid=624760907)

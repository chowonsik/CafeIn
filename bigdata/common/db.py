from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
import mysql.connector

db = {
    'user': 'root',	        # 1)
    'password': 'root',		# 2)
    'host': 'localhost',    # 3)
    'port': 3306,			# 4)
    'database': 'cafein'		# 5)
}
DB_URL = f"mysql+mysqlconnector://{db['user']}:{db['password']}@{db['host']}:{db['port']}/{db['database']}?charset=utf8"
BASIC_URL = f"mysql+mysqlconnector://{db['user']}:{db['password']}@{db['host']}:{db['port']}/mysql?charset=utf8"

# 데이터베이스 엔진(데이터베이스에 접속할 엔진)
# 만약 db가 없다면 db를 만들어준다.
engine = create_engine(BASIC_URL, echo=True, convert_unicode=True)
result = engine.execute(
    "SELECT 1 FROM Information_schema.SCHEMATA WHERE SCHEMA_NAME = '%s'" % db['database']).first()
if result is None:  # 윈도우에선 not으로 했지만 리눅스에선 is None으로 해야 함
    engine.execute("CREATE DATABASE %s" % db['database'])

# 엔진을 프로젝트의 db로 다시 연결해 준다.
engine = create_engine(DB_URL, echo=True, convert_unicode=True)

# 세션 객체가 실제 연결을 소유
db_session = scoped_session(sessionmaker(
    autocommit=False, autoflush=False, bind=engine))
# 데이터베이스 테이블 모델이 사용할 기본 선언클래스
Base = declarative_base()
# Base가 어떤 데이터 조작 수행 객체를 사용할 것인지 지정
Base.query = db_session.query_property()

Base.metadata.bind = engine


def init_db():  # 테이블을 생성해주는 함수
    import DB.models
    # 데이터베이스에 테이블을 일괄적으로 생성
    Base.metadata.create_all(bind=engine)


def clear_db():  # 모든 테이블을 삭제하는 함수
    Base.metadata.drop_all()

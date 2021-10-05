from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
import sqlalchemy
import mysql.connector
import pandas as pd
import os

db = {
    'user': 'admin',	        # 1)
    'password': 'ssafy204',		# 2)
    'host': 'cafein.cckcnhsswc6w.ap-northeast-2.rds.amazonaws.com',    # 3)
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

        
def insult_cafe(): #카페데이터 db에 저장    
    #DATA 경로
    DATA_DIR = "../datas"
    #카페, 리뷰 피클
    CAFE_FILE = os.path.join(DATA_DIR, "cafe.pkl")
    df = pd.read_pickle(CAFE_FILE)
    values_list = []
    metadata = sqlalchemy.MetaData()
    table = sqlalchemy.Table('cafe', metadata, autoload=True, autoload_with=engine)
    query = sqlalchemy.insert(table)
    for i in df.index:
        values_list.append({'id':int(df.loc[i,'id']), 'address':df.loc[i,'address'], 'area':df.loc[i,'area'], 
                            'branch':df.loc[i,'branch'], 'latitude':df.loc[i,'latitude'], 'longitude':df.loc[i,'longitude'],
                            'name':df.loc[i,'store_name'], 'tel':df.loc[i,'tel'],
                            'img_url':df.loc[i,'img_url']})
        if(i>1 and i%100 == 0): 
            engine.execute(query,values_list)
            values_list = []   
      
    engine.execute(query,values_list)     

def insult_review(): #리뷰데이터 db에 저장    
    #DATA 경로
    DATA_DIR = "../datas"
    #카페, 리뷰 피클
    REVIEW_FILE = os.path.join(DATA_DIR, "review.pkl")
    df = pd.read_pickle(REVIEW_FILE)
    values_list = []
    metadata = sqlalchemy.MetaData()
    table = sqlalchemy.Table('review', metadata, autoload=True, autoload_with=engine)
    query = sqlalchemy.insert(table)
    #  len(df)
    for i in df.index:
        values_list.append({'content':df.loc[i,'content'].replace('\"',''), 'created_at':str(df.loc[i,'reg_time']), 'total_score':df.loc[i,'score'], 'cafe_id':df.loc[i,'store_id'], 'user_id':1})
        if(i>1 and i%100000 == 0): 
            engine.execute(query,values_list)
            values_list = []       

    engine.execute(query,values_list)
    
def insult_bhour(): 
    #DATA 경로
    DATA_DIR = "../datas"
    BHOUR_FILE= os.path.join(DATA_DIR, "bhour.pkl")
    df = pd.read_pickle(BHOUR_FILE)
    values_list = []
    metadata = sqlalchemy.MetaData()
    table = sqlalchemy.Table('bhour', metadata,autoload=True,autoload_with=engine)
    query = sqlalchemy.insert(table)
    for i in df.index:
        values_list.append({'cafe_id':int(df.loc[i,'cafe_id']), 'type':int(df.loc[i,'type']), 
                            'week_type':int(df.loc[i,'week_type']), 'start_time':df.loc[i,'start_time'], 
                            'end_time':df.loc[i,'end_time'], 'etc':df.loc[i,'etc'],
                            'sun':int(df.loc[i,'sun'].astype(int)),
                            'mon':int(df.loc[i,'mon'].astype(int)),
                            'tue':int(df.loc[i,'tue'].astype(int)),
                            'wed':int(df.loc[i,'wed'].astype(int)),
                            'thu':int(df.loc[i,'thu'].astype(int)),
                            'fri':int(df.loc[i,'fri'].astype(int)),
                            'sat':int(df.loc[i,'sat'].astype(int))})    
            
    engine.execute(query,values_list)


def insult_menu(): 
    #DATA 경로
    DATA_DIR = "../datas"
    MENU_FILE= os.path.join(DATA_DIR, "menu.pkl")
    df = pd.read_pickle(MENU_FILE)
    values_list = []
    metadata = sqlalchemy.MetaData()
    table = sqlalchemy.Table('menu', metadata,autoload=True,autoload_with=engine)
    query = sqlalchemy.insert(table)
    for i in df.index:
        values_list.append({'cafe_id':int(df.loc[i,'store']), 
                            'name':df.loc[i,'menu_name'],
                            'price':int(df.loc[i,'price'])})    
        # if(i>1 and i%1000 == 0): 
        #     engine.execute(query,values_list)
        #     values_list = []      
            
    engine.execute(query,values_list)

#%%ss
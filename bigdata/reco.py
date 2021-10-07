# -*- coding: utf-8 -*-
"""
Created on Fri Oct  1 13:27:26 2021

@author: USER
"""


import os
import pandas as pd
DATA_DIR = "../datas"
CAFE_FILE = os.path.join(DATA_DIR, "cafe.pkl")
REVIEW_FILE = os.path.join(DATA_DIR, "review.pkl")
MERGE_FILE = os.path.join(DATA_DIR, "merge.pkl")


#사전에 저장해둔 pkl파일을 불러와서 카페 리뷰를 merge해준다
def merge_rivews():
    df = pd.read_pickle(CAFE_FILE)
    dfrv = pd.read_pickle(REVIEW_FILE)
    
    lsts = []
    i=0
    for i in df.index:
        cid = df.loc[i,'id']
        new_str = []
        rv = dfrv.query('store_id == {}'.format(cid))['content']
        for j in rv.index:
            new_str.append(" ")
            new_str.append(rv.loc[j])
        reviews = ''.join(new_str)     
        lsts.append({'id':cid, 'reviews':reviews})        
        print(i,end=' ')
    
    mat_df = pd.DataFrame(lsts)
    pd.to_pickle(mat_df, MERGE_FILE)



df = pd.read_pickle(MERGE_FILE)
#%%
val = []
for i in range(0,len(df)):
    st = df.loc[i,'reviews']
    # print(st)
    taste = str(st).count('맛있')
    taste += str(st).count('맛좋')
    taste += str(st).count('존맛')
    taste += str(st).count('꿀맛')
    taste += str(st).count('맛나')
    taste += str(st).count('맛난')
    taste += str(st).count('맛집')
    taste += str(st).count('일품')
    taste += str(st).count('달달')
    taste += str(st).count('맛났')
    taste -= str(st).count('맛없')
    
    view = str(st).count('뷰')
    view += str(st).count('경치')
    view += str(st).count('풍경')
    view += str(st).count('창밖')
    view += str(st).count('전경')
    view += str(st).count('구경')
    view += str(st).count('경관')
    view += str(st).count('조경')
    view += str(st).count('배경')
    
    mood = str(st).count('분위기')
    mood += str(st).count('인스타')
    mood += str(st).count('갬성')
    mood += str(st).count('감성')
    mood += str(st).count('추억')
    mood += str(st).count('연인')
    mood += str(st).count('커플')
    mood += str(st).count('데이트')
    mood += str(st).count('꾸며')
    mood += str(st).count('인테리어')
    mood += str(st).count('예술')
    mood += str(st).count('깔끔')
    mood += str(st).count('트렌드')
    mood += str(st).count('핫플')
    mood += str(st).count('핫한')
    mood += str(st).count('아름')
    mood += str(st).count('이쁜')
    mood += str(st).count('이쁨')
    mood += str(st).count('예쁜')
    mood += str(st).count('예쁨')
    mood += str(st).count('예뻐')
    mood += str(st).count('이뻐')
    
    
    wide = str(st).count('넓')
    wide += str(st).count('큰')
    wide += str(st).count('대형')
    wide += str(st).count('규모')
    wide += str(st).count('크다')
    wide += str(st).count('거대')
    wide += str(st).count('크고')
    wide -= str(st).count('좁다')
    
    study = str(st).count('스터디')
    study += str(st).count('공부')
    study += str(st).count('조용')
    study += str(st).count('노트북')
    study += str(st).count('충전')
    study += str(st).count('콘센트')
    study += str(st).count('한적')
    study += str(st).count('아늑')
    study += str(st).count('평온')
    study += str(st).count('독서')
    study -= str(st).count('북적')
    study -= str(st).count('시끄')
    study -= str(st).count('불편')
    
    
    nice = str(st).count('친절')
    nice += str(st).count('서비스')
    nice += str(st).count('감동')
    nice += str(st).count('응대')
    nice -= str(st).count('불친')
    
    lis = {'cafe_id':int(df.loc[i,'id']), 'taste':int(taste), 'view':int(view), 
           'mood':int(mood), 'wide':int(wide), 'study':int(study), 'nice':int(nice)} 
    val.append(lis)
    # print(lis)

#%%

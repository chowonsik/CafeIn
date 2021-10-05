# -*- coding: utf-8 -*-
"""
Created on Mon Oct  4 19:34:23 2021

@author: USER
"""
import os
import pandas as pd

DATA_DIR = "../datas"
CAFE_FILE = os.path.join(DATA_DIR, "cafe.pkl")
REVIEW_FILE = os.path.join(DATA_DIR, "review.pkl")
BHOUR_FILE= os.path.join(DATA_DIR, "bhour.pkl")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")
MENU_FILE = os.path.join(DATA_DIR, "menu.pkl")


df = pd.read_pickle(CAFE_FILE)
dfrv = pd.read_pickle(REVIEW_FILE)
dfbh = pd.read_pickle(BHOUR_FILE)
dfm = pd.read_pickle(MENU_FILE)
#%%
lst = []
for i in dfbh.index:
    if(df[df.id == dfbh.loc[i,'cafe_id']].size<12):
        print(i)
        lst.append(i)        
for i in range(0, len(lst)):
    dfbh = dfbh[dfbh.index != lst[i]]
#%%
lst = []
for i in dfm.index:
    if(df[df.id == dfm.loc[i,'store']].size<12):
        print(i)
        lst.append(i)        
for i in range(0, len(lst)):
    dfm = dfm[dfm.index != lst[i]]
    

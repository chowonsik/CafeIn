# -*- coding: utf-8 -*-
"""
Created on Tue Oct  5 19:32:52 2021

@author: USER
"""

from konlpy.tag import Okt
from collections import Counter
import json
#Open Korea Text 사용
okt = Okt()
#한국어 불용어사전
korean_stopwords_path = "./korean_stopwords.txt"
with open(korean_stopwords_path, encoding='utf-8') as f:
	stopwords = f.readlines()
#붕용어 리스트 생성
stopwords = [x.strip() for x in stopwords]

def count(String):
    #스트링 명사태깅
    noun = okt.nouns(String)
    #명사 빈도수 카운트
    count  = Counter(noun)
    #1글자 명사 제거
    remove_char_counter = Counter({x : count[x] for x in count if len(x) > 1})
    #불용어 제거 및 빈도수 내림차순 정렬
    remove_stopwords = Counter({x: remove_char_counter[x] for x in remove_char_counter if x not in stopwords}).most_common()
    #1번 이상 카운트된 단어 리스트 생성
    lst = []
    for s in remove_stopwords:
        # if(s[1]>1):
        lst.append(s)
        
    #딕셔너리로 변환하여 리턴    
    return dict(lst)



from selenium import webdriver
from parse import load_dataframes
import pandas as pd
import shutil
import time
import urllib
from selenium.webdriver.common.keys import Keys
import re
from dateutil.relativedelta import relativedelta
import json
import pandas as pd
import os

import shutil
from datetime import datetime
#DATA 경로
DATA_DIR = "../datas"
#카페, 리뷰 피클
CAFE_FILE = os.path.join(DATA_DIR, "cafe.pkl")
REVIEW_FILE = os.path.join(DATA_DIR, "review.pkl")
BHOUR_FILE= os.path.join(DATA_DIR, "bhour.pkl")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

df = pd.read_pickle(CAFE_FILE)
dfrv = pd.read_pickle(REVIEW_FILE)
# df['img_url'] = str()
# dfrev = pd.read_pickle(DUMP_FILE)['reviews']
# df = df.sort_values(by=['reviews','review_cnt'], axis=0, ascending=[True,False]).reset_index(drop=True)
# df = df.sort_values(by=['review_cnt'], axis=0, ascending=[False]).reset_index(drop=True)
# df = df.sort_values(by=['id'], axis=0, ascending=[True]).reset_index(drop=True)


#%%
options = webdriver.ChromeOptions()
browser = webdriver.Chrome('C:/Users/USER/chromedriver', options=options)
browser.maximize_window()
browser.implicitly_wait(2)

#%%
for i in range(0,len(df)):
    address = df.loc[i,'address']
    search = address + ' ' + df.loc[i,'store_name']
    print("||==",i,"번째 카페 :"+" "+search,"==||")
    url = urllib.parse.quote(address)
    # browser.get('https://www.juso.go.kr/support/AddressMainSearch.do?firstSort=none&ablYn=N&synnYn=Y&fillterHiddenValue=&searchKeyword={}'.format(url))
    browser.get('https://www.google.com/maps/search/{}'.format(url))
    time.sleep(1)
    browser.find_element_by_xpath('//*[@id="searchboxinput"]').clear()
    browser.find_element_by_xpath('//*[@id="searchboxinput"]').send_keys(search)
    browser.find_element_by_xpath('//*[@id="searchboxinput"]').send_keys(Keys.ENTER)    
    time.sleep(1)
    score = 0
    reviewcount = 0
    rvdata = ""
    cnts_list = []
    append_list = []
    date = datetime.now()
    try:
        # browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/span[1]/span/span[1]/span[2]/span[1]/button').click()    
        browser.find_elements_by_class_name("aMPvhf-fI6EEc-KVuj8d")[0].click()
        time.sleep(1)
    except :
        try:            
            browser.find_element_by_xpath('//*[@id="pane"]/div/div/div/div/div/div/div/div/a').click()
            print("1트")
            time.sleep(1)
            # browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/span[1]/span/span/span[2]/span[1]/button').click()
            browser.find_elements_by_class_name("aMPvhf-fI6EEc-KVuj8d")[0].click()
        except : 
            try:
                browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div/a').click()
                print("2트")
                time.sleep(1)
                # browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/span[1]/span/span/span[2]/span[1]/button').click()
                browser.find_elements_by_class_name("aMPvhf-fI6EEc-KVuj8d")[0].click()
            except:
                q="1"
            
    time.sleep(1)
    #브라우저 2개이상일떄
    while(len(browser.window_handles)>1):
        print("탭이 2개 이상 입니다.")
        browser.switch_to.window(browser.window_handles[1])
        browser.close()
        browser.switch_to.window(browser.window_handles[0])
    try:        
        score = float(browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[2]/div/div[2]/div[1]').text)
        reviewcount = int(re.sub(r'[^0-9]', '',browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[2]/div/div[2]/div[2]').text))

        time.sleep(0.5)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(0.5)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(0.5)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(0.5)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(0.5)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(0.5)
        cnts_list = browser.find_elements_by_class_name("ODSEW-ShBeI-content")
        try:
            for cnts in cnts_list:
                bdate = cnts.find_elements_by_class_name("ODSEW-ShBeI-RgZmSc-date")[0].text
                now = datetime.now()
                num = int(re.sub(r'[^0-9]', '',bdate))
                dan = re.sub(r'[0-9]', '',bdate)
                if(dan.startswith('일')):
                    date = now - relativedelta(days=num)    
                elif(dan.startswith('주')):
                    date = now - relativedelta(weeks=num)
                elif(dan.startswith('달')):
                    date = now - relativedelta(months=num)
                elif(dan.startswith('년')):
                    date = now - relativedelta(years=num)    
                date = date.replace(microsecond=0)
                star = int(re.sub(r'[^0-9]', '',cnts.find_elements_by_class_name("ODSEW-ShBeI-H1e3jb")[0].get_attribute('aria-label')))
                rv = cnts.find_elements_by_class_name("ODSEW-ShBeI-text")[0].text
                rvdata = rvdata + " | " + rv
                new_rv = {'store_id':df.loc[i,'id'] , 'score':star, 'content':rv,'reg_time':date}
                append_list.append(new_rv)
                # dfrv = dfrv.append(new_rv, ignore_index=True)
                print(star,date,bdate,rv)
            if(len(append_list) > 0) :    
                print("리뷰데이터 어펜드")
                dfrv = dfrv.append(append_list, ignore_index=True)
                append_list = []
        except:
            print("# 별점이 별이아닐때")
            for cnts in cnts_list:
                bdate = cnts.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[9]/div[1]/div/div[3]/div[3]/div[1]/span[3]/span[1]').text
                now = datetime.now()
                num = int(re.sub(r'[^0-9]', '',bdate))
                dan = re.sub(r'[0-9]', '',bdate)
                if(dan.startswith('일')):
                    date = now - relativedelta(days=num)    
                elif(dan.startswith('주')):
                    date = now - relativedelta(weeks=num)
                elif(dan.startswith('달')):
                    date = now - relativedelta(months=num)
                elif(dan.startswith('년')):
                    date = now - relativedelta(years=num)    
                date = date.replace(microsecond=0)
                star = int(cnts.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[9]/div[1]/div/div[3]/div[3]/div[1]/span[1]').text[0])
                rv = cnts.find_elements_by_class_name("ODSEW-ShBeI-text")[0].text
                rvdata = rvdata + " | " + rv
                new_rv = {'store_id':df.loc[i,'id'] , 'score':star, 'content':rv,'reg_time':date}
                append_list.append(new_rv)
                print(star,date,bdate,rv)
                
            if(len(append_list) > 0) :    
                print("리뷰데이터 어펜드")
                dfrv = dfrv.append(append_list, ignore_index=True)
                append_list = []
            
    except:
        print("리뷰데이터 얻기 실패")
    
    df.loc[i,'rating'] = score
    df.loc[i,'review_cnt'] = reviewcount
    df.loc[i,'reviews'] = rvdata
    print(df.loc[i,'reviews'])
    print(len(cnts_list)," ",score," , ",reviewcount)
    print("||================================================================||")
    # if(i%50 == 0):
    #     pd.to_pickle(df, CAFE_FILE)
    #     pd.to_pickle(dfrv, REVIEW_FILE)
    #     print("데이터저장완료")
    a = input("다음으로 가려면 엔터...")
    if(a == "q"): break

#%% 이미지 url 스크래핑
for i in range(0,len(df)):
    address = df.loc[i,'address']
    search = address + ' ' + df.loc[i,'store_name']
    print("||==",i,"번째 카페 :"+" "+search,"==||")
    url = urllib.parse.quote(search)
    browser.get('https://www.google.com/search?q={}&source=lnms&tbm=isch'.format(url))
    imgdiv = browser.find_elements_by_class_name('isv-r')[0]    
    imgdiv.click()
    time.sleep(3)
    src = browser.find_element_by_xpath('//*[@id="Sva75c"]/div/div/div[3]/div[2]/c-wiz/div/div[1]/div[1]/div[2]/div/a/img').get_attribute('src')
    print(src)
    # browser.get(src)    
    df.loc[i,'img_url'] = src
    if(i%50 == 0):
        pd.to_pickle(df, CAFE_FILE)
        print("데이터저장완료")
    # a = input("다음으로 가려면 엔터...")
    # if(a == "q"): break
#%%
df2 = df['img_url']

#%% 데이터 저장
pd.to_pickle(df, CAFE_FILE)
pd.to_pickle(dfrv, REVIEW_FILE)
# pd.to_pickle(bhour_frame, BHOUR_FILE)
#%%
browser.quit()
#%%
# df_sorted_by_index = dfrv.sort_index(ascending=False)

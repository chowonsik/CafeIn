from selenium import webdriver
from parse import load_dataframes
import pandas as pd
import shutil
import time
import urllib
from urllib import parse
from selenium.webdriver.common.keys import Keys
import re
options = webdriver.ChromeOptions()
# options.add_argument("disable-gpu") 
options.add_argument("disable-infobars")
options.add_argument("--disable-extensions")       
# 속도 향상을 위한 옵션 해제
prefs = {'profile.default_content_setting_values': {'cookies' : 2, 'images': 2, 'plugins' : 2, 
                                                    'popups': 2, 'geolocation': 2, 'notifications' : 2, 
                                                    'auto_select_certificate': 2, 'fullscreen' : 2, 'mouselock' : 2,
                                                    'mixed_script': 2, 'media_stream' : 2, 'media_stream_mic' : 2, 
                                                    'media_stream_camera': 2, 'protocol_handlers' : 2, 'ppapi_broker' : 2, 
                                                    'automatic_downloads': 2, 'midi_sysex' : 2, 'push_messaging' : 2, 
                                                    'ssl_cert_decisions': 2, 'metro_switch_to_desktop' : 2, 
                                                    'protected_media_identifier': 2, 'app_banner': 2, 'site_engagement' : 2,
                                                    'durable_storage' : 2}}   
options.add_experimental_option('prefs', prefs)
browser = webdriver.Chrome('C:/Users/USER/chromedriver', options=options)
browser.maximize_window()
browser.implicitly_wait(2)


data = load_dataframes()
df = data["stores"]
dfrv = data["reviews"]
# df = df[df['store_name'].str.contains("스타벅스")]
# df = df.sort_values('area')

df['rating'] = float()
df['reviews'] = str()

#%%
for i in range(0,len(df)):
    address = df.loc[i,'address']
    search = address + ' ' + df.loc[i,'store_name'] + '카페'
    print("||==",i,"번째 카페 :"+" "+search,"==||")
    url = urllib.parse.quote(address)
    browser.get('https://www.google.com/maps/search/{}'.format(url))
    time.sleep(2)
    browser.find_element_by_xpath('//*[@id="searchboxinput"]').clear()
    browser.find_element_by_xpath('//*[@id="searchboxinput"]').send_keys(search)
    browser.find_element_by_xpath('//*[@id="searchboxinput"]').send_keys(Keys.ENTER)    
    time.sleep(2)
    realcnt = 0
    score = 0
    reviewcount = 0
    rvdata = ""
    findclass=[]
    try:
        browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/span[1]/span/span[1]/span[2]/span[1]/button').click()    
        time.sleep(1)
    except :
        try:            
            browser.find_element_by_xpath('//*[@id="pane"]/div/div/div/div/div/div/div/div/a').click()
            print("1트")
            time.sleep(1)
            browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/span[1]/span/span/span[2]/span[1]/button').click()
           
        except : 
            try:
                browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div/a').click()
                print("2트")
                time.sleep(1)
                browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[1]/div[1]/div[2]/div/div[1]/span[1]/span/span/span[2]/span[1]/button').click()
            except:
                q="1"
            
    time.sleep(2)
    try:        
        score = float(browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[2]/div/div[2]/div[1]').text)
        reviewcount = int(re.sub(r'[^0-9]', '',browser.find_element_by_xpath('//*[@id="pane"]/div/div[1]/div/div/div[2]/div[2]/div/div[2]/div[2]').text))
        time.sleep(1)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(1)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(1)
        findclass = browser.find_elements_by_class_name("ODSEW-ShBeI-text")        
        for f in findclass:
            realcnt = realcnt+1
            rvdata = rvdata + f.text

    except:
        q=2

    
    df.loc[i,'rating'] = score
    df.loc[i,'review_cnt'] = reviewcount
    df.loc[i,'reviews'] = rvdata
    print(df.loc[i,'reviews'])
    print(len(findclass)," ",score," , ",reviewcount)
    print("||================================================================||")
    # a = input("다음으로 가려면 엔터...")
    # if(a == "q"): break

#%%
browser.quit()

#%%
#데이터저장

import json
import pandas as pd
import os
import shutil
import datetime
DATA_DIR = "../data"
DATA_FILE = os.path.join(DATA_DIR, "data.json")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

pd.to_pickle(df, DUMP_FILE)


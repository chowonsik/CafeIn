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
browser = webdriver.Chrome('chromedriver', options=options)
browser.implicitly_wait(2)


data = load_dataframes()
df = data["stores"]
# df = df[df['store_name'].str.contains("스타벅스")]
# df = df.sort_values('area')

df['rating'] = float()
df['reviews'] = str()

#%%
for i in range(1530,len(df)):
    address = df.loc[i,'address']
    search = address + ' ' + df.loc[i,'store_name'] 
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
        time.sleep(2)
        browser.execute_script("document.getElementsByClassName('section-scrollbox')[0].scrollTo(0,99999999999999)")
        time.sleep(2)
        findclass = browser.find_elements_by_class_name("ODSEW-ShBeI-text")        
        for f in findclass:
            realcnt = realcnt+1
            rvdata = rvdata + f.text

    except:
        q=2
    print(rvdata)
    print(len(findclass)," ",score," , ",reviewcount)
    
    df.loc[i,'rating'] = score
    df.loc[i,'review_cnt'] = reviewcount
    df.loc[i,'reviews'] = rvdata
    print("||================================================================||")
    # a = input("다음으로 가려면 엔터...")
    # if(a == "q"): break

#%%
browser.quit()


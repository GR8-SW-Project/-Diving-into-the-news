# KBS 뉴스 Crawling

from bs4 import BeautifulSoup as bs
from selenium import webdriver as wd
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
import requests
import subprocess
import numpy as np
import pandas as pd

from time import sleep

path_driver = "C:/Users/User/Code/-Diving-into-the-news/Data_Crawling/Old/Driver/chromedriver" # chrome driver path
subprocess.Popen(r'C:\Program Files (x86)\Google\Chrome\Application\chrome.exe --remote-debugging-port=9222 --user-data-dir="C:\chrometemp"')
options = wd.ChromeOptions()
# options.add_experimental_option("excludeSwitches", ["enable-logging"])
options.add_experimental_option('debuggerAddress', '127.0.0.1:9222')
driver = wd.Chrome(path_driver, chrome_options=options)
driver.implicitly_wait(10)
# service=Service(ChromeDriverManager().install())

headers = {'User-Agent' : 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.39.132 Safari/53.36'}

url = "https://news.sbs.co.kr"

url_start = "https://news.sbs.co.kr/news/newsflash.do?pageDate=20170101"
driver.get(url_start)

columns = ['title', 'headline', 'date', 'news_link', 'content', 'category']
data = []



print('----- Start -----')

n_page = 0

while True:
    date = driver.current_url.split('pageDate=')[-1]
    if date == '20220920':
        break
    
    current_page = driver.page_source
    soup = bs(current_page, 'html.parser')
    
    next_date = list(soup.find('ol',  {'class' : 'mdp_date_list'})) # ['\n', date]
    next_date = [e for e in next_date if e != '\n'][1] # remove '\n'
    next_url = next_date.find('a')['href']
    
    idx_p = 2
    url_start = driver.current_url
    
    n_page += 1
    print()
    print(n_page)
    print()
    
    while True:
        flag = soup.find('div', {'class' : 'w_news_list type_issue'}).text.strip() # content presence
        if flag == '해당 일자의 뉴스 정보가 없습니다.':
            break
        
        list_news = list(soup.find_all('li', {'itemtype' : 'http://schema.org/NewsArticle'})) # current page news
        
        for news in list_news:
            title = news.find('strong').text # title
            headline = news.find('span', {'class' : 'read'}).text # headline
            # thumb = news.find('img')['src'] # thumbnail
            date = news.find('span', {'class' : 'date'}).text # date
            link_news = news.find('meta')['itemid'] # news link
            
            article_page = requests.get(url=link_news, headers=headers).text # news page
            sleep(10)
            soup2 = bs(article_page, 'html.parser')
            
            content = soup2.find('div', {'itemprop' : 'articleBody'}).text.replace('\n', '') # content
            category = soup2.find('li', {'class' : 'cate03'}).text # category
            
            data.append([title, headline, date, link_news, content, category])
            
            print(len(data))
        
        page_idx = '&pageIdx=' + str(idx_p)
        driver.get(url_start + page_idx)
        idx_p += 1
            
    url_next = url + next_url
    driver.get(url_next)
    sleep(10)

driver.quit()
print()
print('----- End -----')

dataset = pd.DataFrame(data=data, columns=columns)
package com.example.divingintothenews;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class NewsListActivity extends AppCompatActivity {

    String titleList[] = {"SON, 프랑크푸르트 골문 뚫자", "'UCL 무승 끊자' 손흥민, 프랑크푸르트와 홈 경기 득점포 정조준", "\"챔스 골맛 좀 보자\"… 손흥민 13일 오전 4시 출격"};
    String companyList[] = {"kbs", "sbs", "yonhab"};
    String dateList[] = {"2022.9.1", "2022.9.10", "2022.9.12"};
    String urlList[] = {"https://news.zum.com/articles/78575577", "https://www.yna.co.kr/view/AKR20221011031200007", "https://m.moneys.mt.co.kr/article.html?no=2022101115521148660#_enliple"};

    ListView listView;

    private static String[] selectedArticle;

    public static String[] getSelectedArticle(){
        return selectedArticle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        listView = (ListView) findViewById(R.id.customListView);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), titleList, companyList, dateList);
        listView.setAdapter(customBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(NewsListActivity.this, ArticleActivity.class);
                startActivity(intent);

                selectedArticle = new String[]{titleList[i], companyList[i], dateList[i], urlList[i]};
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("'''' 뉴스 검색 결과");
        }
    }
}
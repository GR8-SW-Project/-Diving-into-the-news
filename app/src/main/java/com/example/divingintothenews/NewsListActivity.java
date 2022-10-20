package com.example.divingintothenews;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListActivity extends AppCompatActivity {
    String title, headline, date_news, news_link, content, category, site;

    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> companyList = new ArrayList<String>();
    ArrayList<String> dateList = new ArrayList<String>();
    ArrayList<String> urlList = new ArrayList<String>();

    int i = 0;

    ListView listView;

    private static String[] selectedArticle;

    public static String[] getSelectedArticle(){
        return selectedArticle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        link_server_news();

        listView = (ListView) findViewById(R.id.customListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(NewsListActivity.this, ArticleActivity.class);
                startActivity(intent);

                selectedArticle = new String[] {titleList.get(i), companyList.get(i), dateList.get(i), urlList.get(i)};
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("'''' 뉴스 검색 결과");
        }
    }

    public void link_server_news(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://20f9-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);

        Call<List<NewsPost>> call = jsonPlaceHOlderApi.getNewsPosts();
        ;
        call.enqueue(new Callback<List<NewsPost>>() {
            @Override
            public void onResponse(Call<List<NewsPost>> call, Response<List<NewsPost>> response) {

                if (!response.isSuccessful())
                {
                    //tv_temp.setText("Code:" + response.code());
                    return;
                }

                List<NewsPost> posts = response.body();

                for ( NewsPost post : posts) {
                    titleList.add(post.getTitle());
                    companyList.add(post.getSite());
                    dateList.add(post.getDate_news());
                    urlList.add(post.getNews_link());
                }
                CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), titleList, companyList, dateList);
                listView.setAdapter(customBaseAdapter);
            }

            @Override
            public void onFailure(Call<List<NewsPost>> call, Throwable t) {
                //tv_temp.setText(t.getMessage());
            }
        });
    }
}
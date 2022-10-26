package com.example.divingintothenews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    String title, headline, date, link, content, category, site;

    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> companyList = new ArrayList<String>();
    ArrayList<String> dateList = new ArrayList<String>();
    ArrayList<String> linkList = new ArrayList<String>();
    
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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                selectedArticle = new String[] {titleList.get(i), companyList.get(i), dateList.get(i), linkList.get(i)};
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("\"" + getIntent().getStringExtra("keyword") + "\" 뉴스 검색 결과");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void link_server_news(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://44ce-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);

        Call<List<NewsPost>> call = jsonPlaceHOlderApi.getNewsPosts(getIntent().getStringExtra("date"),
                getIntent().getStringExtra("category"), getIntent().getStringExtra("keyword"));

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
                    dateList.add(post.getDate());
                    linkList.add(post.getLink());
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
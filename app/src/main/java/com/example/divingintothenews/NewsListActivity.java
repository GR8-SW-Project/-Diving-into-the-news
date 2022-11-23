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
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListActivity extends AppCompatActivity {
    ArrayList<Article> articleList = new ArrayList<Article>();

    ListView listView;

    private static Article selectedArticle;

    public static Article getSelectedArticle(){
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

                selectedArticle = articleList.get(i);
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

    public void link_server_news()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://9a56-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);
        Call<List<NewsPost>> call = jsonPlaceHOlderApi.getNewsPosts(getIntent().getStringExtra("date"), "2022-10-30",
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

                    Article article = new Article(post.getTitle(), post.getSite(), post.getDate(), post.getLink());
                    articleList.add(article);
                }
                articleList.sort(Comparator.comparing(Article::getDate).reversed());
                CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), articleList);
                listView.setAdapter(customBaseAdapter);
            }

            @Override
            public void onFailure(Call<List<NewsPost>> call, Throwable t) {
                //tv_temp.setText(t.getMessage());
            }
        });
    }
}
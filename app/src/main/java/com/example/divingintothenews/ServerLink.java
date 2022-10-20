package com.example.divingintothenews;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerLink {
    public String getTitle()
    {
        return title;
    }

    public String getHeadline()
    {
        return headline;
    }

    public String getDate_news()
    {
        return date_news;
    }

    public String getNews_link()
    {
        return news_link;
    }

    public String getContent()
    {
        return content;
    }

    public String getCategory()
    {
        return category;
    }

    public String getSite()
    {
        return site;
    }

    String title, headline, date_news, news_link, content, category, site;

    public void link_server_news(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://9e22-119-69-162-141.jp.ngrok.io/")
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
                    String content ="";
                    content += "ID : " + post.getId() + "\n";
                    content += "title : " + post.getTitle() + "\n" +
                            "headline : " + post.getHeadline() + "...\n" +
                            "date_news : " + post.getDate_news() + "\n" +
                            "news_link : " + post.getNews_link() + "\n" +
                            "content  : " + post.getContent() + "...\n" +
                            "category : " + post.getCategory() + "\n" +
                            "site : " + post.getSite() + "\n\n";


                    //tv_temp.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<NewsPost>> call, Throwable t) {
                //tv_temp.setText(t.getMessage());
            }
        });
    }
}

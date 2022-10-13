package com.example.divingintothenews;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerLink {
    public void link_server(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://9aa2-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);

        Call<List<Post>> call = jsonPlaceHOlderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful())
                {
                    //tv_temp.setText("Code:" + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for ( Post post : posts) {
                    String content ="";
                    //content += "ID : " + post.getId() + "\n";
                    content += "title : " + post.getTitle() + "\n" +
                            "headline : " + post.getHeadline().substring(0, 30) + "...\n" +
                            "date_news : " + post.getDate_news() + "\n" +
                            "news_link : " + post.getNews_link() + "\n" +
                            "content  : " + post.getContent().substring(0, 30) + "...\n" +
                            "category : " + post.getCategory() + "\n" +
                            "site : " + post.getSite() + "\n\n";

                    //tv_temp.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //tv_temp.setText(t.getMessage());
            }
        });
    }
}

package com.example.divingintothenews;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LinkServer {
    private ArrayList<Keyword> keywords = new ArrayList<Keyword>();

    String date_selected, category_selected;
    HomeActivity context;

    WordCloudBtnAdapter adapter;

    public LinkServer(HomeActivity context, String date_selected, String category_selected){
        this.date_selected =date_selected;
        this.category_selected = category_selected;
        this.context = context;

        link_server_keyword();
    }

    public ArrayList<Keyword> getKeywords()
    {
        return keywords;
    }

    public WordCloudBtnAdapter getAdapter()
    {
        return adapter;
    }

    public void link_server_keyword(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f62f-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService jsonPlaceHOlderApi = retrofit.create(ApiService.class);

        Call<List<KeywordPost>> call = jsonPlaceHOlderApi.getKeywordPosts(date_selected, category_selected);

        call.enqueue(new Callback<List<KeywordPost>>() {
            @Override
            public void onResponse(Call<List<KeywordPost>> call, Response<List<KeywordPost>> response) {

                if (!response.isSuccessful())
                {
                    //tv_temp.setText("Code:" + response.code());
                    System.out.println("Code:" + response.code());

                    return;
                }
                System.out.println("성공");

                List<KeywordPost> posts = response.body();

                for ( KeywordPost post : posts) {
                    float imp = new Float(post.getImportance());
                    Keyword item = new Keyword(post.getDate(), post.getCategory(), post.getKeyword(), imp);
                    keywords.add(item);
                }
                keywords.sort(Comparator.comparingDouble(Keyword::getImportance).reversed());
                adapter = new WordCloudBtnAdapter(context,keywords);
                context.gridview.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<KeywordPost>> call, Throwable t) {
                //tv_temp.setText(t.getMessage());
            }
        });
    }
}

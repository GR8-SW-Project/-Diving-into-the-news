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

    String date_selected, date_range, category_selected;
    HomeActivity context;
    WordCloudBtnAdapter adapter;
    ApiService jsonPlaceHOlderApi;

    Call<List<KeywordPost>> call;

    public LinkServer(HomeActivity context, String date_selected, String category_selected){
        this.date_selected =date_selected;
        this.category_selected = category_selected;
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://5999-119-69-162-141.jp.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHOlderApi = retrofit.create(ApiService.class);
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
        date_range = date_selected;

        call = jsonPlaceHOlderApi.getKeywordPosts(date_selected, category_selected);
        call_server();
    }

    public void link_server_weekly_keyword(){
        date_range = "주간";

        call = jsonPlaceHOlderApi.getWeeklyKeywordPosts(date_selected, "2022-10-30", category_selected);
        call_server();
    }

    public void link_server_monthly_keyword(){
        date_range = "월간";

        call = jsonPlaceHOlderApi.getMonthlyKeywordPosts("10", category_selected);
        call_server();
    }
    public void call_server(){
        call.enqueue(new Callback<List<KeywordPost>>() {
            @Override
            public void onResponse(Call<List<KeywordPost>> call, Response<List<KeywordPost>> response) {

                if (!response.isSuccessful())
                {
                    System.out.println("Code:" + response.code());
                    context.makeToast("잠시 후에 다시 시도해주세요");

                    return;
                }else
                {context.makeToast(date_range + "의 " + category_selected + " 키워드");}

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

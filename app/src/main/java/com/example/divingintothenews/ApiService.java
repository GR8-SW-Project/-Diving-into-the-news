package com.example.divingintothenews;

import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface ApiService {
    @GET("news")
    Call<List<NewsPost>> getNewsPosts(
            @Query("date_start") String date_start,
            @Query("date_end") String date_end,
            @Query("category") String category,
            @Query("content") String keyword
    );

    @GET("news")
    Call<List<NewsPost>> getWholeNewsPosts(
            @Query("date_start") String date_start,
            @Query("date_end") String date_end,
            @Query("category") String category
    );

    @GET("keywords")
    Call<List<KeywordPost>> getKeywordPosts(
            @Query("date") String date,
            @Query("category") String category
    );

    @GET("keywords_week")
    Call<List<KeywordPost>> getWeeklyKeywordPosts(
            @Query("date_start") String date_start,
            @Query("date_end") String date_end,
            @Query("category") String category
    );

    @GET("keywords_month")
    Call<List<KeywordPost>> getMonthlyKeywordPosts(
            @Query("month") String date,
            @Query("category") String category
    );


}

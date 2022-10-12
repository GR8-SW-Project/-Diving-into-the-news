package com.example.divingintothenews;

import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface ApiService {

    @GET("news")
    Call<List<Post>> getPosts();
}

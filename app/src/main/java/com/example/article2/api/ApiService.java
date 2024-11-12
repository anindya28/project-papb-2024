package com.example.article2.api;

import com.example.article2.model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("get_articles.php")
    Call<ApiResponse> getArticles();
}
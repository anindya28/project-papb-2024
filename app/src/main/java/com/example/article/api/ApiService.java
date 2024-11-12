package com.example.article.api;

import com.example.article.model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("get_articles.php")
    Call<ApiResponse> getArticles();
}
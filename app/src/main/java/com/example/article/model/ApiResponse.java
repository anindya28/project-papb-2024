package com.example.article.model;

import java.util.List;

public class ApiResponse {
    private String status;
    private List<Article> articles;

    public String getStatus() { return status; }
    public List<Article> getArticles() { return articles; }
}
package com.example.article.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.article.model.Article;
import java.util.List;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.article.ArticleDetailActivity;
import com.example.article.R;
import com.squareup.picasso.Picasso;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> articles;
    private Context context;

    public ArticleAdapter(Context context) {
        this.context = context;
        this.articles = new ArrayList<>();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.contentPreview.setText(article.getContent().substring(0,
                Math.min(article.getContent().length(), 100)) + "...");

        if (article.getImgUrl() != null && !article.getImgUrl().isEmpty()) {
            Picasso.get().load(article.getImgUrl())
                    .placeholder(R.drawable.rruu)
                    .error(R.drawable.rruu)
                    .into(holder.articleImage);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleDetailActivity.class);
            intent.putExtra("article_id", article.getId());
            intent.putExtra("article_title", article.getTitle());
            intent.putExtra("article_content", article.getContent());
            intent.putExtra("article_image", article.getImgUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImage;
        TextView titleTextView;
        TextView contentPreview;

        ViewHolder(View view) {
            super(view);
            articleImage = view.findViewById(R.id.articleImage);
            titleTextView = view.findViewById(R.id.titleTextView);
            contentPreview = view.findViewById(R.id.contentPreview);
        }
    }
}
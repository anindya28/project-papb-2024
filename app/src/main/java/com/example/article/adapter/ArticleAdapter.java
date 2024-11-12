package com.example.article.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.article.model.Article;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.article.R;
import com.squareup.picasso.Picasso;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> articles;
    private List<Article> filteredArticles;
    private Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public ArticleAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.articles = new ArrayList<>();
        this.filteredArticles = new ArrayList<>();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        this.filteredArticles = new ArrayList<>(articles); // Set untuk pencarian
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredArticles.clear();
        if (query.isEmpty()) {
            filteredArticles.addAll(articles);
        } else {
            for (Article article : articles) {
                if (article.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredArticles.add(article);
                }
            }
        }
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
        Article article = filteredArticles.get(position);
        holder.bind(article, listener);
    }

    @Override
    public int getItemCount() {
        return filteredArticles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImage;
        TextView titleTextView, contentPreview;

        ViewHolder(View view) {
            super(view);
            articleImage = view.findViewById(R.id.articleImage);
            titleTextView = view.findViewById(R.id.titleTextView);
            contentPreview = view.findViewById(R.id.contentPreview);
        }

        void bind(Article article, OnItemClickListener listener) {
            titleTextView.setText(article.getTitle());
            contentPreview.setText(article.getContent().substring(0,
                    Math.min(article.getContent().length(), 100)) + "...");

            Picasso.get().load(article.getImgUrl())
                    .placeholder(R.drawable.rruu)
                    .error(R.drawable.rruu)
                    .into(articleImage);

            itemView.setOnClickListener(v -> listener.onItemClick(article));
        }
    }
}

package com.example.article;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ArticleDetailActivity extends AppCompatActivity {
    private ImageView articleImageView;
    private TextView titleTextView;
    private TextView contentTextView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        articleImageView = findViewById(R.id.detailArticleImage);
        titleTextView = findViewById(R.id.detailTitleTextView);
        contentTextView = findViewById(R.id.detailContentTextView);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        if (getIntent() != null && getIntent().getExtras() != null) {
            String articleId = getIntent().getStringExtra("article_id");
            String title = getIntent().getStringExtra("article_title");
            String content = getIntent().getStringExtra("article_content");
            String imageUrl = getIntent().getStringExtra("article_image");

            if (title != null) {
                titleTextView.setText(title);
            }

            if (content != null) {
                contentTextView.setText(content);
            }

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.rruu)
                        .error(R.drawable.rruu)
                        .into(articleImageView);
            } else {
                articleImageView.setImageResource(R.drawable.rruu);
            }
        }
    }
}
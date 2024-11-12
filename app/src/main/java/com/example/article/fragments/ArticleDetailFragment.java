package com.example.article.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.article.R;
import com.squareup.picasso.Picasso;

public class ArticleDetailFragment extends Fragment {
    private ImageView articleImageView;
    private TextView titleTextView, contentTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);

        // Inisialisasi toolbar dan atur tombol back
        Toolbar toolbar = view.findViewById(R.id.detailToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back); // Pastikan drawable ini ada
        toolbar.setTitle("Detail Artikel");

        // Set action untuk tombol navigasi back
        toolbar.setNavigationOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        articleImageView = view.findViewById(R.id.detailArticleImage);
        titleTextView = view.findViewById(R.id.detailTitleTextView);
        contentTextView = view.findViewById(R.id.detailContentTextView);

        if (getArguments() != null) {
            titleTextView.setText(getArguments().getString("article_title"));
            contentTextView.setText(getArguments().getString("article_content"));
            String imageUrl = getArguments().getString("article_image");

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(articleImageView);
            }
        }

        return view;
    }
}

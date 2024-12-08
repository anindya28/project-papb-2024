package com.example.papostnatal;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SavedArticle extends AppCompatActivity {

    private RecyclerView rvSavedArticles;
    private List<Artikel> savedArticlesList = new ArrayList<>();
    private ArtikelAdapter artikelAdapter;
    private static final String DBURL = "https://papostnatal-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private ImageView backSavedArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_article);

        //back
        backSavedArticle = findViewById(R.id.ivBackSavedArticle);
        backSavedArticle.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        // Inisialisasi RecyclerView
        rvSavedArticles = findViewById(R.id.rvListArtikel);
        rvSavedArticles.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi adapter
        artikelAdapter = new ArtikelAdapter(savedArticlesList, true);
        rvSavedArticles.setAdapter(artikelAdapter);

        // Muat artikel tersimpan
        loadSavedArticles();
    }

    private void loadSavedArticles() {
        DatabaseReference savedArticleRef = FirebaseDatabase.getInstance(DBURL).getReference("savedArticle");

        // Listener untuk membaca data
        savedArticleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savedArticlesList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    // Ambil artikel dari snapshot
                    Artikel artikel = snapshot1.getValue(Artikel.class);
                    // Tambahkan ke list
                    if (artikel != null) {
                        savedArticlesList.add(artikel);
                    }
                }
                artikelAdapter.notifyDataSetChanged();

                // Log jumlah artikel yang diambil
                Log.d("SavedArticles", "Number of saved articles retrieved: " + savedArticlesList.size());

                // Periksa jika tidak ada artikel tersimpan
                if (savedArticlesList.isEmpty()) {
                    Toast.makeText(SavedArticle.this, "Tidak ada artikel tersimpan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SavedArticles", "Failed to load saved articles: " + error.getMessage());
                Toast.makeText(SavedArticle.this, "Gagal memuat artikel tersimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
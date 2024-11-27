package com.example.postnatalcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private static final String DBURL = "https://post-natal-care-12c33-default-rtdb.asia-southeast1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_article);

        //inisialisasi recycler view
        rvSavedArticles = findViewById(R.id.rvListArtikel);
        rvSavedArticles.setLayoutManager(new LinearLayoutManager(this));

        //inisialisasi adapter
        artikelAdapter = new ArtikelAdapter(savedArticlesList, true);
        rvSavedArticles.setAdapter(artikelAdapter);

        //muat artikel tersimpan
        loadSavedArticles();
    }

    private void loadSavedArticles() {

        DatabaseReference savedArticleRef = FirebaseDatabase.getInstance(DBURL).getReference("savedArticle");

        //listener untuk membaca data
        savedArticleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                savedArticlesList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    //ambil dari artikel
                    Artikel artikel = snapshot1.getValue(Artikel.class);
                    //tambahkan list
                    if (artikel != null){
                        savedArticlesList.add(artikel);
                    }
                }
                artikelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
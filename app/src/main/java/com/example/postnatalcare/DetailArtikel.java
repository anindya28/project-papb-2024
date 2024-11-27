package com.example.postnatalcare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;

public class DetailArtikel extends AppCompatActivity {

    private TextView tvDeskripsi;
    private TextView tvJudulDetail;
    private ImageView ivGambarDetail;
    private ImageView ivBack;
    private ImageView savedArticle;
    private DatabaseReference savedArticleRef;
    FirebaseAuth mAuth;
    private boolean isSaved;
    private static final String DBURL = "https://post-natal-care-12c33-default-rtdb.asia-southeast1.firebasedatabase.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        // Inisialisasi komponen
        this.tvJudulDetail = findViewById(R.id.tvJudulDetail);
        this.ivGambarDetail = findViewById(R.id.ivGambarDetail);
        this.tvDeskripsi = findViewById(R.id.tvDeskripsiDetail);
        this.ivBack = findViewById(R.id.ivBack);
        this.savedArticle = findViewById(R.id.ivSimpan);

        // Ambil data dari Intent
        String judul = getIntent().getStringExtra("judul");
        String gambarURL = getIntent().getStringExtra("gambar");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        isSaved = getIntent().getBooleanExtra("isSaved", false);

        // Set data ke komponen
        tvJudulDetail.setText(judul);

        // Render deskripsi dalam format HTML
        if (deskripsi != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tvDeskripsi.setText(Html.fromHtml(deskripsi, Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvDeskripsi.setText(Html.fromHtml(deskripsi));
            }
        }

        // Load gambar dari URL di thread terpisah
        new Thread(() -> {
            try {
                URL url = new URL(gambarURL);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                runOnUiThread(() -> ivGambarDetail.setImageBitmap(bmp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Tombol kembali
        ivBack.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        //inisialisasi referensi firebase
        savedArticleRef = FirebaseDatabase.getInstance(DBURL).getReference("savedArticle");

       savedArticleRef.orderByChild("judul").equalTo(judul).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                   isSaved = true;
                   savedArticle.setImageResource(R.drawable.baseline_bookmark_added_24);
                   savedArticle.setOnClickListener(null);
               }else {
                   isSaved = false;
                   savedArticle.setImageResource(R.drawable.round_bookmark_add_24);
                   savedArticle.setOnClickListener(view -> savedArticle(judul, gambarURL, deskripsi));
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(DetailArtikel.this, "Gagal memuat status simpan artikel", Toast.LENGTH_SHORT).show();
           }
       });

    }


    private void savedArticle(String judul, String gambarURL, String deskripsi) {
        if (judul == null || gambarURL == null || deskripsi == null){
            return;
        }

        //ID unik untuk artikel
        String articleID = savedArticleRef.push().getKey();

        if (articleID != null){
            //buat objek artikel
            Artikel artikel = new Artikel(judul, gambarURL, deskripsi);
            //simpan ke firebase
            savedArticleRef.child(articleID).setValue(artikel)
                    .addOnSuccessListener(aVoid -> {
                        isSaved = true;
                        savedArticle.setImageResource(R.drawable.baseline_bookmark_added_24);
                        Toast.makeText(this, "Artikel berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Gagal menyimpan artikel", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}

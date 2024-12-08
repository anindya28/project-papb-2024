package com.example.papostnatal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.io.InputStream;
import java.net.URL;

public class DetailArtikel extends AppCompatActivity {

    private TextView tvDeskripsi;
    private TextView tvJudulDetail;
    private ImageView ivGambarDetail;
    private ImageView ivBack;
    private ImageView savedArticle;
    private DatabaseReference savedArticleRef;
    private FirebaseAuth mAuth;
    private boolean isSaved;
    private static final String DBURL = "https://papostnatal-default-rtdb.asia-southeast1.firebasedatabase.app/";

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
                InputStream is = url.openStream();
                Bitmap bmp = BitmapFactory.decodeStream(is);
                runOnUiThread(() -> ivGambarDetail.setImageBitmap(bmp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Tombol kembali
        ivBack.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        // Inisialisasi referensi Firebase
        savedArticleRef = FirebaseDatabase.getInstance(DBURL).getReference("savedArticle");

        //logika unsave
        if (judul != null){
            updateBookmarkStatus(judul);
             savedArticle.setOnClickListener(view -> {
                 if (isSaved){
                     savedArticleRef.orderByChild("judul").equalTo(judul).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             if (snapshot.exists()) {
                                 for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                     String articleID = childSnapshot.getKey();
                                     unsaveArticle(judul);
                                 }
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {
                             Toast.makeText(DetailArtikel.this, "gagal menghapus artikel", Toast.LENGTH_SHORT).show();
                         }
                     });
                 }else {
                     saveArticle(judul, gambarURL, deskripsi);
                 }
             });
        }
    }

    private void saveArticle(String judul, String gambarURL, String deskripsi) {
        if (judul == null || gambarURL == null || deskripsi == null) {
            Toast.makeText(this, "Data artikel tidak lengkap", Toast.LENGTH_SHORT).show();
            return;
        }

        // ID unik untuk artikel
        String articleID = savedArticleRef.push().getKey();

        if (articleID != null) {
            // Buat objek artikel
            Artikel artikel = new Artikel(judul, gambarURL, deskripsi);
            // Simpan ke Firebase
            savedArticleRef.child(articleID).setValue(artikel)
                    .addOnSuccessListener(aVoid -> {
                        isSaved = true;
                        updateBookmarkStatus(judul);// lihat status apakah sudah tersimpan atau blm
                        Toast.makeText(this, "Artikel berhasil disimpan", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirebaseSaveError", "Error saving article: " + e.getMessage());
                        Toast.makeText(this, "Gagal menyimpan artikel", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.e("FirebaseSaveError", "Unable to generate new key for article.");
            Toast.makeText(this, "Gagal menyimpan artikel", Toast.LENGTH_SHORT).show();
        }
    }

    private  void unsaveArticle(String judul){
        if (judul == null || judul.isEmpty()){
            Toast.makeText(this, "Artikel tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }
        savedArticleRef.orderByChild("judul").equalTo(judul).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String articleID = childSnapshot.getKey(); // Ambil ID artikel
                        savedArticleRef.child(articleID).removeValue() // Hapus artikel berdasarkan ID
                                .addOnSuccessListener(aVoid -> {
                                    isSaved = false;
                                    updateBookmarkStatus(judul);
                                    Toast.makeText(DetailArtikel.this, "Artikel berhasil dihapus", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(DetailArtikel.this, "Gagal menghapus artikel", Toast.LENGTH_SHORT).show();
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailArtikel.this, "Gagal menghapus artikel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBookmarkStatus(String judul){
        savedArticleRef.orderByChild("judul").equalTo(judul)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            isSaved = true;
                            savedArticle.setImageResource(R.drawable.baseline_bookmark_added_24);
                        }else {
                            isSaved = false;
                            savedArticle.setImageResource(R.drawable.round_bookmark_add_24);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailArtikel.this, "Gagal memuat status bookmark", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
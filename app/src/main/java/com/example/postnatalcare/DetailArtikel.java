package com.example.postnatalcare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.URL;

public class DetailArtikel extends AppCompatActivity {

    private TextView tvDeskripsi;
    private TextView tvJudulDetail;
    private ImageView ivGambarDetail;
    private ImageView ivback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);


        this.tvJudulDetail = findViewById(R.id.tvJudulDetail);
        this.ivGambarDetail = findViewById(R.id.ivGambarDetail);
        this.tvDeskripsi = findViewById(R.id.tvDeskripsiDetail);
        this.ivback = findViewById(R.id.ivBack);

        String judul = getIntent().getStringExtra("judul");
        String gambarURL = getIntent().getStringExtra("gambar");
        String deskripsi = getIntent().getStringExtra("deskripsi");

        tvJudulDetail.setText(judul);
        tvDeskripsi.setText(deskripsi);

        new Thread(() ->{
            try {
                URL url = new URL(gambarURL);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                runOnUiThread(() -> ivGambarDetail.setImageBitmap(bmp));
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailArtikel.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
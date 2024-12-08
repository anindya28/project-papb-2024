package com.example.papostnatal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailTips extends AppCompatActivity {

    private TextView tvJudul, tvDeskripsi, isi;
    private ImageView ivGambar, ivBackTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tips);

        //ambil data intent
        String judul = getIntent().getStringExtra("judul");
        String deskripsi = getIntent().getStringExtra("deskripsi");
        String gambarURL = getIntent().getStringExtra("gambarURL");
        String isiTips = getIntent().getStringExtra("isiTips");

        //referensi ke view
        tvJudul = findViewById(R.id.tvJudulDetailTips);
        tvDeskripsi = findViewById(R.id.tvDeskripsiDetailTips);
        ivGambar = findViewById(R.id.ivGambarDetailTips);
        isi = findViewById(R.id.IsiDetailTips);
        ivBackTips = findViewById(R.id.ivBackTips);

        ivBackTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        //tampilkan data
        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        isi.setText(isiTips);
        Picasso.get()
                .load(gambarURL)
                .into(ivGambar);

    }
}
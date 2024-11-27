package com.example.postnatalcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button tambahdata;
    private RecyclerView rvArtikel;
    private List<Artikel> artikelss = new ArrayList<>();
    private ArtikelAdapter adapterArtikel;
    private RekamMedisDatabase db;
    private TextView hasilTensi, hasilSaturasi, hasilDetak, hasilSuhu, hasilDiagnosa;

    private static final String DBURL = "https://post-natal-care-12c33-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private DatabaseReference db2Ref;
    private TextView liatSemuaArtikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Room Database
        db = Room.databaseBuilder(this, RekamMedisDatabase.class, "rekam-medis-db")
                .fallbackToDestructiveMigration()
                .build();

        // Inisialisasi layout
        hasilTensi = findViewById(R.id.hasilTensi);
        hasilSaturasi = findViewById(R.id.hasilSaturasi);
        hasilDetak = findViewById(R.id.hasilDetakJantung);
        hasilSuhu = findViewById(R.id.hasilSuhu);
        hasilDiagnosa = findViewById(R.id.hasilDiagnosa);

        tambahdata = findViewById(R.id.tambahdata);
        tambahdata.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, TambahRekam.class)));

        liatSemuaArtikel = findViewById(R.id.btArtikelTersimpan);
        liatSemuaArtikel.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SavedArticle.class)));

        rvArtikel = findViewById(R.id.rvArtikel);
        rvArtikel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterArtikel = new ArtikelAdapter(artikelss, false);
        rvArtikel.setAdapter(adapterArtikel);

        // Firebase Database
        db2Ref = FirebaseDatabase.getInstance(DBURL).getReference("status");
        db2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artikelss.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    Artikel artikel = s.getValue(Artikel.class);
                    // Validasi artikel sebelum ditambahkan
                    if (artikel != null && artikel.getJudul() != null && !artikel.getJudul().isEmpty()) {
                        artikelss.add(artikel);
                    }
                }
                adapterArtikel.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        loadRekamMedis();


    }

    private void loadRekamMedis() {
        Thread t = new Thread(() -> {
            try {
                RekamMedis latestData = db.rekamMedisDao().getLatestRekamMedis();
                runOnUiThread(() -> {
                    if (latestData != null) {
                        hasilSaturasi.setText(latestData.saturasiOksigen);
                        hasilTensi.setText(latestData.tekananDarah);
                        hasilDetak.setText(latestData.detakJantung);
                        hasilSuhu.setText(latestData.suhu);
                        hasilDiagnosa.setText(latestData.diagnosa);
                    } else {
                        hasilDiagnosa.setText("Belum ada data diagnosa");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
}

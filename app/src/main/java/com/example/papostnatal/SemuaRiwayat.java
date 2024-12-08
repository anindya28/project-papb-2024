package com.example.papostnatal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SemuaRiwayat extends AppCompatActivity {

    private RecyclerView rvRiwayat;
    private KoleksiAdapter koleksiAdapter;
    private RekamMedisDatabase db;
    private List<RekamMedis> koleksi;
    private ExecutorService executorService;
    private ImageView backRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_riwayat);

        //inisialisasi recyclerview
        rvRiwayat = findViewById(R.id.rvRiwayat);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(this));
        backRiwayat = findViewById(R.id.ivBackRiwayat);

        backRiwayat.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        // Inisialisasi database
        db = Room.databaseBuilder(this, RekamMedisDatabase.class, "rekam-medis-db")
                .fallbackToDestructiveMigration()
                .build();
        koleksi = new ArrayList<>();

        // setup adapter
        koleksiAdapter = new KoleksiAdapter(this, koleksi, ((rekamMedis, position) -> {
            deleteRekamMedis(rekamMedis, position);
        }));
        rvRiwayat.setAdapter(koleksiAdapter);

        // Inisialisasi executor
        executorService = Executors.newSingleThreadExecutor();
        // Load semua data dari database
        loadAllRekamMedis();
    }
    private void deleteRekamMedis(RekamMedis rekamMedis, int position){
        new Thread(()->{
            db.rekamMedisDao().DeleteRekamMedis(rekamMedis);
            runOnUiThread(()->{
                koleksi.remove(position);
                koleksiAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void loadAllRekamMedis() {
        executorService.execute(() -> {
            try {
                // Fetch semua rekam medis
                List<RekamMedis> dataRekamMedis = db.rekamMedisDao().getAll();

                // Update koleksi di thread yang sama
                koleksi.clear();
                koleksi.addAll(dataRekamMedis);

                // Notifikasi adapter untuk perbarui UI
                rvRiwayat.post(() -> koleksiAdapter.notifyDataSetChanged());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
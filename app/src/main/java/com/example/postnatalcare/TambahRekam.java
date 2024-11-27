package com.example.postnatalcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TambahRekam extends AppCompatActivity {

    private List<RekamMedis> koleksi;
    private KoleksiAdapter koleksiAdapter;
    private RecyclerView rvKoleksi;
    private TextInputLayout spO2;
    private TextInputLayout mm;
    private TextInputLayout hg;
    private TextInputLayout bpm;
    private TextInputLayout celcius;
    private Button btSimpan;
    private Button btSemuaRiwayat;
    private RekamMedisDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rekam);

        // Inisialisasi database
        db = Room.databaseBuilder(this, RekamMedisDatabase.class, "rekam-medis-db")
                .fallbackToDestructiveMigration()
                .build();

        // Insialisasi layout
        spO2 = findViewById(R.id.tiSpO2);
        mm = findViewById(R.id.tiMm);
        hg = findViewById(R.id.tiHg);
        bpm = findViewById(R.id.tiDetakJantung);
        celcius = findViewById(R.id.tiSuhu);
        btSimpan = findViewById(R.id.btTambah);
        btSemuaRiwayat = findViewById(R.id.btSemuaRiwayat);

        koleksi = new ArrayList<>();
        koleksiAdapter = new KoleksiAdapter(this, koleksi);
        rvKoleksi = findViewById(R.id.rvRiwayat);
        rvKoleksi.setLayoutManager(new LinearLayoutManager(this));
        rvKoleksi.setAdapter(koleksiAdapter);

        // Inisialisasi validasi form
        validateForm();

        // Load data dari database
        loadRekamMedis();

        btSimpan.setOnClickListener(view -> {
            String sp = spO2.getEditText().getText().toString();
            String mmValue = mm.getEditText().getText().toString();
            String hgValue = hg.getEditText().getText().toString();
            String tensi = mmValue + "/" + hgValue;
            String bpmValue = bpm.getEditText().getText().toString();
            String cel = celcius.getEditText().getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDate = sdf.format(new Date());

            // Simpan ke SQL dan recyclerview
            Thread t = new Thread(() -> {
                RekamMedis rekamMedis = new RekamMedis(sp, tensi, bpmValue, cel, " ", currentDate);

                // Membuat diagnosis
                rekamMedis.diagnosa = rekamMedis.diagnosa();

                // Menyimpan ke SQLite
                db.rekamMedisDao().insertRekamMedis(rekamMedis);

                // Update RecyclerView
                List<RekamMedis> dataRekamMedis = db.rekamMedisDao().getLastThree();
                runOnUiThread(() -> {
                    koleksi.clear();
                    koleksi.addAll(dataRekamMedis);
                    koleksiAdapter.notifyDataSetChanged();
                    checkRekamMedisAvailability();
                });
            });
            t.start();

            // Reset input form
            spO2.getEditText().setText("");
            mm.getEditText().setText("");
            hg.getEditText().setText("");
            bpm.getEditText().setText("");
            celcius.getEditText().setText("");
        });

        btSemuaRiwayat.setOnClickListener(view -> {
            Intent intent = new Intent(TambahRekam.this, SemuaRiwayat.class);
            startActivity(intent);
        });
    }

    private void loadRekamMedis() {
        Thread t = new Thread(() -> {
            try {
                // Mengambil 3 data terakhir
                List<RekamMedis> dataRekamMedis = db.rekamMedisDao().getLastThree();
                runOnUiThread(() -> {
                    koleksi.clear();
                    koleksi.addAll(dataRekamMedis);
                    koleksiAdapter.notifyDataSetChanged();
                    checkRekamMedisAvailability();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    private void validateForm() {
        // Tambahkan listener pada setiap input untuk memvalidasi form
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isFormValid = isFormFilled();
                btSimpan.setEnabled(isFormValid);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        spO2.getEditText().addTextChangedListener(textWatcher);
        mm.getEditText().addTextChangedListener(textWatcher);
        hg.getEditText().addTextChangedListener(textWatcher);
        bpm.getEditText().addTextChangedListener(textWatcher);
        celcius.getEditText().addTextChangedListener(textWatcher);
    }

    private boolean isFormFilled() {
        return !spO2.getEditText().getText().toString().isEmpty() &&
                !mm.getEditText().getText().toString().isEmpty() &&
                !hg.getEditText().getText().toString().isEmpty() &&
                !bpm.getEditText().getText().toString().isEmpty() &&
                !celcius.getEditText().getText().toString().isEmpty();
    }

    private void checkRekamMedisAvailability() {
        btSemuaRiwayat.setEnabled(!koleksi.isEmpty());
    }
}


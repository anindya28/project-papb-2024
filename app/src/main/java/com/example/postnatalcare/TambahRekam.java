package com.example.postnatalcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TambahRekam extends AppCompatActivity {

    private List<Riwayat> Koleksi;
    private KoleksiAdapter koleksiAdapter;
    private RecyclerView rvKoleksi;
    private TextInputLayout SpO2;
    private TextInputLayout mm;
    private TextInputLayout hg;
    private TextInputLayout bpm;
    private TextInputLayout celcius;
    private Button btSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rekam);

        this.SpO2 = findViewById(R.id.tiSpO2);
        this.mm = findViewById(R.id.tiMm);
        this.hg = findViewById(R.id.tiHg);
        this.bpm = findViewById(R.id.tiDetakJantung);
        this.celcius = findViewById(R.id.tiSuhu);
        this.btSimpan = findViewById(R.id.btTambah);
        
        this.Koleksi = new ArrayList<Riwayat>();
        this.koleksiAdapter = new KoleksiAdapter(this, this.Koleksi);
        this.rvKoleksi = this.findViewById(R.id.rvRiwayat);
        this.rvKoleksi.setLayoutManager(new LinearLayoutManager(this));
        this.rvKoleksi.setAdapter(this.koleksiAdapter);

        this.btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Sp = SpO2.getEditText().getText().toString();
                String MM = mm.getEditText().getText().toString();
                String HG = hg.getEditText().getText().toString();
                String BPM = bpm.getEditText().getText().toString();
                String cel = celcius.getEditText().getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                Riwayat riwayatbaru = new Riwayat(Sp,MM,HG,BPM,cel, currentDate);
                Koleksi.add(riwayatbaru);
                koleksiAdapter.notifyDataSetChanged();

                SpO2.getEditText().setText("");
                mm.getEditText().setText("");
                hg.getEditText().setText("");
                bpm.getEditText().setText("");
                celcius.getEditText().setText("");
            }
        });





    }
}
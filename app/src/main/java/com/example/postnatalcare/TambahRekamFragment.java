package com.example.postnatalcare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TambahRekamFragment extends Fragment {

    private List<Riwayat> Koleksi;
    private KoleksiAdapter koleksiAdapter;
    private RecyclerView rvKoleksi;
    private TextInputLayout SpO2;
    private TextInputLayout mm;
    private TextInputLayout hg;
    private TextInputLayout bpm;
    private TextInputLayout celcius;
    private Button btSimpan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tambah_rekam_fragment,container,false);

        this.SpO2 = v.findViewById(R.id.tiSpO2);
        this.mm = v.findViewById(R.id.tiMm);
        this.hg = v.findViewById(R.id.tiHg);
        this.bpm = v.findViewById(R.id.tiDetakJantung);
        this.celcius = v.findViewById(R.id.tiSuhu);
        this.btSimpan = v.findViewById(R.id.btTambah);

        this.Koleksi = new ArrayList<Riwayat>();
        this.koleksiAdapter = new KoleksiAdapter(getContext(), this.Koleksi);
        this.rvKoleksi = v.findViewById(R.id.rvRiwayat);
        this.rvKoleksi.setLayoutManager(new LinearLayoutManager(getContext()));
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
//
            }
        });
        return v;
    }
}
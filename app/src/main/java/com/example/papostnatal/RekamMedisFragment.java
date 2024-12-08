package com.example.papostnatal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RekamMedisFragment extends Fragment {

    private RekamMedisDatabase db;
    private TextView hasilTensi;
    private TextView hasilSaturasi;
    private TextView hasilDetak;
    private TextView hasilSuhu;
    private TextView hasilDiagnosa;
    private Button tambahdata;
    private Button updatedata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rekam_medis, container, false);

        //inisialisasi database
        db = Room.databaseBuilder(getContext(), RekamMedisDatabase.class, "rekam-medis-db").fallbackToDestructiveMigration().build();

        //inisialisasi layout
        this.hasilTensi = view.findViewById(R.id.hasilTensi);
        this.hasilSaturasi = view.findViewById(R.id.hasilSaturasi);
        this.hasilDetak = view.findViewById(R.id.hasilDetakJantung);
        this.hasilSuhu = view.findViewById(R.id.hasilSuhu);
        this.hasilDiagnosa = view.findViewById(R.id.hasilDiagnosa);

        tambahdata = view.findViewById(R.id.tambahdata);
        tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), tambah_rekam.class);
                startActivity(i);
            }
        });

        updatedata = view.findViewById(R.id.btUpdate);
        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getContext(),UpdateRekamMedis.class);
                startActivity(a);
            }
        });


        loadRekamMedis();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRekamMedis();
    }

    private void loadRekamMedis(){
        Thread t = new Thread(() -> {
            try {
                //ambil data terbaru dari database
                RekamMedis latestData = db.rekamMedisDao().getLatestRekamMedis();

                //update ke UI
                if (getActivity() != null){
                    getActivity().runOnUiThread(() -> {
                        if (latestData!= null){
                            hasilSaturasi.setText(latestData.saturasiOksigen);
                            hasilTensi.setText(latestData.tekananDarah);
                            hasilDetak.setText(latestData.detakJantung);
                            hasilSuhu.setText(latestData.suhu);
                            hasilDiagnosa.setText(latestData.diagnosa);
                        }else {
                            Log.d("Main Activity", "Tidak ada data rekam medis");
                            hasilDiagnosa.setText("Belum ada data diagnosa");

                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        });
        t.start();
    }
}
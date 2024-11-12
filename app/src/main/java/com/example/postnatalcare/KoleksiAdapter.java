package com.example.postnatalcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KoleksiAdapter extends RecyclerView.Adapter {
    private final Context ctx;
    private final List<Riwayat> Koleksi;

    public KoleksiAdapter(Context ctx, List<Riwayat> Koleksi) {
        this.ctx = ctx;
        this.Koleksi = Koleksi;
    }


    public  static class VH extends RecyclerView.ViewHolder{

        private final TextView tvSpO2;
        private final TextView tvMM;
        private final TextView tvHg;
        private final TextView tvBpm;
        private final TextView tvCel;
        private final TextView tvWaktuPengisian;
        private final TextView tvDiagnosa;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvSpO2 = itemView.findViewById(R.id.tvSpO2);
            this.tvMM = itemView.findViewById(R.id.tvmm);
            this.tvHg = itemView.findViewById(R.id.tvhg);
            this.tvBpm = itemView.findViewById(R.id.tvbpm);
            this.tvCel = itemView.findViewById(R.id.tvcelcius);
            this.tvWaktuPengisian = itemView.findViewById(R.id.tvTanggal);
            this.tvDiagnosa = itemView.findViewById(R.id.tvDiagnosa);
        }

        private void setRiwayat (Riwayat riwayat){
            this.tvSpO2.setText(riwayat.SpO2);
            this.tvMM.setText(riwayat.mm);
            this.tvHg.setText(riwayat.Hg);
            this.tvBpm.setText(riwayat.bpm);
            this.tvCel.setText(riwayat.celcius);
            this.tvWaktuPengisian.setText(riwayat.waktuPengisian);
            this.tvDiagnosa.setText(riwayat.diagnosa());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx).inflate(R.layout.item_riwayat, parent, false);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Riwayat r = this.Koleksi.get(position);
        VH vh = (VH) holder;
        vh.setRiwayat(r);

    }

    @Override
    public int getItemCount() {
        return this.Koleksi.size();
    }

}

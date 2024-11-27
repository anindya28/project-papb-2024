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
    private final List<RekamMedis> Koleksi;

    public KoleksiAdapter(Context ctx, List<RekamMedis> Koleksi) {
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
            this.tvMM = itemView.findViewById(R.id.tvmm); //sistolik
            this.tvHg = itemView.findViewById(R.id.tvhg); //diastolik
            this.tvBpm = itemView.findViewById(R.id.tvbpm);
            this.tvCel = itemView.findViewById(R.id.tvcelcius);
            this.tvWaktuPengisian = itemView.findViewById(R.id.tvTanggal);
            this.tvDiagnosa = itemView.findViewById(R.id.tvDiagnosa);
        }

        private void setRekamMedis (RekamMedis rekamMedis){
            this.tvSpO2.setText(rekamMedis.saturasiOksigen);
            String[] tekananDarah = rekamMedis.tekananDarah.split("/");
            this.tvMM.setText(tekananDarah[0]); // Sistolik
            this.tvHg.setText(tekananDarah[1]); // Diastolik
            this.tvBpm.setText(rekamMedis.detakJantung);
            this.tvCel.setText(rekamMedis.suhu);
            this.tvWaktuPengisian.setText(rekamMedis.waktuPengisian);
            this.tvDiagnosa.setText(rekamMedis.diagnosa);
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
        RekamMedis r = this.Koleksi.get(position);
        VH vh = (VH) holder;
        vh.setRekamMedis(r);

    }

    @Override
    public int getItemCount() {
        return this.Koleksi.size();
    }

}

package com.example.papostnatal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter{

    private final Context ctx;
    private final List<Tips> dataset;
    private DatabaseReference dbRef;

    public TipsAdapter(Context ctx, List<Tips> dataset){
        this.ctx = ctx;
        this.dataset = dataset;
    }
    public void setDBRef(DatabaseReference dbRef){
        this.dbRef = dbRef;
    }

    private class VH extends RecyclerView.ViewHolder{

        private final TextView tvJudul;
        private final TextView tvDeskripsi;
        private final ImageView ivGambar;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.tvJudul = itemView.findViewById(R.id.tvJudul);
            this.tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            this.ivGambar = itemView.findViewById(R.id.ivGambar);

        }

        private void bind(Tips tips){
            this.tvJudul.setText(tips.getJudul());
            this.tvDeskripsi.setText(tips.getDeskripsi());
            //set gambar
            Picasso.get()
                    .load(tips.getGambarUrl())
                    .into(ivGambar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //buat intent untuk pindah halaman
                    Intent intent = new Intent(ctx, DetailTips.class);
                    //kirim data lewat intent
                    intent.putExtra("judul", tips.getJudul());
                    intent.putExtra("deskripsi", tips.getDeskripsi());
                    intent.putExtra("gambarURL", tips.getGambarUrl());
                    intent.putExtra("isiTips", tips.getIsiTips());

                    ctx.startActivity(intent);
                }
            });

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx).inflate(R.layout.item_tips, parent, false);
        return  new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        vh.bind(this.dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }
}

package com.example.postnatalcare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ViewHolder>{

    private List<Artikel> artikelss;

    public ArtikelAdapter( List<Artikel> artikel){
        this.artikelss = artikel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivGambar;
        private final TextView tvJudulArtikel;

        public ViewHolder( View itemView){
            super(itemView);
            this.ivGambar = itemView.findViewById(R.id.ivGambar);
            this.tvJudulArtikel = itemView.findViewById(R.id.tvJudulArtikel);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artikel,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        new Thread(() -> {
                try {
                    URL url = new URL(artikelss.get(position).getGambarUrl());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    holder.ivGambar.post(()->holder.ivGambar.setImageBitmap(bmp));
                }catch (Exception e) {
                    e.printStackTrace();
                }
        }).start();

        holder.tvJudulArtikel.setText(artikelss.get(position).getJudul());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailArtikel.class);
            intent.putExtra("judul", artikelss.get(position).getJudul());
            intent.putExtra("gambar", artikelss.get(position).getGambarUrl());
            intent.putExtra("deskripsi",artikelss.get(position).getDeskripsi());
            view.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return this.artikelss.size();
    }
}

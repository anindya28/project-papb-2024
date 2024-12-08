package com.example.papostnatal;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ViewHolder> {

    private List<Artikel> artikelss;
    private final boolean isFromSavedPage;

    public ArtikelAdapter(List<Artikel> artikelss, boolean isFromSavedPage) {
        this.artikelss = artikelss;
        this.isFromSavedPage = isFromSavedPage;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivGambar;
        private final TextView tvJudulArtikel;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivGambar = itemView.findViewById(R.id.ivGambar);
            this.tvJudulArtikel = itemView.findViewById(R.id.tvJudulArtikel);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artikel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artikel artikel = artikelss.get(position);

        String judul = artikel.getJudul() != null ? artikel.getJudul() : "Judul tidak tersedia";
        String gambarUrl = artikel.getGambarUrl() != null ? artikel.getGambarUrl() : "";

        holder.tvJudulArtikel.setText(judul);

        Picasso.get()
                .load(gambarUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.cancel)
                .into(holder.ivGambar);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailArtikel.class);
            intent.putExtra("judul", judul);
            intent.putExtra("gambar", gambarUrl);
            intent.putExtra("deskripsi", artikel.getDeskripsi() != null ? artikel.getDeskripsi() : "Deskripsi tidak tersedia");
            intent.putExtra("isSaved", isFromSavedPage);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return artikelss.size();
    }

    public void updateData(List<Artikel> filteredList) {
//        artikelss.clear();
//        artikelss.addAll(newArtikels);
        artikelss = filteredList;
        notifyDataSetChanged();
    }
}

package com.example.postnatalcare;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ViewHolder> {

    private final List<Artikel> artikelss;
    private final boolean isFromSavedPage;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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

        // Validasi data artikel
        String judul = artikel.getJudul() != null ? artikel.getJudul() : "Judul tidak tersedia";
        String gambarUrl = artikel.getGambarUrl() != null ? artikel.getGambarUrl() : "";

        // Set judul artikel
        holder.tvJudulArtikel.setText(judul);

        // Picasso untuk memuat gambar
        Picasso.get()
                .load(gambarUrl) // URL gambar
                .placeholder(R.drawable.ic_launcher_foreground) // Gambar placeholder
                .error(R.drawable.cancel) // Gambar error jika URL tidak valid
                .into(holder.ivGambar); // Tempat gambar dimuat

        // Set onClickListener untuk item
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailArtikel.class);
            intent.putExtra("judul", judul);
            intent.putExtra("gambar", gambarUrl);
            intent.putExtra("deskripsi", artikel.getDeskripsi() != null ? artikel.getDeskripsi() : "Deskripsi tidak tersedia");
           intent.putExtra("isSaved", isFromSavedPage); //tandai bahwa ini dari halaman saved
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return artikelss.size();
    }
}

package com.example.papostnatal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArtikelFragment extends Fragment {

    private RecyclerView rvArtikel;
    private List<Artikel> artikelss = new ArrayList<>();
    private ArtikelAdapter adapterArtikel;
    private DatabaseReference db2Ref;

    private static final String DBURL = "https://papostnatal-default-rtdb.asia-southeast1.firebasedatabase.app/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artikel, container, false);

        // Inisialisasi layout
        rvArtikel = view.findViewById(R.id.rvArtikel);
        rvArtikel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapterArtikel = new ArtikelAdapter(artikelss, false);
        rvArtikel.setAdapter(adapterArtikel);

        // SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterArtikel(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterArtikel(newText);
                return true;
            }
        });

        // Firebase Database
        db2Ref = FirebaseDatabase.getInstance(DBURL).getReference("status");
        db2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artikelss.clear();

                for (DataSnapshot s : snapshot.getChildren()) {
                    Artikel artikel = s.getValue(Artikel.class);
                    if (artikel != null && artikel.getJudul() != null && !artikel.getJudul().isEmpty()) {
                        artikelss.add(artikel);
                    }
                }

                if (artikelss.isEmpty()) {
                    pushDummyDataToFirebase();
                } else {
                    Log.d("FirebaseData", "Number of artikel retrieved: " + artikelss.size());
                }

                adapterArtikel.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Error: " + error.getMessage());
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void filterArtikel(String query) {
        List<Artikel> filteredList = new ArrayList<>();
        for (Artikel artikel : artikelss) {
            if (artikel.getJudul().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(artikel);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "Artikel tidak ditemukan", Toast.LENGTH_SHORT).show();
        }else {
            adapterArtikel.updateData(filteredList);
        }
    }

    private void pushDummyDataToFirebase() {
        List<Artikel> dummyArtikels = new ArrayList<>();
        dummyArtikels.add(new Artikel("Artikel 1", "https://example.com/image1.jpg", "Deskripsi artikel 1"));
        dummyArtikels.add(new Artikel("Artikel 2", "https://example.com/image2.jpg", "Deskripsi artikel 2"));

        for (Artikel artikel : dummyArtikels) {
            String key = db2Ref.push().getKey();
            if (key != null) {
                db2Ref.child(key).setValue(artikel)
                        .addOnSuccessListener(aVoid -> Log.d("FirebaseData", "Dummy artikel added: " + artikel.getJudul()))
                        .addOnFailureListener(e -> Log.e("FirebaseData", "Failed to add dummy artikel: " + e.getMessage()));
            }
        }
    }
}

package com.example.postnatalcare;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rvArtikel;
    private List<Artikel> artikelss;
    private RequestQueue antrian;
    private ArtikelAdapter adapterArtikel;
    private Button btTambahRekam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        this.rvArtikel = view.findViewById(R.id.rvArtikel);
        this.artikelss = new ArrayList<>();
        this.antrian = Volley.newRequestQueue(requireContext());
        loadDataFormAPI();

        btTambahRekam = view.findViewById(R.id.tambahdata);
        btTambahRekam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameHome, new TambahRekamFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    private void  loadDataFormAPI(){
        String url = "https://api.mockfly.dev/mocks/e0a6f779-9fa6-44b9-975a-9c8b4307a580/data";
        StringRequest req = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Gson gson = new Gson();
                    ApiModel artikelss = gson.fromJson(String.valueOf(response), ApiModel.class);
                    Log.d("loadDataFormAPI", "loadDataFormAPI: "+artikelss.getData().get(1).getJudul());
                    this.artikelss.clear();
                    this.artikelss.addAll(artikelss.getData());
                    this.adapterArtikel = new ArtikelAdapter(this.artikelss);
                    this.rvArtikel.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
                    this.rvArtikel.setAdapter(this.adapterArtikel);

                }, error -> {
            Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        });
        antrian.add(req);
    }
}

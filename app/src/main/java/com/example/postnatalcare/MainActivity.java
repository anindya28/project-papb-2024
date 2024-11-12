package com.example.postnatalcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button tambahdata;
    private RecyclerView rvArtikel;
    private List<Artikel> artikelss;
    private ArtikelAdapter adapterArtikel;
    private RequestQueue antrian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.rvArtikel = findViewById(R.id.rvArtikel);
        this.artikelss = new ArrayList<>();

        this.antrian = Volley.newRequestQueue(this);

        loadDataFormAPI();

        tambahdata = findViewById(R.id.tambahdata);
        tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TambahRekam.class);
                startActivity(i);
            }
        });
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
                    this.rvArtikel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    this.rvArtikel.setAdapter(this.adapterArtikel);

                }, error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
        });
        antrian.add(req);
    }
}


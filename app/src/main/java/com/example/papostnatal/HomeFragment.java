package com.example.papostnatal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rvTips;
    private List<Tips> tips = new ArrayList<>();
    private RequestQueue antrian;
    private TipsAdapter adapterTips;
    private static final String DBURL = "https://papostnatal-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Tips
        rvTips = view.findViewById(R.id.rvTips);

        adapterTips = new TipsAdapter(getContext(), tips);
        this.db = FirebaseDatabase.getInstance(DBURL);
        this.dbRef = this.db.getReference("Tips");
        this.adapterTips.setDBRef(this.dbRef);

        rvTips.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTips.setAdapter(this.adapterTips);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tips.clear();

                for (DataSnapshot s : snapshot.getChildren()){
                    Tips tipss = s.getValue(Tips.class);
                    if (tipss != null && tipss.getJudul() != null && !tipss.getJudul().isEmpty()){
                        tips.add(tipss);
                    }
                }
                if (tips.isEmpty()){
                    pushDummyData();
                }else {
                    Log.d("Firebase Data", "Number of data retrieved: "+tips.size());
                }

                adapterTips.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Data", "Error: "+error.getMessage());
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //tips = new ArrayList<>();
        //antrian = Volley.newRequestQueue(getContext());
        //loadTipsFromAPI();

        return view;
    }

    private void pushDummyData(){
        List<Tips> dummy = new ArrayList<>();
        dummy.add(new Tips("Minggu Pertama",
                "https://images.unsplash.com/photo-1590467590164-c75b94a98575?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Hari 1 - 7",
                "Pada minggu pertama ini adalah periode yang penuh tantangan dan adaptasi. Dukungan keluarga pada minggu pertama dapat mencakup : " +
                        "\n 1. Membantu Perawatan Bayi, seperti mengganti popok bayi, membantu menyusui, merawat bayi saat ibu sedang istirahat" +
                        "\n 2. Menyediakan makanan yang sehat dan bergizi untuk ibu" +
                        "\n 3. Membantu dengan tugas rumah tangga, seperti memasak, mencuci pakaian, dan membersihkan rumah" +
                        "\n 4. Memberikan dukungan emosional dan memastikan ibu merasa didengar dan dipahami. " +
                        "\n " +
                        "\n Selain dukungan konkret ini, penting juga untuk mendengarkan kebutuhan dan keinginan ibu. Setiap ibu memiliki pengalaman nifas yang berbeda, jadi komunikasi terbuka dan empati sangat penting. Dukungan keluarga yang kuat selama masa nifas dapat membantu ibu merasa lebih nyaman, percaya diri, dan merasa didukung dalam peran barunya sebagai orang tua"));
        dummy.add(new Tips("Minggu Kedua",
                "https://images.unsplash.com/photo-1581952975975-08cd95a728d4?q=80&w=2942&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Hari 8 - 14",
                "Pada minggu kedua, ibu mungkin mulai merasa lebih stabil secara fisik tetapi masih membutuhkan banyak perhatian. Dukungan keluarga dapat mencakup : " +
                        "\n 1. Membantu ibu menjaga kebersihan pribadinya dan memberikan dukungan dengan perawatan luka jahit atau episiotomi jika diperlukan" +
                        "\n 2. Terus membantu dengan tugas-tugas rumah tangga dan perawatan bayi" +
                        "\n 3. Mengenalkan ibu pada istirahat yang baik" +
                        "\n 4. Membantu menjaga tidur yang cukup" +
                        "\n 5. Memberikan waktu luang untuk dirinya sendiri.  " +
                        "\n " +
                        "\n Selain dukungan konkret ini, penting juga untuk mendengarkan kebutuhan dan keinginan ibu. Setiap ibu memiliki pengalaman nifas yang berbeda, jadi komunikasi terbuka dan empati sangat penting. Dukungan keluarga yang kuat selama masa nifas dapat membantu ibu merasa lebih nyaman, percaya diri, dan merasa didukung dalam peran barunya sebagai orang tua."));
        dummy.add(new Tips("Minggu Ketiga",
                "https://static.honestdocs.id/system/blog_articles/main_hero_images/000/000/038/original/Masa_Nifas.jpg",
                "Hari 15 - 45",
                "Pada minggu-minggu berikutnya, ibu biasanya semakin pulih secara fisik tetapi masih memerlukan dukungan keluarga. Hal ini mencakup : " +
                        "\n 1. Terus membantu dengan perawatan bayi dan tugas-tugas rumah tangga " +
                        "\n 2. Mengenalkan ibu pada aktivitas fisik ringan yang dapat membantu pemulihan, seperti berjalan-jalan perlahan atau latihan ringan (sesuai dengan rekomendasi dokter)" +
                        "\n 3. Memberikan dukungan emosional dalam menjalani perubahan hormonal pasca-melahirkan dan peran sebagai orang tua" +
                        "\n 4. Mendengarkan ibu dan memberikan ruang untuk berbicara tentang pengalaman dan perasaannya."));

        for (Tips tipss : dummy){
            String key = dbRef.push().getKey();
            if (key != null) {
                dbRef.child(key).setValue(tipss);
            }
        }
    }

//    private void  loadTipsFromAPI(){
//        String url = "https://api.mockfly.dev/mocks/ca2a4e4d-555d-439f-9130-823b530566e4/tips";
//        StringRequest req = new StringRequest(
//                Request.Method.GET,
//                url,
//                response -> {
//                    Gson gson = new Gson();
//                    ApiModel tipss = gson.fromJson(String.valueOf(response), ApiModel.class);
//                    Log.d("loadTipsFromAPI", "loadTipsFromAPI : " + tipss.getData().get(1).getJudul());
//                    this.tips.clear();
//                    this.tips.addAll(tipss.getData());
//                    this.adapterTips = new  TipsAdapter(this.tips);
//                    this.rvTips.setLayoutManager(new LinearLayoutManager(getContext()));
//                    this.rvTips.setAdapter(this.adapterTips);
//                }, error -> {
//            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();}
//        );
//        antrian.add(req);
//    }
}
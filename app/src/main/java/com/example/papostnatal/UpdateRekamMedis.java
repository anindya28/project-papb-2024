package com.example.papostnatal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;

public class UpdateRekamMedis extends AppCompatActivity {

    private RekamMedisDatabase db;
    private TextInputLayout UpdateSpO2,UpdateSistolik, UpdateDiastolik, UpdateDetak, UpdateSuhu;
    private int rekamMedisId;
    private Button btUpdate;
    private ImageView ivBackUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rekam_medis);

        //inisialisasi database
        db = Room.databaseBuilder(this, RekamMedisDatabase.class, "rekam-medis-db")
                .fallbackToDestructiveMigration().build();

        //inisialisasi layout
        this.UpdateSpO2 = findViewById(R.id.saturasi);
        this.UpdateSistolik = findViewById(R.id.UpdateSistolik);
        this.UpdateDiastolik = findViewById(R.id.UpdateDiastolik);
        this.UpdateDetak = findViewById(R.id.UpdateDetak);
        this.UpdateSuhu = findViewById(R.id.UpdateSuhu);
        this.btUpdate = findViewById(R.id.btUpdateBaru);
        this.ivBackUpdate = findViewById(R.id.ivBackUpdate);

        ivBackUpdate.setOnClickListener(view -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        loadRekamMedis();
        btUpdate.setOnClickListener(view -> {
            //ambil data baru
            Thread t = new Thread(()->{
                //ambil dari input
                String UpdateSp = UpdateSpO2.getEditText().getText().toString();
                String UpdateSis = UpdateSistolik.getEditText().getText().toString();
                String UpdateDias = UpdateDiastolik.getEditText().getText().toString();
                String UpdateDetakJantung = UpdateDetak.getEditText().getText().toString();
                String Updatesuhu = UpdateSuhu.getEditText().getText().toString();

                //gabung sistolik dan diastolik
                String tensi = UpdateSis + "/" + UpdateDias;
                //update rekam medis
                RekamMedis rekamMedis = db.rekamMedisDao().getLatestRekamMedis();
                rekamMedis.saturasiOksigen = UpdateSp;
                rekamMedis.tekananDarah = tensi;
                rekamMedis.detakJantung = UpdateDetakJantung;
                rekamMedis.suhu = Updatesuhu;
                rekamMedis.diagnosa = rekamMedis.diagnosa();


                db.rekamMedisDao().updateRekamMedis(rekamMedis);

                runOnUiThread(()->{
                    Toast.makeText(UpdateRekamMedis.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                });
            });
            t.start();
        });
    }
    private void loadRekamMedis(){
        Thread t = new Thread(()->{
            RekamMedis rekamMedis = db.rekamMedisDao().getLatestRekamMedis();
            runOnUiThread(()->{
                if (rekamMedis != null){
                    UpdateSpO2.getEditText().setText(rekamMedis.saturasiOksigen);
                    String[] UpdateTekananDarah = rekamMedis.tekananDarah.split("/");
                    UpdateSistolik.getEditText().setText(UpdateTekananDarah[0]); // Sistolik
                    UpdateDiastolik.getEditText().setText(UpdateTekananDarah[1]); //diastolik
                    UpdateDetak.getEditText().setText(rekamMedis.detakJantung);
                    UpdateSuhu.getEditText().setText(rekamMedis.suhu);
                }
            });
        });
        t.start();
    }
}
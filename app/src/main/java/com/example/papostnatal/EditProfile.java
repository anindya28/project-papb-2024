package com.example.papostnatal;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private ImageView ivDatePicker;
    private DatePickerDialog datePickerDialog;
    private Button btUpdateProfile;
    private static final String DBURL = "https://papostnatal-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private DatabaseReference db2Ref;
    private EditText etNamaPengguna;
    private EditText etNamaIbuNifas;
    private RadioGroup rgJenisKelamin;
    private RadioButton rbLaki;
    private RadioButton rbPerempuan;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        tvDateResult = findViewById(R.id.tglDipilih);
        ivDatePicker = findViewById(R.id.ivDatePicker);
        btUpdateProfile = findViewById(R.id.btUpdateProfile);
        etNamaPengguna = findViewById(R.id.namaPengguna);
        etNamaIbuNifas = findViewById(R.id.namaIbuNifas);
        rgJenisKelamin = findViewById(R.id.jenisKelaminAnak);
        rbLaki = findViewById(R.id.rbLakiLaki);
        rbPerempuan = findViewById(R.id.rbPerempuan);
        ivBack = findViewById(R.id.ivBackEditProfile);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        ivDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedData();
            }
        });

        db2Ref = FirebaseDatabase.getInstance(DBURL).getReference("profile");
        //pushDummyData();
        fetchData();



    }

//    private void pushDummyData(){
//        Map<String, Object> dummyData = new HashMap<>();
//        dummyData.put("namaPengguna", "jessicaaa");
//        dummyData.put("namaIbuNifas", "Jessica Amalia");
//        dummyData.put("tglPersalinan", "2023-12-30");
//        dummyData.put("jenisKelamin", "Laki-Laki");
//
//        db2Ref.child("DummyProfile").setValue(dummyData)
//                .addOnSuccessListener(aVoid -> Log.d("FirebaseData", "Dummy data berhasil ditambahkan"))
//                .addOnFailureListener(e -> Log.e("FirebaseData", "Gagal menambahkan dummy data: " + e.getMessage()));
//    }
    private void fetchData(){
        db2Ref.child("DummyProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String namaPengguna = snapshot.child("namaPengguna").getValue(String.class);
                    String namaIbuNifas = snapshot.child("namaIbuNifas").getValue(String.class);
                    String tglPersalinan = snapshot.child("tglPersalinan").getValue(String.class);
                    String jenisKelamin = snapshot.child("jenisKelamin").getValue(String.class);

                    // Tampilkan data di EditText
                    etNamaPengguna.setText(namaPengguna);
                    etNamaIbuNifas.setText(namaIbuNifas);
                    tvDateResult.setText(tglPersalinan);

                    // Tampilkan data di RadioButton
                    if (jenisKelamin != null) {
                        if ("Laki-laki".equalsIgnoreCase(jenisKelamin)) {
                            rbLaki.setChecked(true);
                        } else if ("Perempuan".equalsIgnoreCase(jenisKelamin)) {
                            rbPerempuan.setChecked(true);
                        }
                    } else {
                        Toast.makeText(EditProfile.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Gagal membaca data: " + error.getMessage());
            }
        });
    }
    private void savedData(){
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("namaPengguna", etNamaPengguna.getText().toString().trim());
        updatedData.put("namaIbuNifas", etNamaIbuNifas.getText().toString().trim());
        updatedData.put("tglPersalinan", tvDateResult.getText().toString().trim());

        // Ambil data dari RadioGroup
        String jenisKelamin = (rbLaki.isChecked()) ? "Laki-laki" : (rbPerempuan.isChecked()) ? "Perempuan" : "Tidak Diketahui";
        updatedData.put("jenisKelamin", jenisKelamin);

        db2Ref.child("DummyProfile").updateChildren(updatedData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    fetchData();
                })
                .addOnFailureListener(e -> Log.e("FirebaseData", "Gagal memperbarui data: " + e.getMessage()));
    }
    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        int year = newCalendar.get(Calendar.YEAR);
        int monthOfYear = newCalendar.get(Calendar.MONTH);
        int dayOfMonth = newCalendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvDateResult.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            }
        }, year, monthOfYear, dayOfMonth);
        datePickerDialog.show();

    }

}
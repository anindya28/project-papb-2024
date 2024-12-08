package com.example.papostnatal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ProfilFragment extends Fragment {

    private Button liatSemuaArtikel;
    private ImageView backSavedArticle;
    private Button editProfile;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        //list artikel tersimpan
        liatSemuaArtikel = view.findViewById(R.id.btSavedArticle);
        liatSemuaArtikel.setOnClickListener(v -> startActivity(new Intent(getContext(), SavedArticle.class)));

        editProfile = view.findViewById(R.id.btEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditProfile.class);
                startActivity(i);
            }
        });

        return view;
    }


}
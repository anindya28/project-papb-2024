package com.example.trackingkesehatanibu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private EditText beratBadanEditText, tekananDarahEditText, detakJantungEditText;
    private Button kirimButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        beratBadanEditText = view.findViewById(R.id.beratBadan);
        tekananDarahEditText = view.findViewById(R.id.tekananDarah);
        detakJantungEditText = view.findViewById(R.id.detakJantung);
        kirimButton = view.findViewById(R.id.kirimButton);


        kirimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kirimData();
            }
        });

        return view;
    }

    private void kirimData() {

        String beratBadan = beratBadanEditText.getText().toString();
        String tekananDarah = tekananDarahEditText.getText().toString();
        String detakJantung = detakJantungEditText.getText().toString();


        Toast.makeText(getActivity(), "Data dikirim: " + beratBadan + ", " + tekananDarah + ", " + detakJantung, Toast.LENGTH_SHORT).show();
    }
}

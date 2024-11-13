package com.example.trackingkesehatanibu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText namaEditText, beratEditText;
    private Button submitButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namaEditText = findViewById(R.id.namaEditText);
        beratEditText = findViewById(R.id.beratEditText);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void sendData() {
        String nama = namaEditText.getText().toString();
        String berat = beratEditText.getText().toString();


        new SendDataTask().execute(nama, berat);
    }


    private class SendDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String nama = params[0];
            String berat = params[1];
            String response = "";

            try {

                String urlString = "http:http://10.0.2.2/api.php";
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);


                JSONObject postData = new JSONObject();
                postData.put("nama", nama);
                postData.put("berat", berat);


                OutputStream os = urlConnection.getOutputStream();
                os.write(postData.toString().getBytes("UTF-8"));
                os.close();


                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuilder responseBuilder = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                in.close();

                response = responseBuilder.toString();
                urlConnection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            resultTextView.setText(result);
        }
    }
}

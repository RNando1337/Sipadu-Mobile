package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.VibrationAttributes;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ValidasiPassword extends AppCompatActivity {

    TextInputEditText pass;
    Button Lanjut;

    private String JSON_RESPONSE = "response";
    private String JSON_RESULT = "result";
    private String JSON_VALIDASI = "validasi";
    private String JSON_PASS = "pass";
    private String JSON_EMAIL = "email";
    private String URL_CEK = "http://192.168.43.175/ci/sitaro_crud/validasi_pass.php";

    //Shared inisialisasi Preferance
    private SharedPreferences preferences;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;
    String validasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validasi_password);

        //membuat data/file baru
        preferences = getSharedPreferences("Data_Login", Context.MODE_PRIVATE);
        pass = findViewById(R.id.password);
        Lanjut = findViewById(R.id.Lanjut);
        Intent i = getIntent();
        String getEmail = preferences.getString("Email", "");
        validasi = i.getStringExtra("CekValidasi");

        Lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asynctask(validasi,pass.getText().toString(),getEmail);
            }
        });

    }

//    private void cekValidasi(){
//        Intent i = getIntent();
//        String getEmail = preferences.getString("Email", "");
//        validasi = i.getStringExtra("CekValidasi");
//        asynctask(validasi,pass.getText().toString(),getEmail);
//    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Peringatan");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Password Salah!")
                .setIcon(R.drawable.logo_pln)
                .setCancelable(false)
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();

    }

    private void asynctask(String validasi,String getPass, String mail){
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONdata(validasi,s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(JSON_EMAIL, mail);
                hashMap.put(JSON_PASS, getPass);
                RequestHandler requestHandler = new RequestHandler();
                String i = requestHandler.sendPostRequest(URL_CEK, hashMap);
                return i;
            }
        }.execute();
    }

    private void JSONdata(String Validasi,String json) {
        String response= "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_RESULT);
            JSONObject object = jsonArray.getJSONObject(0);
            response = object.getString(JSON_RESPONSE);

            if(response.equals("gagal")){
                showDialog();
            }
            if(response.equals("ok")){
                if(Validasi.equals("Email") || Validasi == "Email"){
                    Intent i = new Intent(ValidasiPassword.this, UbahEmail.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(ValidasiPassword.this, Atur_akun.class);
                    startActivity(i);
                }
            }

//            Toast.makeText(Login.this, response+""+name, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
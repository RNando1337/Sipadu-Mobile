package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Atur_akun extends AppCompatActivity {


    ProgressDialog load;
    TextInputEditText ubah;
    Button ubahpass;

    SharedPreferences sharedPreferences;

    private String TAG_PASS = "pass";
    private String TAG_USER = "CEKUSER";
    private String TAG_KD = "id_OR_kd";
    private String URL_UBAH = "http://192.168.43.175/ci/sitaro_crud/atur_akun.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_akun);

        ubah = findViewById(R.id.txtpassword);
        ubahpass = findViewById(R.id.Ubah_Password);

        ubahpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubahpass();
                Intent i = new Intent(Atur_akun.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void ubahpass(){
        sharedPreferences = getSharedPreferences("Data_Login", 0);
        String kd_pet = sharedPreferences.getString("IDPEL", "");
        String getUbah = ubah.getText().toString().trim();


        AsyncTask<Void,Void,String> asyncTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                new SweetAlertDialog(Atur_akun.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil mengubah password!")
                        .hideConfirmButton()
                        .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(Atur_akun.this, Profile.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_USER,"pelanggan");
                hashMap.put(TAG_PASS,getUbah);
                hashMap.put(TAG_KD, kd_pet);
                RequestHandler requestHandler = new RequestHandler();
                String input = requestHandler.sendPostRequest(URL_UBAH,hashMap);
                return input;
            }
        };
        asyncTask.execute();
    }

}
package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UbahEmail extends AppCompatActivity {

    TextInputEditText email;
    TextInputLayout emails;
    Button Lanjut;

    SharedPreferences sharedPreferences;
    // Shared Konfigurasi
    private SharedPreferences.Editor editor;

    private String TAG_EMAIL = "email";
    private String TAG_KD = "IDPEL";
    private String URL_UBAH = "http://192.168.43.175/ci/sitaro_crud/ubahemail.php";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_email);

        email = findViewById(R.id.txtEmail);
        emails = findViewById(R.id.Email);
        Lanjut = findViewById(R.id.Ubah_email);

        sharedPreferences = getSharedPreferences("Data_Login", 0);

        email.setText(sharedPreferences.getString("Email", ""));

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(email.getText().toString().trim().matches(emailPattern) && s.length() > 0){
                    emails.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    emails.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    emails.setErrorEnabled(false);
                }else{
                    emails.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    emails.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    emails.setError("Email tidak valid");
                }
            }
        });

        Lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(email.getText().toString().trim().matches(emailPattern))
                        || email.getText().toString().trim().length() < 1
                ) {
                    showDialog();
                }else{
                    ubahmail();
                }
            }
        });

    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Peringatan");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Email Tidak Valid!!")
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

    public void ubahmail(){

        String kd_pet = sharedPreferences.getString("IDPEL", "");
        String getUbah = email.getText().toString().trim();

        AsyncTask<Void,Void,String> asyncTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                new SweetAlertDialog(UbahEmail.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil mengubah email!")
                        .hideConfirmButton()
                        .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sharedPreferences = getSharedPreferences("Data_Login", 0); // 0 = mode_private
                                editor = sharedPreferences.edit();
                                editor.putString("Email", getUbah);
                                editor.commit();
                                Intent i = new Intent(UbahEmail.this, Profile.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_EMAIL,getUbah);
                hashMap.put(TAG_KD, kd_pet);
                RequestHandler requestHandler = new RequestHandler();
                String input = requestHandler.sendPostRequest(URL_UBAH,hashMap);
                return input;
            }
        };
        asyncTask.execute();
    }





}
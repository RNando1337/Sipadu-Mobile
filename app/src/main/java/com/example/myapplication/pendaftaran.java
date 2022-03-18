package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class pendaftaran extends AppCompatActivity {

    ProgressDialog load;
    TextInputEditText username,Email,Password;
    TextInputLayout usernames,Emails,Passwords;
    ImageView back;
    Button daftar;
//    List<String> dataList = new ArrayList<>();
    private String JSON_STRING = "result";
    private String JSON_RESPONSE = "response";
    private String URL_PENGGUNA = "http://192.168.43.175/CI/Api";
    private String URL_GETPENGGUNA = "http://192.168.43.175/CI/Api?data=";
    private String URL_DAFTAR_PENGGUNA = "http://192.168.43.175/CI/Api/register_proses";
    private String NAMA_PEL = "NAMA";
    private String EMAIL = "email";
    private String PASS = "pass";
    private String JSON_PENGADU = "tipe";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    //Shared inisialisasi Preferance
    private SharedPreferences preferences;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran);

        //membuat data/file baru
        preferences = getSharedPreferences("Data_Login", Context.MODE_PRIVATE);

        back = findViewById(R.id.arrow);
        Email = findViewById(R.id.email);
        username = findViewById(R.id.txtUsername);
        Password = findViewById(R.id.password);
        daftar = findViewById(R.id.Login);

        Emails = findViewById(R.id.Email);
        usernames = findViewById(R.id.username);
        Passwords = findViewById(R.id.Password);

        if(preferences.getString("Tipe_user","").equalsIgnoreCase("Lembaga")){
            usernames.setHint("Nama Lembaga");
        }else{
            usernames.setHint("Nama Lengkap");
        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    usernames.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    usernames.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    usernames.setErrorEnabled(false);
                }else{
                    usernames.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    usernames.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    usernames.setError("Data Belum Terisi");
                }
            }
        });

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(Email.getText().toString().trim().matches(emailPattern) && s.length() > 0){
                    Emails.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    Emails.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    Emails.setErrorEnabled(false);
                }else{
                    Emails.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    Emails.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    Emails.setError("Email tidak valid");
                }
            }
        });

        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(Password.length() < 8){
                    Passwords.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    Passwords.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    Passwords.setError("Password harus lebih dari 8 karakter");
                }else{
                    Passwords.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    Passwords.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    Passwords.setErrorEnabled(false);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pendaftaran.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Email.getText().toString().trim().matches(emailPattern))
                        || Email.getText().toString().trim().length() < 1
                        || Password.getText().toString().trim().length() < 8
                        || username.getText().toString().length() < 1
                ) {
                    showDialog();
                }else{
                    if(preferences.getString("Tipe_user","").equalsIgnoreCase("Lembaga")){
                       if(username.getText().toString().split(" ")[0].equalsIgnoreCase("Dinas")
                       || username.getText().toString().split(" ")[0].equalsIgnoreCase("Rumah")
                       || username.getText().toString().split(" ")[0].equalsIgnoreCase("Ponpes")
                       || username.getText().toString().split(" ")[0].equalsIgnoreCase("SMA")
                       || username.getText().toString().split(" ")[0].equalsIgnoreCase("SMP")
                       || username.getText().toString().split(" ")[0].equalsIgnoreCase("SMK")
                       || username.getText().toString().split(" ")[0].equalsIgnoreCase("SD")
                       ){
                           simpanData();
                       }else{
                           showDialog2();
                       }
                    }else{
                        simpanData();
                    }
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
                .setMessage("Data tidak sesuai!")
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

    private void showDialog2(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Peringatan");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Isi sesuai nama lembaga / instansi!")
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

//    private void getpengguna(String json){
//        final HashMap<String, ArrayList<String>> data = new HashMap<>();
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray jsonArray = jsonObject.getJSONArray(JSON_STRING);
//            for(int i = 0; i<jsonArray.length(); i++){
//                JSONObject object = jsonArray.getJSONObject(i);
//
//                dataList.add(object.getString(ID_PEL)+" ( "+object.getString(NAMA_PEL)+" )");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,dataList);
//        idpel.setAdapter(adapter);
//    }
//
//    private void getJSON() {
//        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                load = ProgressDialog.show(pendaftaran.this, "Load","Get Data",false,false);
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                load.dismiss();
//                getpengguna(s);
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                RequestHandler requestHandler = new RequestHandler();
//                String ambil = requestHandler.sendGetRequest(URL_PENGGUNA);
//                return ambil;
//            }
//        };
//        asyncTask.execute();
//    }

    public void exeJSON(String data){
        String response= "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_STRING);
            JSONObject object = jsonArray.getJSONObject(0);
            response = object.getString(JSON_RESPONSE);

            if(response.equals("Gagal")){
                new SweetAlertDialog(pendaftaran.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("E-mail sudah terdaftar!")
                        .setCancelText("Tutup")
                        .show();
            }

            if(response.equals("Gagal2")){
                new SweetAlertDialog(pendaftaran.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Bukan nama lembaga / instansi!")
                        .setCancelText("Tutup")
                        .show();
            }

            if(response.equals("Berhasil")){
                new SweetAlertDialog(pendaftaran.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil melakukan pendaftaran!")
                        .hideConfirmButton()
                        .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                Intent i = new Intent(pendaftaran.this,Login.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .show();
            }

//            Toast.makeText(Login.this, response+""+name, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void simpanData(){
        final String email_pel = Email.getText().toString();
        final String pass_pel = Password.getText().toString();
        final String NAMA = username.getText().toString();
        final String tipe_pengadu = preferences.getString("Tipe_user", "");
        AsyncTask<Void,Void,String> asyncTask = new AsyncTask<Void, Void, String>() {

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                exeJSON(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(EMAIL, email_pel);
                hashMap.put(NAMA_PEL, NAMA);
                hashMap.put(JSON_PENGADU, tipe_pengadu);
                hashMap.put(PASS, pass_pel);
                RequestHandler requestHandler = new RequestHandler();
                String input = requestHandler.sendPostRequest(URL_DAFTAR_PENGGUNA,hashMap);
                return input;
            }
        };
        asyncTask.execute();
    }

}



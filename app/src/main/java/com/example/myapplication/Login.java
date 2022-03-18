package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.microedition.khronos.egl.EGLDisplay;

public class Login extends AppCompatActivity {

    TextInputEditText user,pass;
    ImageView back;
    private TextView forgotpass,daftar;
    private Button login;
    private String JSON_RESPONSE = "response";
    private String JSON_NAMA = "nama";
    private String JSON_IDPEL = "idpel";
    private String JSON_EMAIL = "email";
    private String JSON_NIK = "NIK";
    private String JSON_NO_HP = "no_hp";
    private String JSON_ALAMAT = "alamat";
    private String USER = "user";
    private String PASS = "pass";
    private String CEKUSER = "CEKUSER";
    private String JSON_PENGADU = "tipe";
    private String URL_LOGIN = "http://192.168.43.175/ci/sitaro_crud/login.php";
    private String JSON_RESULT = "result";

    //Shared inisialisasi Preferance
    private SharedPreferences preferences;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.arrow);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        forgotpass = findViewById(R.id.lupapass);
        daftar = findViewById(R.id.daftar);
        login = findViewById(R.id.Login);

        //membuat data/file baru
        preferences = getSharedPreferences("Data_Login", Context.MODE_PRIVATE);

        LoginIsNotNull(); // check session

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,lupapass.class);
                startActivity(i);
                finish();
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,pendaftaran.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().trim().length() == 0
                || pass.getText().toString().trim().length() == 0){
                    showDialog2();
                }else{
                    login();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,TipePengadu.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void LoginIsNotNull(){
        String getName = preferences.getString("Nama", "");
        if(getName != null && !getName.equals("")){
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
            finish();
        }else{
            showDialog();
        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Peringatan");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Login untuk melakukan pengaduan!")
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
                .setMessage("Login gagal, akun belum teraktivasi/tidak terdaftar!")
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

    private void showDialog3(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Peringatan");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Login gagal, status pengadu tidak sama dengan status pelanggan!")
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

    private void getData(String json){
        String response= "";
        String name= "";
        String getIDPEL ="";
        String email = "";
        String NIK = "";
        String Alamat = "";
        String no_hp = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_RESULT);
            JSONObject object = jsonArray.getJSONObject(0);
            response = object.getString(JSON_RESPONSE);

            if(response.equalsIgnoreCase("gagal")){
                showDialog2();
            }

            if(response.equalsIgnoreCase("tipe_salah")){
                showDialog3();
            }

            if(response.equalsIgnoreCase("ok")){
                name = object.getString(JSON_NAMA);
                getIDPEL = object.getString(JSON_IDPEL);
                email = object.getString(JSON_EMAIL);
                NIK = object.getString(JSON_NIK);
                Alamat = object.getString(JSON_ALAMAT);
                no_hp = object.getString(JSON_NO_HP);
                if(getIDPEL.equals("")
                        ||NIK.equals("")
                        ||Alamat.equals("")
                        ||no_hp.equals("")
                        ||getIDPEL == ""
                        ||NIK == ""
                        ||Alamat == ""
                        ||no_hp == "") {
                    preferences = getSharedPreferences("Data_Login", 0); // 0 = mode_private
                    editor = preferences.edit();
                    editor.putString("Nama", name);
                    editor.putString("Email", email);
                    editor.putString("NIK", "");
                    editor.putString("no_hp", "");
                    editor.putString("Alamat", "");
                    editor.putString("IDPEL", "");
                    editor.commit();
                }else{
                    preferences = getSharedPreferences("Data_Login", 0); // 0 = mode_private
                    editor = preferences.edit();
                    editor.putString("Nama", name);
                    editor.putString("Email", email);
                    editor.putString("NIK", NIK);
                    editor.putString("no_hp", no_hp);
                    editor.putString("Alamat", Alamat);
                    editor.putString("IDPEL", getIDPEL);
                    editor.commit();
                }

                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish();
            }

//            Toast.makeText(Login.this, response+""+name, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        final String users = user.getText().toString();
        final String passw = pass.getText().toString();
        final String tipe_pengadu = preferences.getString("Tipe_user", "");
        AsyncTask<Void,Void,String> asyncTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getData(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(USER, users);
                hashMap.put(PASS, passw);
                hashMap.put(JSON_PENGADU, tipe_pengadu);
                hashMap.put(CEKUSER, "pelanggan");
                RequestHandler requestHandler = new RequestHandler();
                String input = requestHandler.sendPostRequest(URL_LOGIN,hashMap);
                return input;
            }
        };
        asyncTask.execute();
    }


}
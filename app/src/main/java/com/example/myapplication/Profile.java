package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Profile extends AppCompatActivity {

    RelativeLayout ubah_pass,ubah_mail;
    TextView txtuabah_pass, txtuabah_mail,arrow,arrow2;
    TextInputEditText nama,idpel,no_hp,alamat,nik;
    TextInputLayout txtNama,txtIDPEL,txtNo_Hp,txtAlamat,txtNIK;
    Button Simpan;

    private String JSON_RESPONSE = "response";
    private String JSON_RESULT = "result";


    private String JSON_NAMA = "nama";
    private String JSON_IDPEL = "idpel";
    private String JSON_NO_HP = "no_hp";
    private String JSON_ALAMAT = "alamat";
    private String JSON_NIK = "nik";
    private String JSON_EMAIL = "email";
    private String JSON_PENGADU = "tipe";
    private String URL_PROFILE = "http://192.168.43.175/ci/sitaro_crud/UpdateProfile.php";


    //Shared inisialisasi Preferance
    private SharedPreferences preferences;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //membuat data/file baru
        preferences = getSharedPreferences("Data_Login", Context.MODE_PRIVATE);

        // Pass
        ubah_pass = findViewById(R.id.changepass);
        txtuabah_pass = findViewById(R.id.txtChangepass);
        arrow = findViewById(R.id.txtChangepass2);
        // Mail
        ubah_mail = findViewById(R.id.changemail);
        txtuabah_mail = findViewById(R.id.txtChangemail);
        arrow2 = findViewById(R.id.txtChangemail2);

        //TextInputEditText
        nama = findViewById(R.id.txtNama);
        idpel = findViewById(R.id.txtIDPEL);
        no_hp = findViewById(R.id.txtNo_HP);
        alamat = findViewById(R.id.txtAlamat);
        nik = findViewById(R.id.txtNIK);
        //setText
        nama.setText(preferences.getString("Nama", ""));
        idpel.setText(preferences.getString("IDPEL", ""));
        no_hp.setText(preferences.getString("no_hp", ""));
        alamat.setText(preferences.getString("Alamat", ""));
        nik.setText(preferences.getString("NIK", ""));

        //TextInputLayout
        txtNama = findViewById(R.id.Nama);
        txtIDPEL = findViewById(R.id.IDPEL);
        txtNo_Hp = findViewById(R.id.No_Hp);
        txtAlamat = findViewById(R.id.Alamat);
        txtNIK = findViewById(R.id.NIK);

        //Button
        Simpan = findViewById(R.id.Simpan);


        //addTextChangeListener
        /**  Daerah Kekuasaan addTextChangeListener (Start) **/
        nama.addTextChangedListener(new TextWatcher() {
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
                    txtNama.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtNama.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtNama.setErrorEnabled(false);
                }else{
                    txtNama.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtNama.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtNama.setError("Data Belum Terisi");
                }
            }
        });

        idpel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 11){
                    txtIDPEL.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtIDPEL.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtIDPEL.setErrorEnabled(false);
                }else{
                    txtIDPEL.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtIDPEL.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtIDPEL.setError("ID Pelanggan tidak valid");
                }
            }
        });

        no_hp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 11){
                    txtNo_Hp.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtNo_Hp.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtNo_Hp.setErrorEnabled(false);
                }else{
                    txtNo_Hp.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtNo_Hp.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtNo_Hp.setError("No Tidak Valid");
                }
            }
        });

        alamat.addTextChangedListener(new TextWatcher() {
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
                    txtAlamat.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtAlamat.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtAlamat.setErrorEnabled(false);
                }else{
                    txtAlamat.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtAlamat.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtAlamat.setError("Data belum terisi");
                }
            }
        });

        nik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 15){
                    txtNIK.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtNIK.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    txtNIK.setErrorEnabled(false);
                }else{
                    txtNIK.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtNIK.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    txtNIK.setError("NIK tidak valid");
                }
            }
        });

        /**  Daerah Kekuasaan addTextChangeListener (END) **/



        /**  Daerah Kekuasaan Layout Ubah Password (Start) **/

        ubah_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference("Pass");
            }
        });

        txtuabah_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference("Pass");
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference("Pass");
            }
        });

        /**  Daerah Kekuasaan Layout Ubah Password (END) **/

        /**  Daerah Kekuasaan Layout Ubah Email (Start) **/

        ubah_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference("Email");
                Intent i = new Intent(Profile.this,ValidasiPassword.class);
            }
        });

        txtuabah_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference("Email");
            }
        });

        arrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preference("Email");
            }
        });

        /**  Daerah Kekuasaan Layout Ubah Email (END) **/

        //BUTTON EVENT
        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama.getText().toString().length() > 0
                    && idpel.getText().toString().length() > 11
                    && no_hp.getText().toString().length() > 11
                    && alamat.getText().toString().length() > 0
                    && nik.getText().toString().length() > 15){
                    asynctask();
                }else{
                    showDialog();
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
                .setMessage("Data Belum Terlengkapi!")
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

    public void preference(String s){
        Intent i = new Intent(Profile.this,ValidasiPassword.class);
        i.putExtra("CekValidasi", s);
        startActivity(i);
    }



    public void asynctask(){
        final String getNama = nama.getText().toString();
        final String getIDPEL = idpel.getText().toString();
        final String getNoHP = no_hp.getText().toString();
        final String getAlamat = alamat.getText().toString();
        final String getNIK = nik.getText().toString();
        final String getEmail = preferences.getString("Email", "");
        final String tipe_pengadu = preferences.getString("Tipe_user", "");
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(JSON_NAMA, getNama);
                hashMap.put(JSON_IDPEL, getIDPEL);
                hashMap.put(JSON_NO_HP, getNoHP);
                hashMap.put(JSON_ALAMAT, getAlamat);
                hashMap.put(JSON_NIK, getNIK);
                hashMap.put(JSON_EMAIL, getEmail);
                hashMap.put(JSON_PENGADU, tipe_pengadu);
                RequestHandler requestHandler = new RequestHandler();
                String i = requestHandler.sendPostRequest(URL_PROFILE, hashMap);
                return i;
            }
        }.execute();
    }

    private void JSON(String json) {
        String response= "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_RESULT);
            JSONObject object = jsonArray.getJSONObject(0);
            response = object.getString(JSON_RESPONSE);

            if(response.equals("gagal")){
                new SweetAlertDialog(Profile.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Gagal mengupdate profile!")
                        .setNeutralText("Tutup")
                        .show();
            }
            if(response.equals("gagal2")){
                new SweetAlertDialog(Profile.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("ID Pelanggan tidak tercantum dalam data kami!")
                        .setConfirmText("Tutup")
                        .show();
            }

//            if(response.equals("gagal3")){
//                new SweetAlertDialog(Profile.this, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("Oops...")
//                        .setContentText("ID Pelanggan bukan milik Lembaga atau Instansi!")
//                        .setConfirmText("Tutup")
//                        .show();
//            }

            if(response.equals("gagal3")){
                new SweetAlertDialog(Profile.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("ID Pelanggan bukan milik Lembaga atau Instansi terkait!")
                        .setConfirmText("Tutup")
                        .show();
            }

            if(response.equals("berhasil")){
                preferences = getSharedPreferences("Data_Login", 0); // 0 = mode_private
                editor = preferences.edit();
                editor.putString("Nama", nama.getText().toString());
                editor.putString("NIK", nik.getText().toString());
                editor.putString("no_hp", no_hp.getText().toString());
                editor.putString("Alamat", alamat.getText().toString());
                editor.putString("IDPEL", idpel.getText().toString());
                editor.commit();
                    new SweetAlertDialog(Profile.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil mengupdate profile!")
                        .hideConfirmButton()
                        .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }

//            Toast.makeText(Login.this, response+""+name, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
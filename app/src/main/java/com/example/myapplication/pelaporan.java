package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class pelaporan extends AppCompatActivity {

    RelativeLayout ambilokasi;
    TextView txtAmbil_Lok1, plus;
    ProgressDialog load;
    AutoCompleteTextView kat_aduan;
    TextInputEditText desk;
    TextInputLayout tilnohp,tildesk;
    ImageView gambar;
    Button laporkan;
    List<String> dataList = new ArrayList<>();

    Bitmap bitmap;
    final int KODE_GALLERY_REQUEST = 999;

    //URL
    private String URL_KAT_ADUAN = "http://192.168.43.175/ci/sitaro_crud/getKatAduan.php";
    private String URL_SEND_ADUAN = "http://192.168.43.175/ci/sitaro_crud/aduan.php";

    //JSON
    private String JSON_STRING = "result";
    private String JSON_RESPONSE = "response";
    private String JSON_GAMBAR = "PHOTO";
    private String JSON_KOORDINAT = "KOORDINAT";
    private String JSON_DESK = "DESK";
    private String JSON_KD_KAT_ADUAN = "KD_KAT_ADUAN";
    private String JSON_ID_PEL = "IDPEL";

    // KEY
    private String KD_ADUAN = "kd_aduan";
    private String NM_ADUAN = "nm_aduan";

    private String LAT = "Latitude";
    private String LNG = "Longtitude";


    String getLat,getLng;

    //TEMP MEMORI
    String tempKD_ADUAN = "";

    private SharedPreferences preferences,preferences2;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelaporan);

        ambilokasi = findViewById(R.id.ambillokasi);
        kat_aduan = findViewById(R.id.kat);
        gambar = findViewById(R.id.gambar);
        txtAmbil_Lok1 = findViewById(R.id.txtambillokasi1);
        desk = findViewById(R.id.desk);
        laporkan = findViewById(R.id.laporkan);
        plus = findViewById(R.id.txtambillokasi2);

        getJSON();

        preferences = getSharedPreferences("Data_Pelaporan", Context.MODE_PRIVATE);
        preferences2 = getSharedPreferences("Data_Login", Context.MODE_PRIVATE);
        getLat = preferences.getString(LAT, "");
        getLng = preferences.getString(LNG, "");

        if((getLat != null && !getLat.equals("")) && (getLng != null && !getLng.equals(""))){
            txtAmbil_Lok1.setText(getLat+","+getLng);
            plus.setText(Html.fromHtml("&#xf304;"));
        }

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        pelaporan.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        KODE_GALLERY_REQUEST);
            }
        });

        ambilokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pelaporan.this,pick_location.class);
                startActivity(i);
            }
        });

        laporkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((getLat == null && getLat.equals(""))
                    || (getLng == null && getLng.equals(""))
                    || kat_aduan.getText().toString().length() == 0
                    || desk.getText().toString().length() == 0
                    || gambar.getDrawable().getConstantState() == gambar.getResources().getDrawable(R.drawable.upfoto).getConstantState()){
                    showDialog();
                }else{
                    simpanData();
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
                .setMessage("Field tidak boleh kosong!")
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

    private void getKATaduan(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_STRING);
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);

                dataList.add(object.getString(NM_ADUAN));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,dataList);
        kat_aduan.setAdapter(adapter);
    }

    private void getJSON() {
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                load = ProgressDialog.show(pelaporan.this, "Load","Get Data",false,false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                load.dismiss();
                getKATaduan(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                String ambil = requestHandler.sendGetRequest(URL_KAT_ADUAN);
                return ambil;
            }
        };
        asyncTask.execute();
    }


    public void exeJSON(String data){
        String response= "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_STRING);
            JSONObject object = jsonArray.getJSONObject(0);
            response = object.getString(JSON_RESPONSE);

            if(response.equals("gagal")){
                new SweetAlertDialog(pelaporan.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Gagal melakukan aduan!")
                        .setConfirmText("Tutup")
                        .show();
            }
            if(response.equals("ok")){
                editor = preferences.edit();
                editor.putString(LAT, "");
                editor.putString(LNG, "");
                editor.commit();
                new SweetAlertDialog(pelaporan.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil melakukan aduan!")
                        .hideConfirmButton()
                        .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                Intent i = new Intent(pelaporan.this, Status.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == KODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Select Image"), KODE_GALLERY_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(), "Tidak Mempunyai Akses", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == KODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                gambar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    //JSON
//    private String JSON_STRING = "result";
//    private String JSON_RESPONSE = "response";
//    private String JSON_GAMBAR = "PHOTO";
//    private String JSON_KOORDINAT = "KOORDINAT";
//    private String JSON_NO_HP = "NOHP";
//    private String JSON_DESK = "DESK";
//    private String JSON_KD_KAT_ADUAN = "KD_KAT_ADUAN";
//    private String JSON_ID_PEL = "IDPEL";

    private void simpanData(){
        final String gbr = gbrToStr(bitmap);
        final String koordinat = getLat+","+getLng;
        final String deskripsi = desk.getText().toString().trim();
        final String kategori_aduan = kat_aduan.getText().toString();
        final String IDPEL = preferences2.getString("IDPEL", "");
        new AsyncTask<Void,Void,String>(){
            
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                exeJSON(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(JSON_GAMBAR,gbr);
                hashMap.put(JSON_KOORDINAT, koordinat);
                hashMap.put(JSON_DESK, deskripsi);
                hashMap.put(JSON_KD_KAT_ADUAN, kategori_aduan);
                hashMap.put(JSON_ID_PEL, IDPEL);
                RequestHandler requestHandler = new RequestHandler();
                String i = requestHandler.sendPostRequest(URL_SEND_ADUAN,hashMap);
                return i;
            }
        }.execute();
    }

    private String gbrToStr(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] image = outputStream.toByteArray();
        String encode = Base64.encodeToString(image, Base64.DEFAULT);
        return encode;
    }

}
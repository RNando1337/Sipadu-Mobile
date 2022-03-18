package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditStatus extends AppCompatActivity {

    RelativeLayout ambilokasi;
    TextView txtAmbil_Lok1, plus;
    ProgressDialog load;
    AutoCompleteTextView kat_aduan;
    TextInputEditText desk;
    ImageView gambar;
    Button ubah;
    List<String> dataList = new ArrayList<>();

    Bitmap bitmap;
    final int KODE_GALLERY_REQUEST = 999;

    //URL
    private String URL_KAT_ADUAN = "http://192.168.43.175/ci/sitaro_crud/getKatAduan.php";
    private String URL_EDIT_STATUS = "http://192.168.43.175/ci/sitaro_crud/edit_status.php";

    //JSON Tags
    private String JSON_RESPONSE = "response";
    private String JSON_RESULT = "result";
    private String JSON_KD_ADUAN = "kd_pengaduan";
    private String JSON_KAT_ADUAN = "nm_pengaduan";
    private String JSON_DESK = "deskripsi";
    private String JSON_GAMBAR = "gambar";
    private String JSON_KOORDINAT = "koordinat";
    private String JSON_TANGGAL = "tanggal";
    private String JSON_IDPEL = "id_pelanggan";
    private String JSON_STATUS = "status";
    private String JSON_NAMA = "nama";

    // KEY
    private String KD_ADUAN = "kd_aduan";
    private String NM_ADUAN = "nm_aduan";

    private SharedPreferences preferences;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_status);

        ambilokasi = findViewById(R.id.ambillokasi); //RelativeLayout
        kat_aduan = findViewById(R.id.kat);
        gambar = findViewById(R.id.gambar);
        txtAmbil_Lok1 = findViewById(R.id.txtambillokasi1);  //TextView
        desk = findViewById(R.id.desk);
        ubah = findViewById(R.id.ubahaduan);
        plus = findViewById(R.id.txtambillokasi2);

        getJSON();
        preferences = getSharedPreferences("Data_Edit_Pelaporan", Context.MODE_PRIVATE);
        setItem();

        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        EditStatus.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        KODE_GALLERY_REQUEST);
            }
        });

        ambilokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditStatus.this,pick_location2.class);
                startActivity(i);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditStatus.this,pick_location2.class);
                startActivity(i);
            }
        });

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });


    }

    private void setItem(){
        kat_aduan.setText(preferences.getString(JSON_KAT_ADUAN, ""));
        txtAmbil_Lok1.setText(preferences.getString(JSON_KOORDINAT,""));
        desk.setText(preferences.getString(JSON_DESK,""));
    }

    private void getKATaduan(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_RESULT);
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
                load = ProgressDialog.show(EditStatus.this, "Load","Get Data",false,false);

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

    private String gbrToStr(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] image = outputStream.toByteArray();
        String encode = Base64.encodeToString(image, Base64.DEFAULT);
        return encode;
    }

    public void updateData(){
        final String kate_aduan = kat_aduan.getText().toString();
        final String koordinat = txtAmbil_Lok1.getText().toString();
        final String deskripsi = desk.getText().toString();
        final String kd_aduan = preferences.getString(JSON_KD_ADUAN, "");
        final String gbr;
        if (gambar.getDrawable().getConstantState() == gambar.getResources().getDrawable(R.drawable.upfoto).getConstantState()) {
            gbr = "Tidak ada gambar";
        } else {
            gbr = gbrToStr(bitmap);
        }

        new AsyncTask<Void,Void,String>(){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                new SweetAlertDialog(EditStatus.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil mengubah!")
                        .hideConfirmButton()
                        .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                Intent i = new Intent(EditStatus.this, com.example.myapplication.Status.class);
                                startActivity(i);
                                finish();
                            }
                        }).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(JSON_KD_ADUAN, kd_aduan);
                hashMap.put(JSON_KAT_ADUAN, kate_aduan);
                hashMap.put(JSON_KOORDINAT, koordinat);
                hashMap.put(JSON_GAMBAR, gbr);
                hashMap.put(JSON_DESK, deskripsi);
                RequestHandler requestHandler = new RequestHandler();
                String i = requestHandler.sendPostRequest(URL_EDIT_STATUS, hashMap);
                return i;
            }
        }.execute();
    }

}
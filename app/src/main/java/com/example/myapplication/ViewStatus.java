package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ViewStatus extends AppCompatActivity {

    ImageView gambar;
    TextView kat_aduan,tanggal_aduan,status,desk,no_hp,nm_pengadu,petugas;
    Button lihat_response;

    //JSON Tags
    private String JSON_RESPONSE = "response";
    private String JSON_RESULT = "result";
    private String JSON_KD_ADUAN = "kd_pengaduan";
    private String JSON_KAT_ADUAN = "nm_pengaduan";
    private String JSON_DESK = "deskripsi";
    private String JSON_GAMBAR = "gambar";
    private String JSON_NO_HP = "no_hp";
    private String JSON_KOORDINAT = "koordinat";
    private String JSON_TANGGAL = "tanggal";
    private String JSON_IDPEL = "id_pelanggan";
    private String JSON_STATUS = "status";
    private String JSON_NAMA = "nama";
    private String JSON_NAMA_PETUGAS = "nama_petugas";
    private String JSON_BUKTI_PHOTO = "bukti_foto";
    private String JSON_DESK_PETUGAS = "deskripsi_petugas";
    private String JSON_VERTIFIKASI = "verifikasi";

    //Shared inisialisasi Preferance
    private SharedPreferences preferences;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_status);

        gambar = findViewById(R.id.gambar);
        kat_aduan = findViewById(R.id.kat_aduan);
        tanggal_aduan = findViewById(R.id.Tanggal_aduan);
        status = findViewById(R.id.Status);
        desk = findViewById(R.id.desk);
        no_hp = findViewById(R.id.No_Hp);
        nm_pengadu = findViewById(R.id.nm_pengadu);
        petugas = findViewById(R.id.Petugas);
        lihat_response = findViewById(R.id.LihatResponse);


        setItem();



    }

    private void setItem(){
        preferences = getSharedPreferences("Data_Status", Context.MODE_PRIVATE);
        Intent i = getIntent();
        Picasso.with(ViewStatus.this)
                .load("http://192.168.43.175/CI/sitaro_crud/"+preferences.getString(JSON_GAMBAR, ""))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit().centerInside()
                .into(gambar);
        kat_aduan.setText(preferences.getString(JSON_KAT_ADUAN, ""));
        tanggal_aduan.setText(Html.fromHtml("&#xf073;")+" "+preferences.getString(JSON_TANGGAL, ""));
        if(preferences.getString(JSON_STATUS, "").equals("Diterima")){
            status.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.hijau)));
            status.setText(Html.fromHtml("&#xf058;")+" Diterima");
        }else if(preferences.getString(JSON_STATUS, "").equals("Ditolak")){
            status.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.merah)));
            status.setText(Html.fromHtml("&#xf05e;")+" Ditolak");
        }else if(preferences.getString(JSON_STATUS, "").equals("Diproses")){
            status.setText(Html.fromHtml("&#xf017;")+" Diproses");
        }else if(preferences.getString(JSON_STATUS, "").equals("Ditindaklanjuti")){
            status.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.warning)));
            status.setText(preferences.getString(JSON_STATUS, ""));
        }
        no_hp.setText(Html.fromHtml("&#xf879;")+" "+preferences.getString(JSON_NO_HP, ""));
        nm_pengadu.setText(Html.fromHtml("&#xf2bb;")+" "+preferences.getString(JSON_NAMA, ""));
        if(preferences.getString(JSON_NAMA_PETUGAS, "").equalsIgnoreCase("null")){
            petugas.setText(Html.fromHtml("&#xf509;") + " Belum ada verifikator");
        }else {
            petugas.setText(Html.fromHtml("&#xf509;") + " " + preferences.getString(JSON_NAMA_PETUGAS, ""));
        }
        desk.setText(preferences.getString(JSON_DESK, ""));
        if(preferences.getString(JSON_BUKTI_PHOTO, "").equalsIgnoreCase("") || preferences.getString(JSON_BUKTI_PHOTO, "") == ""
        || preferences.getString(JSON_BUKTI_PHOTO, "").equalsIgnoreCase(" ") || preferences.getString(JSON_BUKTI_PHOTO, "") == " "){
            lihat_response.setVisibility(View.GONE);
        }else{
            lihat_response.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ViewStatus.this,ResponsePetugas.class);
                    i.putExtra(JSON_VERTIFIKASI,preferences.getString(JSON_VERTIFIKASI, ""));
                    i.putExtra(JSON_BUKTI_PHOTO,preferences.getString(JSON_BUKTI_PHOTO, ""));
                    i.putExtra(JSON_DESK_PETUGAS,preferences.getString(JSON_DESK_PETUGAS, ""));
                    startActivity(i);
                }
            });
        }
    }

}
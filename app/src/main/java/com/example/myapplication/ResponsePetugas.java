package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ResponsePetugas extends AppCompatActivity {

    TextView Desk,Tanggal;
    ImageView gambar;

    private String JSON_BUKTI_PHOTO = "bukti_foto";
    private String JSON_DESK_PETUGAS = "deskripsi_petugas";
    private String JSON_VERTIFIKASI = "verifikasi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_petugas);

        Intent i = getIntent();
        Desk = findViewById(R.id.desk);
        gambar = findViewById(R.id.gambar);
        Tanggal = findViewById(R.id.Tanggal_verifikasi);

        Tanggal.setText("Diverifikasi tanggal : "+i.getStringExtra(JSON_VERTIFIKASI));
        Picasso.with(ResponsePetugas.this)
                .load("http://192.168.43.175/CI/sitaro_crud/"+i.getStringExtra(JSON_BUKTI_PHOTO))
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit().centerInside()
                .into(gambar);
        Desk.setText(i.getStringExtra(JSON_DESK_PETUGAS));


    }
}
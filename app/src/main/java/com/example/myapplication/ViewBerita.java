package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ViewBerita extends AppCompatActivity {

    private ImageView gambar;
    private TextView jdlbrt,tgl,desk;

    private String JSON_RESPONSE = "response";
    private String JSON_KD_BRT = "kd_ber";
    private String JSON_JDL_BRT = "judul";
    private String JSON_DESK_BRT = "informasi";
    private String JSON_GAMBAR = "gambar";
    private String JSON_KD_PET = "kd_petugas";
    private String JSON_TIMEUPLOAD = "timeupload";
    private String JSON_RESULT = "result";

    String jdl_bar,desk_bar,gbr_bar,tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_berita);

        Intent i = getIntent();
        jdl_bar = i.getStringExtra(JSON_JDL_BRT);
        desk_bar = i.getStringExtra(JSON_DESK_BRT);
        gbr_bar = i.getStringExtra(JSON_GAMBAR);
        tanggal = i.getStringExtra(JSON_TIMEUPLOAD);

        gambar = findViewById(R.id.gambar);
        jdlbrt = findViewById(R.id.jdlbrt);
        tgl = findViewById(R.id.tgl);
        desk = findViewById(R.id.desk);

        setItem();


    }

    private void setItem(){
        Picasso.with(ViewBerita.this)
                .load("http://192.168.43.175/CI/sitaro_crud/"+gbr_bar)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit().centerInside()
                .into(gambar);
        jdlbrt.setText(jdl_bar);
        tgl.setText(Html.fromHtml("&#xf073;")+"  "+tanggal);
        desk.setText(desk_bar);
    }

}
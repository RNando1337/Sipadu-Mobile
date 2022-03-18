package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TipePengadu extends AppCompatActivity {

    RelativeLayout menu1,menu2;
    ImageButton mngb1,mngb2;
    ImageView back;

    //Shared inisialisasi Preferance
    private SharedPreferences preferences;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_pengadu);

        menu1 = findViewById(R.id.menus1);
        menu2 = findViewById(R.id.menus2);
        mngb1 = findViewById(R.id.mngb1);
        mngb2 = findViewById(R.id.mngb2);
        back = findViewById(R.id.arrow);

        //membuat data/file baru
        preferences = getSharedPreferences("Data_Login", Context.MODE_PRIVATE);

        LoginIsNotNull();

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences("Masyarakat");
                Intent intent = new Intent(TipePengadu.this, Login.class);
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences("Lembaga");
                Intent intent = new Intent(TipePengadu.this, Login.class);
                startActivity(intent);
            }
        });

        mngb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences("Masyarakat");
                Intent intent = new Intent(TipePengadu.this, Login.class);
                startActivity(intent);
            }
        });

        mngb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences("Lembaga");
                Intent intent = new Intent(TipePengadu.this, Login.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TipePengadu.this,home.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void LoginIsNotNull(){
        String getName = preferences.getString("Nama", "");
        if(getName != null && !getName.equals("")){
            Intent i = new Intent(TipePengadu.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void setPreferences(String tipe){
        editor = preferences.edit();
        editor.putString("Tipe_user", tipe);
        editor.commit();
    }

}
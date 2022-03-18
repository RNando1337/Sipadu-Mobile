package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class home extends AppCompatActivity {

    RelativeLayout menu1,menu2,menu3;
    ImageButton mngb1,mngb2,mngb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menu1 = findViewById(R.id.menus1);
        menu2 = findViewById(R.id.menus2);
        menu3 = findViewById(R.id.menus3);
        mngb1 = findViewById(R.id.mngb1);
        mngb2 = findViewById(R.id.mngb2);
        mngb3 = findViewById(R.id.mngb3);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, TipePengadu.class);
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Berita.class);
                startActivity(intent);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, AboutUs.class);
                startActivity(intent);
            }
        });

        mngb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, TipePengadu.class);
                startActivity(intent);
            }
        });

        mngb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Berita.class);
                startActivity(intent);
            }
        });

        mngb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, AboutUs.class);
                startActivity(intent);
            }
        });

    }

    

}
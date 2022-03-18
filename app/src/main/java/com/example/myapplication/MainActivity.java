package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout menu1,menu2,menu3,menu4;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);
        menu4 = findViewById(R.id.menu4);
        UserName = findViewById(R.id.UserName);
        sharedPreferences = getSharedPreferences("Data_Login", 0);
        UserName.setText(sharedPreferences.getString("Nama", ""));
        if(sharedPreferences.getString("NIK", "") == ""
                || sharedPreferences.getString("no_hp", "") == ""
                || sharedPreferences.getString("Alamat", "") == ""
                || sharedPreferences.getString("IDPEL", "") == ""
                || sharedPreferences.getString("NIK", "").equals("")
                || sharedPreferences.getString("no_hp", "").equals("")
                || sharedPreferences.getString("Alamat", "").equals("")
                || sharedPreferences.getString("IDPEL", "").equals("")){
            showDialog();
        }
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            Log.d("Debug", "GPS Aktif");
//        }else{
//            showGPSDisabledAlertToUser();
//        }



        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,pelaporan.class);
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Status.class);
                startActivity(intent);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
            }
        });

        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
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
                .setMessage("Lengkapi data sebelum melakukan pengaduan!")
                .setIcon(R.drawable.logo_pln)
                .setCancelable(false)
                .setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logout();
                    }
                })
                .setNeutralButton("Lengkapi!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(MainActivity.this,Profile.class);
                        startActivity(in);
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();

    }

    private void Logout(){
        sharedPreferences = getSharedPreferences("Data_Login", 0);
        editor = sharedPreferences.edit();
        editor.putString("Nama", "");
        editor.putString("Email", "");
        editor.putString("NIK", "");
        editor.putString("no_hp", "");
        editor.putString("Alamat", "");
        editor.putString("IDPEL", "");
        editor.commit();
        Intent i = new Intent(MainActivity.this,Login.class);
        startActivity(i);
        finish();
    }

//    private void showGPSDisabledAlertToUser(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("GPS dinonaktifkan di perangkat Anda. Apakah Anda ingin mengaktifkannya?")
//                .setCancelable(false)
//                .setPositiveButton("Klik dan Aktifkan GPS",
//                        new DialogInterface.OnClickListener(){
//                            public void onClick(DialogInterface dialog, int id){
//                                Intent callGPSSettingIntent = new Intent(
//                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                startActivity(callGPSSettingIntent);
//                            }
//                        });
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//    }

}
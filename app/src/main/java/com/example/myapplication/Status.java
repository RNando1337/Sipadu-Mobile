package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.cardemulation.HostApduService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Status extends AppCompatActivity implements LaporAdapter.ItemClickListener{

    RecyclerView recyclerView;
    TextInputEditText cari;
    LaporAdapter laporAdapter;
    ArrayList<DataLaporan> dataLaporans;

    private String URL_GET_STATUS = "http://192.168.43.175/CI/sitaro_crud/Status.php";
    private String URL_DELETE_STATUS = "http://192.168.43.175/CI/sitaro_crud/delete_status.php";
    //JSON Tags
    private String JSON_RESPONSE = "response";
    private String JSON_RESULT = "result";
    private String JSON_KD_ADUAN = "kd_pengaduan";
    private String JSON_KAT_ADUAN = "nm_pengaduan";
    private String JSON_DESK = "deskripsi";
    private String JSON_GAMBAR = "gambar";
    private String JSON_KOORDINAT = "koordinat";
    private String JSON_NO_HP = "no_hp";
    private String JSON_TANGGAL = "tanggal";
    private String JSON_IDPEL = "id_pelanggan";
    private String JSON_STATUS = "status";
    private String JSON_NAMA = "nama";
    private String JSON_NAMA_PETUGAS = "nama_petugas";
    private String JSON_BUKTI_PHOTO = "bukti_foto";
    private String JSON_DESK_PETUGAS = "deskripsi_petugas";
    private String JSON_VERTIFIKASI = "verifikasi";

    private String TAG_DATA = "DATA";
    private String TAG_IDPEL = "IDPEL";

    private String LAT = "Latitude";
    private String LNG = "Longtitude";

    //Shared inisialisasi Preferance
    private SharedPreferences preferences,preferences2,preferences3;

    // Shared Konfigurasi
    private SharedPreferences.Editor editor;

    String IDPEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        cari = findViewById(R.id.txtcari);

        recyclerView = findViewById(R.id.recycler_view);
        dataLaporans = new ArrayList<>();

        //membuat data/file baru
        preferences = getSharedPreferences("Data_Login", Context.MODE_PRIVATE);
        IDPEL = preferences.getString("IDPEL", "");

        ambildata("",IDPEL);


        cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(cari.getText().toString().trim().length() >= 1){
                    dataLaporans.clear();
                    ambildata(cari.getText().toString().trim(),IDPEL);
                }else{
                    dataLaporans.clear();
                    ambildata("",IDPEL);
                }
            }
        });

    }

    public void getJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(JSON_RESULT);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                DataLaporan d = new DataLaporan(jo.getString(JSON_KD_ADUAN),
                        jo.getString(JSON_KAT_ADUAN),
                        jo.getString(JSON_DESK),
                        jo.getString(JSON_GAMBAR),
                        jo.getString(JSON_STATUS),
                        jo.getString(JSON_KOORDINAT),
                        jo.getString(JSON_NO_HP),
                        jo.getString(JSON_TANGGAL),
                        jo.getString(JSON_IDPEL),
                        jo.getString(JSON_NAMA),
                        jo.getString(JSON_NAMA_PETUGAS),
                        jo.getString(JSON_BUKTI_PHOTO),
                        jo.getString(JSON_VERTIFIKASI),
                        jo.getString(JSON_DESK_PETUGAS));
                dataLaporans.add(d);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        laporAdapter = new LaporAdapter(dataLaporans,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        laporAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(laporAdapter);
        laporAdapter.setClickListener(this);
    }

    public void ambildata(String data,String IDPEL){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getJSON(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_DATA, data);
                hashMap.put(TAG_IDPEL, IDPEL);
                RequestHandler requestHandler = new RequestHandler();
                String i = requestHandler.sendPostRequest(URL_GET_STATUS, hashMap);
                return i;
            }
        }.execute();
    }

    @Override
    public void onClick(View v, int position) {
        if(v.getId() == R.id.cog){
            String[] array_pos = dataLaporans.get(position).getKoordinat().split(",");
            if(dataLaporans.get(position).getStatus().equalsIgnoreCase("Diterima")
            || dataLaporans.get(position).getStatus().equalsIgnoreCase("Ditolak")
            || dataLaporans.get(position).getStatus().equalsIgnoreCase("Ditindaklanjuti")){
                new SweetAlertDialog(Status.this)
                        .setTitleText("Pilih Menu")
                        .setConfirmText("Detail")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                preferences3 = getSharedPreferences("Data_Status", Context.MODE_PRIVATE);
                                editor = preferences3.edit();
                                editor.putString(JSON_KAT_ADUAN, dataLaporans.get(position).getKat_aduan());
                                editor.putString(JSON_TANGGAL, dataLaporans.get(position).getTanggal());
                                editor.putString(JSON_STATUS, dataLaporans.get(position).getStatus());
                                editor.putString(JSON_DESK, dataLaporans.get(position).getDesk());
                                editor.putString(JSON_GAMBAR, dataLaporans.get(position).getGambar());
                                editor.putString(JSON_NO_HP, dataLaporans.get(position).getNo_hp());
                                editor.putString(JSON_NAMA, dataLaporans.get(position).getNama());
                                editor.putString(JSON_NAMA_PETUGAS, dataLaporans.get(position).getNama_petugas());
                                editor.putString(JSON_BUKTI_PHOTO, dataLaporans.get(position).getBukti_foto());
                                editor.putString(JSON_VERTIFIKASI, dataLaporans.get(position).getVerifikasi());
                                editor.putString(JSON_DESK_PETUGAS, dataLaporans.get(position).getResponse());
                                editor.commit();
                                Intent i = new Intent(Status.this, ViewStatus.class);
                                startActivity(i);
                            }
                        })
                        .show();
            }else {
                new SweetAlertDialog(Status.this)
                        .setTitleText("Pilih Menu")
                        .setConfirmText("Ubah")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                preferences2 = getSharedPreferences("Data_Edit_Pelaporan", Context.MODE_PRIVATE);
                                editor = preferences2.edit();
                                editor.putString(JSON_KD_ADUAN, dataLaporans.get(position).getKD_ADUAN());
                                editor.putString(JSON_KAT_ADUAN, dataLaporans.get(position).getKat_aduan());
                                editor.putString(JSON_KOORDINAT, dataLaporans.get(position).getKoordinat());
                                editor.putString(LAT, array_pos[0]);
                                editor.putString(LNG, array_pos[1]);
                                editor.putString(JSON_DESK, dataLaporans.get(position).getDesk());
                                editor.commit();
                                Intent i = new Intent(Status.this, EditStatus.class);
                                startActivity(i);
                            }
                        })
                        .setNeutralText("Detail")
                        .setNeutralClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                preferences3 = getSharedPreferences("Data_Status", Context.MODE_PRIVATE);
                                editor = preferences3.edit();
                                editor.putString(JSON_KAT_ADUAN, dataLaporans.get(position).getKat_aduan());
                                editor.putString(JSON_TANGGAL, dataLaporans.get(position).getTanggal());
                                editor.putString(JSON_STATUS, dataLaporans.get(position).getStatus());
                                editor.putString(JSON_DESK, dataLaporans.get(position).getDesk());
                                editor.putString(JSON_GAMBAR, dataLaporans.get(position).getGambar());
                                editor.putString(JSON_NO_HP, dataLaporans.get(position).getNo_hp());
                                editor.putString(JSON_NAMA, dataLaporans.get(position).getNama());
                                editor.putString(JSON_NAMA_PETUGAS, dataLaporans.get(position).getNama_petugas());
                                editor.putString(JSON_BUKTI_PHOTO, dataLaporans.get(position).getBukti_foto());
                                editor.putString(JSON_VERTIFIKASI, dataLaporans.get(position).getVerifikasi());
                                editor.putString(JSON_DESK_PETUGAS, dataLaporans.get(position).getResponse());
                                editor.commit();
                                Intent i = new Intent(Status.this, ViewStatus.class);
                                //                            i.putExtra(JSON_KAT_ADUAN,dataLaporans.get(position).getKat_aduan());
                                //                            i.putExtra(JSON_TANGGAL,dataLaporans.get(position).getTanggal());
                                //                            i.putExtra(JSON_STATUS,dataLaporans.get(position).getStatus());
                                //                            i.putExtra(JSON_DESK,dataLaporans.get(position).getDesk());
                                //                            i.putExtra(JSON_GAMBAR,dataLaporans.get(position).getGambar());
                                //                            i.putExtra(JSON_NO_HP,dataLaporans.get(position).getNo_hp());
                                //                            i.putExtra(JSON_NAMA,dataLaporans.get(position).getNama());
                                //                            i.putExtra(JSON_NAMA_PETUGAS,dataLaporans.get(position).getNama_petugas());
                                //                            i.putExtra(JSON_BUKTI_PHOTO,dataLaporans.get(position).getBukti_foto());
                                //                            i.putExtra(JSON_DESK_PETUGAS,dataLaporans.get(position).getResponse());
                                startActivity(i);
                            }
                        })
                        .show();
            }
        }
        if(v.getId() == R.id.hapus){
            new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Anda Yakin?")
                    .setContentText("Data yang terhapus akan hilang!")
                    .setConfirmText("Hapus")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            new AsyncTask<Void, Void, String>() {
                                @Override
                                protected void onPostExecute(String s) {
                                    super.onPostExecute(s);
                                    new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Berhasil menghapus!")
                                            .hideConfirmButton()
                                            .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
//                                                    HapusData("");
                                                    dataLaporans.clear();
                                                    ambildata("",IDPEL);
                                                }
                                            })
                                            .show();
                                }

                                @Override
                                protected String doInBackground(Void... voids) {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put(JSON_KD_ADUAN, dataLaporans.get(position).getKD_ADUAN().trim());
                                    RequestHandler rh = new RequestHandler();
                                    String s = rh.sendPostRequest(URL_DELETE_STATUS, hashMap);
                                    return s;
                                }
                            }.execute();
                        }
                    })
                    .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }
}
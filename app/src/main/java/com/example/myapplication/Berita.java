package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Berita extends AppCompatActivity implements BeritaAdapter.ItemClickListener{

    TextInputEditText cari;
    RecyclerView recyclerView;

    //Adapter
    BeritaAdapter beritaAdapter;

    // Array untuk Adapter
    private ArrayList<ModelBerita> modelBeritas;

    private String JSON_RESPONSE = "response";
    private String JSON_KD_BRT = "kd_ber";
    private String JSON_JDL_BRT = "judul";
    private String JSON_DESK_BRT = "informasi";
    private String JSON_GAMBAR = "gambar";
    private String JSON_KD_PET = "kd_petugas";
    private String JSON_TIMEUPLOAD = "timeupload";
    private String JSON_RESULT = "result";

    private String URL_BERITA = "http://192.168.43.175/ci/sitaro_crud/list_berita_pelanggan.php";

    //KEY HASHMAP
    private String TAG_SEARCH = "DATA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);

        cari = findViewById(R.id.txtcari);
        recyclerView = findViewById(R.id.recycler_view);
        modelBeritas = new ArrayList<>();

        aksesData("");

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
                    modelBeritas.clear();
                    aksesData(cari.getText().toString().trim());
                }else{
                    modelBeritas.clear();
                    aksesData("");
                }
            }
        });

    }

    public void aksesJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(JSON_RESULT);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                ModelBerita d = new ModelBerita(jo.getString(JSON_KD_BRT),
                        jo.getString(JSON_JDL_BRT),
                        jo.getString(JSON_DESK_BRT),
                        jo.getString(JSON_GAMBAR),
                        jo.getString(JSON_TIMEUPLOAD));
                modelBeritas.add(d);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        beritaAdapter = new BeritaAdapter(modelBeritas,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        beritaAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(beritaAdapter);
        beritaAdapter.setClickListener(this);
    }

    public void aksesData(String data){
        new AsyncTask<Void,Void,String>(){

//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                dialog = ProgressDialog.show(Kategori.this,"Loading...", "Get Data",false, false);
//            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                dialog.dismiss();
                aksesJSON(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_SEARCH, data);
                RequestHandler requestHandler = new RequestHandler();
                String input = requestHandler.sendPostRequest(URL_BERITA,hashMap);
                return input;
            }
        }.execute();
    }


    @Override
    public void onClick(View v, int position) {
        if(R.id.image == v.getId()
            || R.id.jdlBrt == v.getId()
            || R.id.tgl == v.getId()
            || R.id.textDescription == v.getId()
            || R.id.view == v.getId()){
            Intent i = new Intent(Berita.this, ViewBerita.class);
            i.putExtra(JSON_JDL_BRT, modelBeritas.get(position).getJdl_brt());
            i.putExtra(JSON_DESK_BRT, modelBeritas.get(position).getInformasi());
            i.putExtra(JSON_GAMBAR, modelBeritas.get(position).getGambar());
            i.putExtra(JSON_TIMEUPLOAD, modelBeritas.get(position).getTimeupload());
            startActivity(i);
        }
    }
}
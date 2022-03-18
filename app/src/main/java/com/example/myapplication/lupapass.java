package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class lupapass extends AppCompatActivity {

    TextInputEditText email;
    TextInputLayout Emails;
    ImageView back;
    Button forgot;
    private String JSON_RESULT = "result";
    private String JSON_RESPONSE = "response";
    private String JSON_EMAIL = "email";
    private String URL_RESET = "http://192.168.43.175/CI/Api/ubahpass";

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapass);

        back = findViewById(R.id.arrow);
        email = findViewById(R.id.emails);
        forgot = findViewById(R.id.forgot);

        Emails = findViewById(R.id.Email);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {
                if(email.getText().toString().trim().matches(emailPattern) && s.length() > 0){
                    Emails.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    Emails.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.primary)));
                    Emails.setErrorEnabled(false);
                }else{
                    Emails.setBoxStrokeColorStateList(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    Emails.setDefaultHintTextColor(ColorStateList.valueOf(getApplicationContext().getColor(R.color.merah)));
                    Emails.setError("Email tidak valid");
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim().length() == 0){
                    new SweetAlertDialog(lupapass.this, SweetAlertDialog.WARNING_TYPE)
                            .setContentText("Data belum terisi!")
                            .setCancelText("Tutup")
                            .show();
                }else{
                    reset();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lupapass.this,Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void getData(String json){
        String response= "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_RESULT);
            JSONObject object = jsonArray.getJSONObject(0);
            response = object.getString(JSON_RESPONSE);

            if(response.equals("Gagal")){
                new SweetAlertDialog(lupapass.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Gagal melakukan reset!")
                        .setCancelText("Tutup")
                        .show();
            }
            if(response.equals("Berhasil")){
                new SweetAlertDialog(lupapass.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Berhasil melakukan reset!")
                        .hideConfirmButton()
                        .setNeutralButton("Tutup", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                Intent i = new Intent(lupapass.this, Login.class);
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

    private void reset() {
        final String users = email.getText().toString();
        AsyncTask<Void,Void,String> asyncTask = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getData(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(JSON_EMAIL, users);
                RequestHandler requestHandler = new RequestHandler();
                String input = requestHandler.sendPostRequest(URL_RESET,hashMap);
                return input;
            }
        };
        asyncTask.execute();
    }
}
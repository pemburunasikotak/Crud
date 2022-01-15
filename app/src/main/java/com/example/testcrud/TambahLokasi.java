package com.example.testcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testcrud.Model.ModelCreateLokasi;
import com.example.testcrud.koneksi.ApiConfig;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahLokasi extends AppCompatActivity {

    private Button btnsimpan;
    private ImageView btnkembali;
    private EditText textalamat, textnamalloakasi;
    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lokasi);
        btnsimpan = findViewById(R.id.btn_Simpan);
        btnkembali = findViewById(R.id.btn_kembali);
        textalamat = findViewById(R.id.et_namaLokasi);
        textnamalloakasi = findViewById(R.id.et_simpannamalokasi);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahdata();
            }


        });
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahLokasi.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    private void tambahdata() {
        if (textalamat.getText().toString().isEmpty()){
            textalamat.setError("alamat harap diisi");

        }
        if(textnamalloakasi.getText().toString().isEmpty()){
            textnamalloakasi.setError("nama lokasi harus diisi");
        }


        String nama_lokasi = textalamat.getText().toString();
        String alamat = textnamalloakasi.getText().toString();
        if(!nama_lokasi.trim().isEmpty()&&!alamat.isEmpty()){
            ApiConfig.getService(TambahLokasi.this).postCreateLogin(3,nama_lokasi,alamat).enqueue(new Callback<ModelCreateLokasi>() {
                @Override
                public void onResponse(Call<ModelCreateLokasi> call, Response<ModelCreateLokasi> response) {
                    if (response.isSuccessful()){
                        ModelCreateLokasi createlokasi = response.body();
                        if (createlokasi.status){
                            Toast.makeText(TambahLokasi.this,"Tambah berhasil",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TambahLokasi.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(TambahLokasi.this, createlokasi.message,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ModelCreateLokasi> call, Throwable t) {
                    Toast.makeText(TambahLokasi.this,"gagal",Toast.LENGTH_SHORT).show();
                    Log.e("error",t.getMessage());
                }
            });
        }


    }
}
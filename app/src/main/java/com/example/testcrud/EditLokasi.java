package com.example.testcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testcrud.Model.ModelCreateLokasi;
import com.example.testcrud.Model.ModelUpdate;
import com.example.testcrud.koneksi.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLokasi extends AppCompatActivity {

    public EditText et_editalamat,et_editnamalokasi;
    public EditText tv_id;
    public int id_lokasi ;
    public String nama_lokasi, alamat;
    public Button edit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_loakasi);
        et_editalamat = findViewById(R.id.et_editalamatlokasi);
        et_editnamalokasi = findViewById(R.id.et_editnamaLokasi);
        tv_id = findViewById(R.id.tv_id);
        Bundle bundle = getIntent().getExtras();


        alamat = bundle.getString("alamat");
        nama_lokasi= bundle.getString("nama");
        id_lokasi = bundle.getInt("id");

        et_editalamat.setText(alamat);
        et_editnamalokasi.setText(nama_lokasi);
//        tv_id.setText(bundle.getInt("id"));



        edit = findViewById(R.id.btn_update);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void EditData() {
        if (et_editnamalokasi.getText().toString().isEmpty()){
            et_editnamalokasi.setError("nama lokasi harus diisi");
        }
        if(et_editalamat.getText().toString().isEmpty()){
            et_editalamat.setError("alamat harap diisi");
        }
        String nama_lokasi = et_editnamalokasi.getText().toString();
        String alamat = et_editalamat.getText().toString();
        if(!nama_lokasi.trim().isEmpty()&&!alamat.isEmpty()){
            ApiConfig.getService(EditLokasi.this).postUpdateLogin(id_lokasi,nama_lokasi,alamat).enqueue(new Callback<ModelUpdate>() {
                @Override
                public void onResponse(Call<ModelUpdate> call, Response<ModelUpdate> response) {
                    if (response.isSuccessful()){
                        ModelUpdate update = response.body();
                        if (update.status){
                            Toast.makeText(EditLokasi.this,"Edit data berhasil",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditLokasi.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(EditLokasi.this, update.message,Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelUpdate> call, Throwable t) {
                    Toast.makeText(EditLokasi.this,"gagal",Toast.LENGTH_SHORT).show();
                    Log.e("error",t.getMessage());
                }
            });
        }

    }
}
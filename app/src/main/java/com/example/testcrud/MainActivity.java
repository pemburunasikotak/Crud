package com.example.testcrud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcrud.Model.ModelLokasi;
import com.example.testcrud.koneksi.ApiConfig;
import com.example.testcrud.koneksi.Interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private Integer id_user;
    private LokasiAdapter adapter;
    private Button btnTambah;
    private EditText pencarian;
    private boolean statusDaftarProdukSdhTerambil = false;
    private Interfaces interfaces;
//    private ModelLokasi listlokasi;
    private List<ModelLokasi.ListLokasi> listlokasi;
//
//    private static final String BASE_URL = "http://solar.goffi.co.id/api/";
//    public static final String token = "d733827e18db1ff38c92152c59b6076c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_user = 3;
        btnTambah = findViewById(R.id.btn_tambahloaksi);
        pencarian = findViewById(R.id.txtPencarian);

        rv = findViewById(R.id.rv_list);
        rv.setVisibility(View.VISIBLE);
        rv.setHasFixedSize(true);

        btnTambah.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, TambahLokasi.class));
                finish();
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // token = ApiConfig.TOKEN_API;


        //Setinggan base URL
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        Request request =chain.request().newBuilder().addHeader("token","d733827e18db1ff38c92152c59b6076c").build();
//                        return chain.proceed(request);
//                    }
//                })
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        //interfaces = retrofit.create(Interfaces.class);
        //test();
        fungRecyleView();
        pencarian.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String kataKunciPencarian = pencarian.getText().toString();
                    if(!kataKunciPencarian.isEmpty()){
                        Toast.makeText(MainActivity.this, "Kata kunci belum diisi", Toast.LENGTH_SHORT).show();
                        Intent intentDaftarProdukActivity = new Intent(MainActivity.this, Produk.class);
                       // intentDaftarProdukActivity.putExtra("mode", "pencarian");
                        intentDaftarProdukActivity.putExtra("kata_kunci", kataKunciPencarian);
                        startActivity(intentDaftarProdukActivity);
                    }
                }

                return true;
            }
        });
    }

    private void fungRecyleView() {
        ApiConfig.getService(MainActivity.this).getDaftarLokasi(id_user).enqueue(new Callback<ModelLokasi>() {
            @Override
            public void onResponse(Call<ModelLokasi> call, Response<ModelLokasi> response) {
                if (response.isSuccessful()){
                    ModelLokasi respon = response.body();
                    listlokasi = response.body().listlokasi;
                    adapter = new LokasiAdapter(MainActivity.this, listlokasi);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rv.setItemAnimator(new DefaultItemAnimator());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelLokasi> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
}

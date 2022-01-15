package com.example.testcrud;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testcrud.Model.ModelLokasi;
import com.example.testcrud.koneksi.ApiConfig;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Produk extends AppCompatActivity {

    private List<ModelLokasi.ListLokasi> listLokasi;
    private LokasiAdapter adapter;
    private RecyclerView recyclerView;
    private Integer id_user;
    private TextView Judul;
    private EditText txtPencarian;
    private List<ModelLokasi.ListLokasi> listSemuaProduk;
    private List<ModelLokasi.ListLokasi> listProdukTerfilter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        recyclerView = findViewById(R.id.rv_listdata);
        id_user = 3;
        Judul = findViewById(R.id.txvJudul);
        txtPencarian = findViewById(R.id.txtPencarian);


        txtPencarian.setText(getIntent().getStringExtra("kata_kunci"));

        getDaftarProduk();
    }
    private void filterProdukBerdasarKataPencarian(String kataKunci){
        listProdukTerfilter.clear();
        for (ModelLokasi.ListLokasi temp:listSemuaProduk){
            if(temp.nama_lokasi.toLowerCase(Locale.ROOT).contains(kataKunci.toLowerCase()) || temp.nama_lokasi.toLowerCase().contains(kataKunci.toLowerCase()))
                listProdukTerfilter.add(temp);
        }
        //listLokasi = response.body().listlokasi;
        adapter = new LokasiAdapter(Produk.this, listLokasi);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Produk.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    private void getDaftarProduk() {
        ApiConfig.getService(Produk.this).getDaftarLokasi(id_user).enqueue(new Callback<ModelLokasi>() {
            @Override
            public void onResponse(Call<ModelLokasi> call, Response<ModelLokasi> response) {
                if (response.isSuccessful()) {
                    ModelLokasi responseDaftarProduk = response.body();
                    listLokasi = responseDaftarProduk.listlokasi;
                    txtPencarian.setText(getIntent().getStringExtra("kata_kunci"));
                    String cari = txtPencarian.getText().toString();
                    filterProdukBerdasarKataPencarian(cari);
                }
            }
            @Override
            public void onFailure(Call<ModelLokasi> call, Throwable t) {


            }
        });
    }
}
//
//            @Override
//            public void onResponse(Call<ModelDaftarProduk> call, Response<ModelDaftarProduk> response) {
//                if(response.isSuccessful()){
//                    ModelDaftarProduk responseDaftarProduk = response.body();
//                    listSemuaProduk = responseDaftarProduk.listProduk;
//                    statusDaftarProdukSdhTerambil = true;
//                    if(mode.equals("pencarian")){
//                        txvJudul.setText("Pencarian Produk");
//                        String kataKunciPencarian = getIntent().getStringExtra("kata_kunci");
//                        txtPencarian.setText(kataKunciPencarian);
//                        filterProdukBerdasarKataPencarian(kataKunciPencarian);
//                    }
//                    else if(mode.equals("produk_per_kategori")){
//                        String idKategori = getIntent().getStringExtra("id_kategori");
//                        String namaKategori = getIntent().getStringExtra("nama_kategori");
//                        txvJudul.setText("Kategori " + namaKategori);
//                        filterProdukPerKategori(idKategori, null);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelDaftarProduk> call, Throwable t) {
//                Toast.makeText(DaftarProdukActivity.this,"Request API daftar produk error", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//    }


//    private void fungRecyleView() {
//        ApiConfig.getService(Produk.this).getDaftarLokasi(id_user).enqueue(new Callback<ModelLokasi>() {
//            @Override
//            public void onResponse(Call<ModelLokasi> call, Response<ModelLokasi> response) {
//                if (response.isSuccessful()){
//                    ModelLokasi respon = response.body();
//                    listLokasi = response.body().listlokasi;
//                    adapter = new LokasiAdapter(Produk.this, listLokasi);
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(Produk.this));
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    adapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelLokasi> call, Throwable t) {
//                Log.e("error", t.getMessage());
//            }
//        });
//    }
//}

package com.example.testcrud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcrud.Model.ModelCreateLokasi;
import com.example.testcrud.Model.ModelLokasi;
import com.example.testcrud.koneksi.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LokasiAdapter extends RecyclerView.Adapter<LokasiAdapter.HolderItemLokasi> {
    private List<ModelLokasi.ListLokasi> listLokasi;
    private Context mcontext;
    private Button btn_hapus;
    private String test ;

    public LokasiAdapter (Context context, List<ModelLokasi.ListLokasi>listLokasi){
        this.listLokasi =listLokasi;
        this.mcontext =context;
    }
    @NonNull
    @Override
    public HolderItemLokasi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlokasi, parent, false);
        return  new LokasiAdapter.HolderItemLokasi(itemview);
    }
    @Override
    public void onBindViewHolder(@NonNull HolderItemLokasi holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_name.setText(listLokasi.get(position).nama_lokasi);
        holder.tv_alamat.setText(listLokasi.get(position).alamat);
        holder.tv_id_lokasi.setText(String.valueOf(listLokasi.get(position).id_lokasi));
        holder.btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("nama", holder.tv_name.getText().toString());
                bundle.putString("alamat", holder.tv_alamat.getText().toString());
                bundle.putInt("id", (listLokasi.get(position).id_lokasi));
                Intent intent = new Intent(mcontext, EditLokasi.class);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        holder.btn_hapus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mcontext);

                // set title dialog
                alertDialogBuilder.setTitle("Konfirmasi hapus lokasi");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Apakah kamu yakin menghapus lokasi"+holder.tv_alamat.getText().toString())
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                //Log.e("erot","error apanih", Throwable.class);
//                                Toast.makeText("halo", holder.tv_id_lokasi.getText().toString());
                                int  test1 = listLokasi.get(position).id_lokasi;
                                //int halo  = Integer.valueOf(test);
                                //System.out.println(halo);
                                Log.d("test", String.valueOf(test1));

                                ApiConfig.getService(mcontext).postDeleteLokasi(test1).enqueue(new Callback<ModelCreateLokasi>() {
                                    @Override
                                    public void onResponse(Call<ModelCreateLokasi> call, Response<ModelCreateLokasi> response) {
                                        if (response.isSuccessful()){
                                            ModelCreateLokasi createlokasi = response.body();
                                            if (createlokasi.status){
                                                Toast.makeText(mcontext,"Hapus berhasil",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(mcontext, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                mcontext.startActivity(intent);
                                            }else {
                                                Toast.makeText(mcontext, createlokasi.message,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelCreateLokasi> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // jika tombol ini diklik, akan menutup dialog
                                // dan tidak terjadi apa2
                                dialog.cancel();
                            }
                        });

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }
        });
    }
    @Override
    public int getItemCount(){
        return listLokasi.size();
    }

    public class HolderItemLokasi extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_alamat,tv_id_lokasi;
        private ImageView btn_edit, btn_hapus;

        public HolderItemLokasi(@NonNull View itemView) {
            super(itemView);
            tv_name =itemView.findViewById(R.id.tv_nama_lokasi1);
            tv_alamat = itemView.findViewById(R.id.tv_lokasi_detail1);
            btn_edit = itemView.findViewById(R.id.btn_edit1);
            btn_hapus = itemView.findViewById(R.id.btn_hapus1);
            tv_id_lokasi = itemView.findViewById(R.id.tv_id_lokasi1);
        }
    }

    private void showDialog(){

    }
}

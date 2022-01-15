package com.example.testcrud.koneksi;

import com.example.testcrud.Model.ModelCreateLokasi;
import com.example.testcrud.Model.ModelLokasi;
import com.example.testcrud.Model.ModelUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Interfaces {

    @GET("get_location_user")
    Call<ModelLokasi> getDaftarLokasi( @Query("id_user")int id_user);

    @FormUrlEncoded
    @POST("create_location")
    Call<ModelCreateLokasi>postCreateLogin(@Field("id_user") int id_user,
                                           @Field("nama_lokasi") String nama_lokasi,
                                           @Field("alamat") String alamat);


    @FormUrlEncoded
    @POST("update_location_user")
    Call<ModelUpdate>postUpdateLogin(@Field("id_lokasi") int id_lokasi,
                                     @Field("nama_lokasi") String nama_lokasi,
                                     @Field("alamat") String alamat);


    @FormUrlEncoded
    @POST("delete_location_user")
    Call<ModelCreateLokasi>postDeleteLokasi(@Field("id_lokasi") int id_lokasi);
}

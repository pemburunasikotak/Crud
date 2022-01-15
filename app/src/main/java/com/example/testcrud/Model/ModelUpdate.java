package com.example.testcrud.Model;

import com.google.gson.annotations.SerializedName;

public class ModelUpdate {
    @SerializedName("status")
    public Boolean status ;
    @SerializedName("message")
    public String message;

//    public List<Tambah> listtambah;
//    public static class Tambah{
//        @SerializedName("id_user")
//        public String id_user;
//        @SerializedName("nama_lokasi")
//        public String nama_lokasi;
//        @SerializedName("alamat")
//        public String alamat;
//    }
}

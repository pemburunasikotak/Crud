package com.example.testcrud.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelLokasi {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String pesan;

    @SerializedName("list_lokasi")
    public List<ListLokasi> listlokasi;

    public static class ListLokasi{
        @SerializedName("id_lokasi")
        public int id_lokasi;
        @SerializedName("id_user")
        public int id_user;
        @SerializedName("nama_lokasi")
        public String nama_lokasi;
        @SerializedName("alamat")
        public String alamat;
    }
}

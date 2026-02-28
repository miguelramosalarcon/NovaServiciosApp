package com.example.novaserviciosapp.network;

import com.example.novaserviciosapp.model.RemoteService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("posts")
    Call<List<RemoteService>> getRemoteServices();
}
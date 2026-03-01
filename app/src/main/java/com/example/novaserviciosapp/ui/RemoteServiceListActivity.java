package com.example.novaserviciosapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novaserviciosapp.R;
import com.example.novaserviciosapp.model.RemoteService;
import com.example.novaserviciosapp.network.ApiService;
import com.example.novaserviciosapp.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteServiceListActivity extends AppCompatActivity {

    private RecyclerView rvRemoteServices;
    private ProgressBar progressBar;
    private TextView tvEmpty;

    private RemoteServiceAdapter adapter;
    private final List<RemoteService> remoteServices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_service_list);

        // UI
        rvRemoteServices = findViewById(R.id.rvRemoteServices);
        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmpty);

        // RecyclerView
        rvRemoteServices.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RemoteServiceAdapter(remoteServices);
        rvRemoteServices.setAdapter(adapter);

        // Cargar datos remotos
        cargarServiciosOnline();
    }

    private void cargarServiciosOnline() {

        mostrarCargando(true);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<List<RemoteService>> call = apiService.getRemoteServices();

        call.enqueue(new Callback<List<RemoteService>>() {
            @Override
            public void onResponse(Call<List<RemoteService>> call, Response<List<RemoteService>> response) {

                mostrarCargando(false);

                if (!response.isSuccessful()) {
                    Toast.makeText(RemoteServiceListActivity.this,
                            "Error servidor: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    mostrarVacio(true);
                    return;
                }

                List<RemoteService> data = response.body();

                if (data == null || data.isEmpty()) {
                    mostrarVacio(true);
                    return;
                }

                remoteServices.clear();
                remoteServices.addAll(data);
                adapter.notifyDataSetChanged();

                mostrarVacio(false);
            }

            @Override
            public void onFailure(Call<List<RemoteService>> call, Throwable t) {

                mostrarCargando(false);
                mostrarVacio(true);

                Toast.makeText(RemoteServiceListActivity.this,
                        "Error de red: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarCargando(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        rvRemoteServices.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void mostrarVacio(boolean isEmpty) {
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }
}
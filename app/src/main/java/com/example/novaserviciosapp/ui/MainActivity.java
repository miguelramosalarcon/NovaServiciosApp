package com.example.novaserviciosapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novaserviciosapp.R;
import com.google.android.material.card.MaterialCardView;

/**
 * MainActivity actúa como Dashboard principal de la aplicación.
 * Responsabilidades:
 * - Detectar interacción del usuario en las tarjetas.
 * - Navegar hacia otras Activities usando Intents explícitos.
 * - Ejecutar acciones externas como apertura del marcador telefónico.
 */
public class MainActivity extends AppCompatActivity {

    private MaterialCardView cardServicios;
    private MaterialCardView cardRegistrar;
    private MaterialCardView cardOnline;
    private MaterialCardView cardContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
    }

    /**
     * Inicializa las referencias de las tarjetas del Dashboard.
     */
    private void initViews() {
        cardServicios = findViewById(R.id.cardServicios);
        cardRegistrar = findViewById(R.id.cardRegistrar);
        cardOnline = findViewById(R.id.cardOnline);
        cardContacto = findViewById(R.id.cardContacto);
    }

    /**
     * Asigna los eventos de click a cada tarjeta.
     */
    private void setupListeners() {

        // Navega a la pantalla de listado de servicios
        cardServicios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ServiceListActivity.class);
            startActivity(intent);
        });

        // Navega a la pantalla de registro de servicio
        cardRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddServiceActivity.class);
            startActivity(intent);
        });

        // Conectar card con servicio online
        cardOnline.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, RemoteServiceListActivity.class);
            startActivity(intent);

        });

        // Abre el marcador telefónico usando Intent implícito
        cardContacto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:999999999"));
            startActivity(intent);
        });
    }
}

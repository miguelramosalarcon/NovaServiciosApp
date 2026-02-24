package com.example.novaserviciosapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.novaserviciosapp.R;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * AddServiceActivity
 * Pantalla encargada de registrar un nuevo servicio.
 * En esta fase solo muestra la interfaz y permite volver al Dashboard.
 */
public class AddServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    /**
     * Maneja el clic en la flecha de regreso (Up button).
     * Finaliza la Activity y vuelve al Dashboard.
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
package com.example.novaserviciosapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novaserviciosapp.R;
import com.example.novaserviciosapp.data.local.NovaDbHelper;
import com.example.novaserviciosapp.model.Servicio;
import com.google.android.material.appbar.MaterialToolbar;

public class EditServiceActivity extends AppCompatActivity {

    private EditText etNombre, etTipo, etFecha, etHora, etCosto;
    private Button btnActualizar;

    private NovaDbHelper dbHelper;
    private int serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        dbHelper = new NovaDbHelper(this);

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Recibir ID
        serviceId = getIntent().getIntExtra("service_id", -1);

        if (serviceId == -1) {
            Toast.makeText(this, "Error al cargar servicio", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Referencias UI
        etNombre = findViewById(R.id.etNombre);
        etTipo = findViewById(R.id.etTipo);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        etCosto = findViewById(R.id.etCosto);
        btnActualizar = findViewById(R.id.btnActualizar);

        cargarDatos();

    }

    private void cargarDatos() {

        Servicio servicio = dbHelper.obtenerServicioPorId(serviceId);

        if (servicio != null) {

            etNombre.setText(servicio.getNombre());
            etTipo.setText(servicio.getTipo());
            etFecha.setText(servicio.getFecha());
            etHora.setText(servicio.getHora());
            etCosto.setText(String.valueOf(servicio.getCosto()));

        } else {
            Toast.makeText(this, "Servicio no encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
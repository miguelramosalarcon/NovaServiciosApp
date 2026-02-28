package com.example.novaserviciosapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novaserviciosapp.R;
import com.example.novaserviciosapp.data.local.NovaDbHelper;
import com.example.novaserviciosapp.model.Servicio;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
/**
 * ServiceListActivity
 * Muestra el listado de servicios activos usando RecyclerView.
 */
public class ServiceListActivity extends AppCompatActivity {

    private RecyclerView recyclerServicios;
    private NovaDbHelper dbHelper;

    private TextView tvEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        tvEmptyState = findViewById(R.id.tvEmptyState);

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Inicializar DB
        dbHelper = new NovaDbHelper(this);

        // RecyclerView
        recyclerServicios = findViewById(R.id.recyclerServicios);
        recyclerServicios.setLayoutManager(new LinearLayoutManager(this));

        // Cargar datos
        cargarServicios();
    }

    /**
     * Se ejecuta cuando la pantalla vuelve a primer plano.
     * Útil para refrescar el listado si vienes de AddServiceActivity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        cargarServicios(); // refresca
    }

    /**
     * Lee servicios activos desde SQLite y los muestra en el RecyclerView.
     */
    private void cargarServicios() {

        List<Servicio> lista = dbHelper.obtenerServiciosActivos();

        if (lista.isEmpty()) {
            // Mostrar mensaje vacío
            tvEmptyState.setVisibility(View.VISIBLE);
            recyclerServicios.setVisibility(View.GONE);
        } else {
            // Mostrar lista
            tvEmptyState.setVisibility(View.GONE);
            recyclerServicios.setVisibility(View.VISIBLE);

            ServicioAdapter adapter = new ServicioAdapter(lista, servicio -> {

                Intent intent = new Intent(ServiceListActivity.this, EditServiceActivity.class);
                intent.putExtra("service_id", servicio.getId());
                startActivity(intent);

            });
            recyclerServicios.setAdapter(adapter);
        }
    }
}
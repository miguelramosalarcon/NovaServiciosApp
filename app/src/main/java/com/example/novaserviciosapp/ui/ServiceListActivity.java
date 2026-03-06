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

import androidx.recyclerview.widget.ItemTouchHelper;
import android.widget.Toast;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * ServiceListActivity
 * Muestra el listado de servicios activos usando RecyclerView.
 */
public class ServiceListActivity extends AppCompatActivity {

    private RecyclerView recyclerServicios;
    private NovaDbHelper dbHelper;

    private ServicioAdapter adapter;
    private List<Servicio> listaServicios;

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

        listaServicios = dbHelper.obtenerServiciosActivos();

        if (listaServicios.isEmpty()) {
            // Mostrar mensaje vacío
            tvEmptyState.setVisibility(View.VISIBLE);
            recyclerServicios.setVisibility(View.GONE);
        } else {
            // Mostrar lista
            tvEmptyState.setVisibility(View.GONE);
            recyclerServicios.setVisibility(View.VISIBLE);

            adapter = new ServicioAdapter(listaServicios, servicio -> {

                Intent intent = new Intent(ServiceListActivity.this, EditServiceActivity.class);
                intent.putExtra("service_id", servicio.getId());
                startActivity(intent);

            });
            recyclerServicios.setAdapter(adapter);

        }

        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        int position = viewHolder.getAdapterPosition();

                        Servicio servicio = listaServicios.get(position);

                        new MaterialAlertDialogBuilder(ServiceListActivity.this)
                                .setTitle("Eliminar servicio")
                                .setMessage("¿Deseas eliminar este servicio?")
                                .setIcon(R.drawable.ic_delete)

                                .setPositiveButton("Eliminar", (dialog, which) -> {

                                    dbHelper.eliminarServicio(servicio.getId());

                                    adapter.eliminarItem(position);

                                    Toast.makeText(
                                            ServiceListActivity.this,
                                            "Servicio eliminado",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                })

                                .setNegativeButton("Cancelar", (dialog, which) -> {

                                    adapter.notifyItemChanged(position);

                                })

                                .setCancelable(false)
                                .show();
                    }

                    @Override
                    public void onChildDraw(Canvas c,
                                            RecyclerView recyclerView,
                                            RecyclerView.ViewHolder viewHolder,
                                            float dX,
                                            float dY,
                                            int actionState,
                                            boolean isCurrentlyActive) {

                        View itemView = viewHolder.itemView;

                        Paint paint = new Paint();
                        paint.setColor(Color.parseColor("#ffffff")); // color

                        c.drawRect(
                                (float) itemView.getRight() + dX,
                                (float) itemView.getTop(),
                                (float) itemView.getRight(),
                                (float) itemView.getBottom(),
                                paint
                        );

                        Drawable icon = ContextCompat.getDrawable(
                                ServiceListActivity.this,
                                R.drawable.ic_delete
                        );

                        if (icon != null) {

                            icon.setTint(ContextCompat.getColor(
                                    ServiceListActivity.this,
                                    R.color.nova_error
                            ));

                            int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;

                            int iconTop = itemView.getTop() + iconMargin;
                            int iconBottom = iconTop + icon.getIntrinsicHeight();

                            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                            int iconRight = itemView.getRight() - iconMargin;

                            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

                            icon.draw(c);

                        }

                        super.onChildDraw(
                                c,
                                recyclerView,
                                viewHolder,
                                dX,
                                dY,
                                actionState,
                                isCurrentlyActive
                        );
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerServicios);

    }
}
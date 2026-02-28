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

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

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

        // Evitar edición manual de fecha y hora para usar los picker
        etFecha.setFocusable(false);
        etFecha.setClickable(true);

        etHora.setFocusable(false);
        etHora.setClickable(true);

        etCosto = findViewById(R.id.etCosto);
        btnActualizar = findViewById(R.id.btnActualizar);

        cargarDatos();
        configurarDatePicker();
        configurarTimePicker();
        configurarBotonActualizar();
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

    private void configurarDatePicker() {

        etFecha.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            // Si ya existe fecha, usarla como base
            String fechaActual = etFecha.getText().toString().trim();
            if (!fechaActual.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    calendar.setTime(sdf.parse(fechaActual));
                } catch (ParseException ignored) { }
            }

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        etFecha.setText(sdf.format(selectedDate.getTime()));
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });
    }

    private void configurarTimePicker() {

        etHora.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            // Si ya existe hora, usarla como base
            String horaActual = etHora.getText().toString().trim();
            if (!horaActual.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
                    calendar.setTime(sdf.parse(horaActual));
                } catch (ParseException ignored) { }
            }

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view, selectedHour, selectedMinute) -> {

                        String horaFormateada = String.format(
                                Locale.US,
                                "%02d:%02d",
                                selectedHour,
                                selectedMinute
                        );

                        etHora.setText(horaFormateada);
                    },
                    hour,
                    minute,
                    true // formato 24 horas
            );

            timePickerDialog.show();
        });
    }


    private void configurarBotonActualizar() {

        btnActualizar.setOnClickListener(v -> {

            String nombre = etNombre.getText().toString().trim();
            String tipo = etTipo.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String hora = etHora.getText().toString().trim();
            String costoStr = etCosto.getText().toString().trim();

            if (nombre.isEmpty() || tipo.isEmpty() || fecha.isEmpty() || hora.isEmpty() || costoStr.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            double costo;

            try {
                costo = Double.parseDouble(costoStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Costo inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            Servicio servicio = new Servicio();
            servicio.setId(serviceId);
            servicio.setNombre(nombre);
            servicio.setTipo(tipo);
            servicio.setFecha(fecha);
            servicio.setHora(hora);
            servicio.setCosto(costo);

            int resultado = dbHelper.actualizarServicio(servicio);

            if (resultado > 0) {
                Toast.makeText(this, "Servicio actualizado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
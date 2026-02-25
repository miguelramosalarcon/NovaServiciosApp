package com.example.novaserviciosapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.novaserviciosapp.R;
import com.example.novaserviciosapp.data.local.NovaDbHelper;
import com.example.novaserviciosapp.model.Servicio;
import com.google.android.material.appbar.MaterialToolbar;

//Librerias para DatePicker y TimePicker
import android.app.DatePickerDialog;
import java.util.Calendar;
import java.util.Locale;
import android.widget.EditText;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.Toast;

/**
 * AddServiceActivity
 * Pantalla encargada de registrar un nuevo servicio.
 * En esta fase solo muestra la interfaz y permite volver al Dashboard.
 */

public class AddServiceActivity extends AppCompatActivity {

    // ==============================
    // Variables de interfaz (UI)
    // ==============================
    private EditText etFecha;
    private EditText etHora;

    private EditText etNombre;
    private EditText etTipo;
    private EditText etCosto;
    private Button btnGuardar;

    private NovaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        dbHelper = new NovaDbHelper(this);


        etFecha = findViewById(R.id.etFecha);
        etFecha.setOnClickListener(v -> showDatePicker());

        etHora = findViewById(R.id.etHora);
        etHora.setOnClickListener(v -> showTimePicker());

        etNombre = findViewById(R.id.etNombre);
        etTipo = findViewById(R.id.etTipo);
        etCosto = findViewById(R.id.etCosto);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {

            if (!validarFormulario()) return;

            // Obtener datos del formulario
            String nombre = etNombre.getText().toString().trim().toUpperCase(Locale.getDefault());
            String tipo = etTipo.getText().toString().trim().toUpperCase(Locale.getDefault());
            String fecha = etFecha.getText().toString().trim();
            String hora = etHora.getText().toString().trim();
            double costo = Double.parseDouble(etCosto.getText().toString().trim());

            // ==============================
            // Crear objeto Servicio (POJO)
            // usando setters (más compatible)
            // ==============================
            Servicio servicio = new Servicio();
            servicio.setNombre(nombre);
            servicio.setTipo(tipo);
            servicio.setFecha(fecha);
            servicio.setHora(hora);
            servicio.setCosto(costo);
            servicio.setActivo(1); // activo por defecto

            // Insertar en base de datos
            long resultado = dbHelper.insertarServicio(servicio);

            if (resultado != -1) {
                Toast.makeText(this, "Servicio guardado correctamente", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            } else {
                Toast.makeText(this, "Error al guardar servicio", Toast.LENGTH_SHORT).show();
            }
        });




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

    //Metodo para usar y llamar al DatePicker-calendario
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {

                    // Formato ISO (yyyy-MM-dd)
                    String formattedDate = String.format(
                            Locale.getDefault(),
                            "%04d-%02d-%02d",
                            selectedYear,
                            selectedMonth + 1,
                            selectedDay
                    );

                    etFecha.setText(formattedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    //Metodo para usar y llamar al TimePicker

    private void showTimePicker() {

        // Obtiene la hora actual del sistema
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {

                    // Formato HH:mm (24 horas)
                    String formattedTime = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            selectedHour,
                            selectedMinute
                    );

                    etHora.setText(formattedTime);
                },
                hour,
                minute,
                true // true = formato 24 horas
        );

        timePickerDialog.show();
    }

    /**
     * Valida que todos los campos del formulario
     * contengan información válida antes de guardar.
     *
     * @return true si el formulario es válido, false en caso contrario.
     */
    private boolean validarFormulario() {

        String nombre = etNombre.getText().toString().trim().toUpperCase(Locale.getDefault());
        String tipo = etTipo.getText().toString().trim().toUpperCase(Locale.getDefault());
        String fecha = etFecha.getText().toString().trim();
        String hora = etHora.getText().toString().trim();
        String costoStr = etCosto.getText().toString().trim();

        // Validación nombre
        if (nombre.isEmpty()) {
            etNombre.setError("Ingrese el nombre del servicio");
            etNombre.requestFocus();
            return false;
        }

        // Validación tipo
        if (tipo.isEmpty()) {
            etTipo.setError("Ingrese el tipo de servicio");
            etTipo.requestFocus();
            return false;
        }

        // Validación fecha
        if (fecha.isEmpty()) {
            etFecha.setError("Seleccione una fecha");
            etFecha.requestFocus();
            return false;
        }

        // Validación hora
        if (hora.isEmpty()) {
            etHora.setError("Seleccione una hora");
            etHora.requestFocus();
            return false;
        }

        // Validación costo
        if (costoStr.isEmpty()) {
            etCosto.setError("Ingrese el costo");
            etCosto.requestFocus();
            return false;
        }

        try {
            double costo = Double.parseDouble(costoStr);
            if (costo < 0) {
                etCosto.setError("El costo no puede ser negativo");
                etCosto.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            etCosto.setError("Costo inválido");
            etCosto.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Limpia todos los campos del formulario
     * después de un registro exitoso.
     */
    private void limpiarFormulario() {
        etNombre.setText("");
        etTipo.setText("");
        etFecha.setText("");
        etHora.setText("");
        etCosto.setText("");
    }


}
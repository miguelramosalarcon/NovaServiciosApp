package com.example.novaserviciosapp.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.novaserviciosapp.R;
import com.google.android.material.appbar.MaterialToolbar;

//Librerias para DatePicker y TimePicker
import android.app.DatePickerDialog;
import java.util.Calendar;
import java.util.Locale;
import android.widget.EditText;
import android.app.TimePickerDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        etFecha = findViewById(R.id.etFecha);
        etFecha.setOnClickListener(v -> showDatePicker());

        etHora = findViewById(R.id.etHora);
        etHora.setOnClickListener(v -> showTimePicker());

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



}
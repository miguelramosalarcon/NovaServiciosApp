package com.example.novaserviciosapp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import com.example.novaserviciosapp.model.Servicio;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

/**
 * NovaDbHelper
 * Clase encargada de gestionar la base de datos local SQLite.
 * Extiende SQLiteOpenHelper, que es la clase oficial de Android
 * para manejar creación y actualización de bases de datos.
 */
public class NovaDbHelper extends SQLiteOpenHelper {

    // ==============================
    // CONSTANTES DE CONFIGURACIÓN
    // ==============================

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "nova_servicios.db";

    // Versión de la base de datos (si cambia la estructura, se incrementa)
    private static final int DATABASE_VERSION = 1;

    // ==============================
    // CONSTANTES DE LA TABLA
    // ==============================

    public static final String TABLE_SERVICIOS = "servicios";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_TIPO = "tipo";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "hora";
    public static final String COLUMN_COSTO = "costo";
    public static final String COLUMN_ACTIVO = "activo";

    // ==============================
    // SENTENCIA SQL DE CREACIÓN
    // ==============================

    private static final String CREATE_TABLE_SERVICIOS =
            "CREATE TABLE " + TABLE_SERVICIOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_TIPO + " TEXT NOT NULL, " +
                    COLUMN_FECHA + " TEXT NOT NULL, " +
                    COLUMN_HORA + " TEXT NOT NULL, " +
                    COLUMN_COSTO + " REAL NOT NULL, " +
                    COLUMN_ACTIVO + " INTEGER DEFAULT 1" +
                    ");";

    // ==============================
    // CONSTRUCTOR
    // ==============================

    public NovaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ==============================
    // MÉTODO onCreate()
    // Se ejecuta la primera vez que
    // se crea la base de datos
    // ==============================

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SERVICIOS);
    }

    // ==============================
    // MÉTODO onUpgrade()
    // Se ejecuta cuando cambia la
    // versión de la base de datos
    // ==============================

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En desarrollo eliminamos y recreamos
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICIOS);
        onCreate(db);
    }

    //Inserta servicios en la base de datos
    public long insertarServicio(Servicio servicio) {

        // Obtiene la base de datos en modo escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Crea un objeto ContentValues para almacenar los valores
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRE, servicio.getNombre());
        values.put(COLUMN_TIPO, servicio.getTipo());
        values.put(COLUMN_FECHA, servicio.getFecha());
        values.put(COLUMN_HORA, servicio.getHora());
        values.put(COLUMN_COSTO, servicio.getCosto());
        values.put(COLUMN_ACTIVO, servicio.getActivo());

        // Inserta los valores en la tabla y obtiene el id generado
        long id = db.insert(TABLE_SERVICIOS, null, values);

        // Cierra la conexión
        db.close();

        return id;
    }


    /**
     * Obtiene todos los servicios activos (activo = 1)
     * ordenados de forma descendente por ID.
     *
     * Equivalente SQL:
     * SELECT * FROM servicios
     * WHERE activo = 1
     * ORDER BY id DESC;
     *
     * @return Lista de servicios activos
     */
    public List<Servicio> obtenerServiciosActivos() {

        // Lista que almacenará los resultados
        List<Servicio> listaServicios = new ArrayList<>();

        // Abrimos la base de datos en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();

        // Parámetros de consulta
        String selection = COLUMN_ACTIVO + " = ?";
        String[] selectionArgs = {"1"};

        String orderBy = COLUMN_ID + " DESC";

        // Ejecutamos la consulta usando query()
        Cursor cursor = db.query(
                TABLE_SERVICIOS,   // Tabla
                null,              // null = todas las columnas
                selection,         // WHERE
                selectionArgs,     // Argumentos del WHERE
                null,              // GROUP BY
                null,              // HAVING
                orderBy            // ORDER BY
        );

        // Verificamos si el cursor tiene datos
        if (cursor != null && cursor.moveToFirst()) {

            do {

                // Crear objeto Servicio
                Servicio servicio = new Servicio();

                // Mapear columnas del cursor al objeto
                servicio.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                servicio.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)));
                servicio.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO)));
                servicio.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)));
                servicio.setHora(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HORA)));
                servicio.setCosto(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COSTO)));
                servicio.setActivo(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTIVO)));

                // Agregar a la lista
                listaServicios.add(servicio);

            } while (cursor.moveToNext());
        }

        // Cerramos cursor
        if (cursor != null) {
            cursor.close();
        }

        // Cerramos base de datos
        db.close();

        return listaServicios;
    }


    public Servicio obtenerServicioPorId(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_SERVICIOS,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        Servicio servicio = null;

        if (cursor != null && cursor.moveToFirst()) {

            servicio = new Servicio();
            servicio.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            servicio.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)));
            servicio.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO)));
            servicio.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)));
            servicio.setHora(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HORA)));
            servicio.setCosto(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COSTO)));
            servicio.setActivo(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTIVO)));

            cursor.close();
        }

        db.close();

        return servicio;
    }


    public int actualizarServicio(Servicio servicio) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRE, servicio.getNombre());
        values.put(COLUMN_TIPO, servicio.getTipo());
        values.put(COLUMN_FECHA, servicio.getFecha());
        values.put(COLUMN_HORA, servicio.getHora());
        values.put(COLUMN_COSTO, servicio.getCosto());

        // WHERE id = ?
        int filasAfectadas = db.update(
                TABLE_SERVICIOS,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(servicio.getId())}
        );

        db.close();

        return filasAfectadas;
    }





}
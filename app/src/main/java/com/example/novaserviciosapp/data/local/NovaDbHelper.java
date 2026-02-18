package com.example.novaserviciosapp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}

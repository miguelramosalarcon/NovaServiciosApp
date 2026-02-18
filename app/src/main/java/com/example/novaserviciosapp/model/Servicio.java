package com.example.novaserviciosapp.model;

/**
 * Clase modelo que representa la entidad Servicio.
 * Esta clase es un POJO (Plain Old Java Object) y
 * modela un registro de la tabla "servicios" en SQLite.
 * Cada objeto Servicio equivale a una fila en la base de datos.
 */
public class Servicio {

    // =========================
    // ATRIBUTOS (Campos de la tabla)
    // =========================

    private int id;
    private String nombre;
    private String descripcion;
    private String fecha;              // Formato: yyyy-MM-dd
    private String hora;               // Formato: HH:mm
    private String tipo;               // Preventivo, Correctivo, etc.
    private double costo;
    private int activo;                // 1 = activo, 0 = inactivo (eliminación lógica)
    private String fechaCreacion;
    private String fechaActualizacion;


    // =========================
    // CONSTRUCTOR VACÍO
    // =========================

    /**
     * Constructor vacío obligatorio.
     * Se usa cuando el objeto será llenado posteriormente
     * mediante setters (por ejemplo, al leer desde SQLite).
     */
    public Servicio() {
    }


    // =========================
    // CONSTRUCTOR COMPLETO
    // =========================

    /**
     * Constructor completo para crear un servicio con
     * todos sus datos.
     */
    public Servicio(int id, String nombre, String descripcion,
                    String fecha, String hora, String tipo,
                    double costo, int activo,
                    String fechaCreacion, String fechaActualizacion) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.costo = costo;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }


    // =========================
    // GETTERS Y SETTERS
    // =========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }


    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }


    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }


    // =========================
    // MÉTODO toString()
    // =========================

    /**
     * Método útil para depuración.
     * Permite imprimir el objeto en logs.
     */
    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha='" + fecha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", costo=" + costo +
                ", activo=" + activo +
                '}';
    }
}

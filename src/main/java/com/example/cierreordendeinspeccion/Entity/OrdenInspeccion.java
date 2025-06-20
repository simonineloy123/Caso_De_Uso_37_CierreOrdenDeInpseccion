package com.example.cierreordendeinspeccion.Entity;

public class OrdenInspeccion {
    private int numero;
    private String estado;
    private String fechaFinalizacion;
    private String responsable;
    private String estacion;
    private String sismografo;

    public OrdenInspeccion(int numero, String estado, String fechaFinalizacion, String responsable, String estacion, String sismografo) {
        this.numero = numero;
        this.estado = estado;
        this.fechaFinalizacion = fechaFinalizacion;
        this.responsable = responsable;
        this.estacion = estacion;
        this.sismografo = sismografo;
    }

    public int getNumero() { return numero; }
    public String getEstado() { return estado; }
    public String getFechaFinalizacion() { return fechaFinalizacion; }
    public String getResponsable() { return responsable; }
    public String getEstacion() { return estacion; }
    public String getSismografo() { return sismografo; }
}

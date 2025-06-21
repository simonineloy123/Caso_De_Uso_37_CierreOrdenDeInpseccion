package com.example.cierreordendeinspeccion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ambito", nullable = false)
    private String ambito;

    public Estado() {}

    public Estado(String nombre, String ambito) {
        this.nombre = nombre;
        this.ambito = ambito;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAmbito() {
        return ambito;
    }

    public boolean esCompletamenteRealizada() {
        return "COMPLETAMENTE REALIZADA".equalsIgnoreCase(nombre);
    }

    public boolean esAmbitoOrdenInspeccion() {
        return "ORDEN_INSPECCION".equalsIgnoreCase(ambito);
    }
}
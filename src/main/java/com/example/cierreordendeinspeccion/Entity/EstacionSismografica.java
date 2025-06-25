package com.example.cierreordendeinspeccion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "EstacionSismografica")
public class EstacionSismografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    public EstacionSismografica(String nombre) {
        this.nombre = nombre;
    }

    public EstacionSismografica() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return this.nombre;
    }

}

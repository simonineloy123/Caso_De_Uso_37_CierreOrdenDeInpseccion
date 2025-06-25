package com.example.cierreordendeinspeccion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Sismografo")
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idSismografo", nullable = false)
    private String IdSismografo;

    public Sismografo(String IdSismografo) {
        this.IdSismografo = IdSismografo;
    }

    public Sismografo() {
    }

    public Long getId() {
        return id;
    }

    public String getIdSismografo(){
        return this.IdSismografo;
    }

}

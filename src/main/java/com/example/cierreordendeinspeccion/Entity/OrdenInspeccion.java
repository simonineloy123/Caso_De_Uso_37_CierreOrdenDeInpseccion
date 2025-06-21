package com.example.cierreordendeinspeccion.Entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "OrdenInspeccion")
public class OrdenInspeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero", nullable = false)
    private int numero;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @Column(name = "fechaFinalizacion", nullable = false)
    private String fechaFinalizacion;


    public OrdenInspeccion(int numero, Estado estado, String fechaFinalizacion) {
        this.numero = numero;
        this.estado = estado;
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public OrdenInspeccion() {
    }

    public int getNumero() { return numero; }
    public Estado getEstado() { return estado; }
    public String getFechaFinalizacion() { return fechaFinalizacion; }

    public List<OrdenInspeccion> buscarOrdenesInspeccionRealizadas(EntityManager em) {

        List<OrdenInspeccion> todas = em.createQuery("SELECT o FROM OrdenInspeccion o", OrdenInspeccion.class)
                .getResultList();

        return todas.stream()
                .filter(o -> o.getEstado() != null &&
                        o.getEstado().esAmbitoOrdenInspeccion() &&
                        o.getEstado().esCompletamenteRealizada())
                .collect(Collectors.toList());
    }

    public void habilitarActualizarSituaciónSismografo() {

    }
}

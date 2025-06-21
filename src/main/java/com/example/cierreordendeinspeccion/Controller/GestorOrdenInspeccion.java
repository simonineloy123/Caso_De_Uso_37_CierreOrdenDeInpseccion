package com.example.cierreordendeinspeccion.Controller;

import com.example.cierreordendeinspeccion.Entity.OrdenInspeccion;
import com.example.cierreordendeinspeccion.Entity.Sesion;
import jakarta.persistence.EntityManager;

import java.util.Comparator;
import java.util.List;

public class GestorOrdenInspeccion {

    public void cerrarOrdenDeInspeccion(EntityManager em, String username){
        this.buscarUsuarioLogueado(em, username);
    }

    public String buscarUsuarioLogueado(EntityManager em, String username) {
        Sesion sesion = new Sesion();
        return sesion.getResponsableInspeccionLogueado(em, username);
    }

    public List<OrdenInspeccion> buscarOrdenesInspeccionRealizadas(EntityManager em) {
        OrdenInspeccion ordenInspeccion = new OrdenInspeccion();
        return ordenInspeccion.buscarOrdenesInspeccionRealizadas(em);
    }

    public List<OrdenInspeccion> ordenarPorFechaFinalizacion(List<OrdenInspeccion> ordenes) {
        ordenes.sort(Comparator.comparing(OrdenInspeccion::getFechaFinalizacion).reversed());
        return ordenes;
    }
}

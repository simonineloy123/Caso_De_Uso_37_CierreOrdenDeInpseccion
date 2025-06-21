package com.example.cierreordendeinspeccion.Entity;

import jakarta.persistence.EntityManager;

public class Sesion {
    private String responsableInspeccionLogueado;

    public String getResponsableInspeccionLogueado(EntityManager em, String username) {
        Usuario usuario = new Usuario();
        return usuario.obtenerEmpleado(em, username);
    }

}

package com.example.cierreordendeinspeccion.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    // Constructor, getters y setters
    public Empleado(String username, String nombre, String apellido) {
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Empleado() {

    }

    public String getUsername() { return username; }
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public Empleado obtenerEmpleado(EntityManager em, String username) {
        try {
            return em.createQuery("SELECT e FROM Empleado e WHERE e.username = :username", Empleado.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
package com.example.proyecto.spring.Formulario;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Formulario")
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @Column(name = "puntuacion",nullable = false)
    private double puntuacion;

    @Column(name = "oficinaTecnica", nullable = false)
    private String oficinaTecnica;

    public Formulario(){}

    public Formulario(double puntuacion, String oficinaTecnica){
        this.puntuacion = puntuacion;
        this.oficinaTecnica = oficinaTecnica;
    }

    public UUID getId() {
        return id;
    }

    public String getOficinaTecnica() {
        return oficinaTecnica;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setOficinaTecnica(String oficinaTecnica) {
        this.oficinaTecnica = oficinaTecnica;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }
}

package com.example.proyecto.spring.Solicitante;

import com.example.proyecto.spring.Proyecto.Proyecto;
import jakarta.persistence.*;
import com.example.proyecto.spring.Persona.*;

import java.util.List;

@Entity
@Table(name = "Solicitante")
public class Solicitante extends Persona{
    @Column(name = "unidadSolicitante",nullable = false)
    private String unidadSolicitante;

    @OneToMany
    private List<Proyecto> proyectos;

    public Solicitante(){}
    public Solicitante(String nombre, String correo, String unidadSolicitante){
       super(nombre, correo);
       this.unidadSolicitante = unidadSolicitante;
    }

    public String getUnidadSolicitante() {
        return unidadSolicitante;
    }

    public void setUnidadSolicitante(String unidadSolicitante) {
        this.unidadSolicitante = unidadSolicitante;
    }
}
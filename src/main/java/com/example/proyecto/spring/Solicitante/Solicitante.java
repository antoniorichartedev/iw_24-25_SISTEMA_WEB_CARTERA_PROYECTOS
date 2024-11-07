package com.example.proyecto.spring.Solicitante;

import jakarta.persistence.Entity;
import com.example.proyecto.spring.Persona.*;

@Entity
public class Solicitante extends Persona{
    private String unidadSolicitante;

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
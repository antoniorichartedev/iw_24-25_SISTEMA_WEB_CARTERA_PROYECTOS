package com.example.proyecto.spring.Promotor;

import com.example.proyecto.spring.Proyecto.Proyecto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import com.example.proyecto.spring.Persona.*;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Promotor extends Persona {
    @Column(name = "importancia")
    private int importancia;

    @OneToMany
    private List<Proyecto> proyectos;

    public Promotor(String nombre, String correo, int importancia){

        // Con super(), llamo al constructor de Persona, ya que es la clase base de Promotor.
        super(nombre, correo);
        this.importancia = importancia;
    }

    public Promotor(){}

    public int getImportancia() {
        return importancia;
    }

    public void setImportancia(int importancia) {
        this.importancia = importancia;
    }
}
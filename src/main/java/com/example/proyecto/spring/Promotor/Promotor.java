package com.example.proyecto.spring.Promotor;

import jakarta.persistence.Entity;
import com.example.proyecto.spring.Persona.*;

@Entity
public class Promotor extends Persona {
    private int importancia;

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
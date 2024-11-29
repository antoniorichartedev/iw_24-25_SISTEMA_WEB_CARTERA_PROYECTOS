package com.example.proyecto.spring.Promotor;

import com.example.proyecto.spring.Proyecto.Proyecto;
import jakarta.persistence.*;
import com.example.proyecto.spring.Persona.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Promotor")
public class Promotor extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
    @Column(name = "importancia", nullable = false)
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
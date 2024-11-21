package com.example.proyecto.spring.Interesado;

import com.example.proyecto.spring.Proyecto.Proyecto;
import jakarta.persistence.*;
import com.example.proyecto.spring.Persona.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Interesado")
public class Interesado extends Persona {

    // Ctor predeterminado.
    public Interesado() {}

    @Column(name = "Financiación Aportada")
    private BigDecimal FinanciacionAportada;

    // Ctor.
    public Interesado(String nombre, String correo, BigDecimal Financiacion)
    {
        super(nombre, correo);
        this.FinanciacionAportada = Financiacion;
    }

    public BigDecimal getFinanciacionAportada() { return this.FinanciacionAportada; }

    /* Relación con Proyecto. Refleja los proyectos en los que está interesado esta persona (el interesado, claro) */
    @OneToMany
    public List<Proyecto> proyectos;

    public List<Proyecto> getProyectos() { return proyectos; }
}

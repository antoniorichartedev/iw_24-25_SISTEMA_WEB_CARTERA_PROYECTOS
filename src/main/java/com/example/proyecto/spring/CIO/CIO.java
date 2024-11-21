package com.example.proyecto.spring.CIO;

import com.example.proyecto.spring.Formulario.Formulario;
import jakarta.persistence.*;
import com.example.proyecto.spring.Persona.*;

import java.util.List;


@Entity
@Table(name = "CIO")
public class CIO extends Persona {

    // Ctor. predeterminado.
    public CIO() {}

    // Relación que tiene con Formulario.
    @OneToMany
    public List<Formulario> formularios;
    public List<Formulario> getFormularios() { return formularios; }
    public void setFormulario(Formulario formulario) { this.formularios.add(formulario); }

    // Ctor.
    public CIO(String nombre, String correo)
    {
        super(nombre, correo);
    }

}

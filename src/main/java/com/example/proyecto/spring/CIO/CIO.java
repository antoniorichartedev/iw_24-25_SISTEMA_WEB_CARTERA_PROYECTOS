package com.example.proyecto.spring.CIO;


import jakarta.persistence.Entity;
import com.example.proyecto.spring.Persona.*;


@Entity
public class CIO extends Persona {

    // Ctor. predeterminado.
    public CIO() {}

    // Ctor.
    public CIO(String nombre, String correo)
    {
        super(nombre, correo);
    }

}

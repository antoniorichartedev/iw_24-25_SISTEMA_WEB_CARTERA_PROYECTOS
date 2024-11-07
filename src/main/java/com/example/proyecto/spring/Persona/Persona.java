package com.example.proyecto.spring.Persona;

import jakarta.persistence.Entity;

// Necesario importarlas explícitamente, ya que no están en el JPA.
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Entity
public class Persona{
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String nombre;

    // Correo que tendrá cada persona.
    @Column(nullable = false)
    private String correo;

    // Ctor.
    public Persona(String nombre, String correo){
        this.nombre = nombre;
        this.correo = correo;
    }

    // Ctor. predeterminado necesario, requerido por JPA.
    public Persona() {}

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() { return this.correo; }

    @Override
    public boolean equals(Object obj){

        // Primero, comprobamos que obj y el objeto implícito (this) son el mismo objeto.
        if(this == obj)
            return true;

        // Si no son el mismo objeto, comprobamos que obj es null o, si no lo es, comprobamos que NO sean de la misma
        // clase.
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        /*
            Si llegamos aquí, quiere decir que:
                * this y obj no son el mismo objeto.
                * obj no es null.
                * this y obj son del mismo tipo de clase, es decir, de la clase Persona.
         */
        Persona other = (Persona) obj;
        return id != null && id.equals(other.id);
    }
}
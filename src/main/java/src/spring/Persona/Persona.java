package src.spring.Persona;

import jakarta.persistence.Entity;

@Entity
public class Persona{
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String nombre;

    public Persona(String nombre){
        this.nombre = nombre;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        return Persona.equals(id, other.id);
    }
}
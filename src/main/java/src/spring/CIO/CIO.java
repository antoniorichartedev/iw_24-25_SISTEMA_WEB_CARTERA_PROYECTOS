package src.spring.CIO;


import jakarta.persistence.Entity;
import src.spring.Persona.Persona;


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

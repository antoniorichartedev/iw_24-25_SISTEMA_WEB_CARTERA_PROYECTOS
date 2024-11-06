package src.spring.Interesado;

import jakarta.persistence.Entity;
import src.spring.Persona.Persona;

import java.math.BigDecimal;

@Entity
public class Interesado extends Persona {

    // Ctor predeterminado.
    public Interesado() {}

    private BigDecimal FinanciacionAportada;

    // Ctor.
    public Interesado(String nombre, String correo, BigDecimal Financiacion)
    {
        super(nombre, correo);
        this.FinanciacionAportada = Financiacion;
    }

    public BigDecimal getFinanciacionAportada() { return this.FinanciacionAportada; }
}

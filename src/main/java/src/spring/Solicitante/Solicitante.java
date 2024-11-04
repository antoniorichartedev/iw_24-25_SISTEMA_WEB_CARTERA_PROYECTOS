package src.spring.Solicitante;
package src.spring.Persona;

import jakarta.persistence.Entity;

public class Solicitante extends Persona{
    private String correo;
    private String unidadSolicitante;

    public Solicitante(String nombre, String correo, String unidadSolicitante){
       super(nombre);
        this.correo = correo;
        this.unidadSolicitante = unidadSolicitante;
    }

    public String getCorreo() {
        return correo;
    }

    public String getUnidadSolicitante() {
        return unidadSolicitante;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setUnidadSolicitante(String unidadSolicitante) {
        this.unidadSolicitante = unidadSolicitante;
    }
}
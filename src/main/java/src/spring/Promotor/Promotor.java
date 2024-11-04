package src.spring.Promotor;
package src.spring.Persona;

import jakarta.persistence.Entity;

@Entity
public class Promotor extends Persona{
    private int importancia;

    public Promotor(String nombre, int importancia){
        super(nombre);
        this.importancia = importancia;
    }

    public int getImportancia() {
        return importancia;
    }

    public void setImportancia(int importancia) {
        this.importancia = importancia;
    }
}
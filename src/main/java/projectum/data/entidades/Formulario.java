package projectum.data.entidades;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Formulario")
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @Column(name = "puntuacion",nullable = false)
    private double puntuacion;

    @ManyToOne
    @JoinColumn(name = "cio_evaluador") // Nombre de la columna de la clave foránea.
    private CIO cio;

    public Formulario(){}

    public Formulario(double puntuacion, CIO cio1){
        this.puntuacion = puntuacion;
    }

    public UUID getId() {
        return id;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public CIO getCio() { return cio; }
}

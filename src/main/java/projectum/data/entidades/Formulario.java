package projectum.data.entidades;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Formulario")
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @Column(name = "puntuacion", nullable = false)
    private int puntuacion;

    @ManyToOne
    @JoinColumn(name = "cio_evaluador") // Nombre de la columna de la clave foránea.
    private Usuario cio;

    @ManyToOne
    @JoinColumn(name = "ot_evaluadora")
    private Usuario ot;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

    public Formulario(){}

    public Formulario(int puntuacion, Usuario cio1, Usuario ot1){
        this.puntuacion = puntuacion;
        this.cio = cio1;
        this.ot = ot1;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setCio(Usuario cio1) {
        this.cio = cio1;
    }
    public Usuario getCio() { return cio; }

    public Usuario getOt() { return ot; }
    public void setOt(Usuario ot1) {
        this.ot = ot1;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
    public Proyecto getProyecto() { return proyecto; }
}

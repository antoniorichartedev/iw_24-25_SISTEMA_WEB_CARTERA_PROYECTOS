package proyectum.data.entidades;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Solicitante extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
    @Column(name = "unidadSolicitante",nullable = false)
    private String unidadSolicitante;

    @OneToMany
    private List<Proyecto> proyectos;

    public Solicitante(){}
    public Solicitante(String nombre, String correo, String password, String unidadSolicitante){
       super(nombre, correo, password);
       this.unidadSolicitante = unidadSolicitante;
    }

    public String getUnidadSolicitante() {
        return unidadSolicitante;
    }

    public void setUnidadSolicitante(String unidadSolicitante) {
        this.unidadSolicitante = unidadSolicitante;
    }
}
package projectum.data.entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Solicitante extends Usuario {

    @Column(name = "unidadSolicitante",nullable = true)
    private String unidadSolicitante;

    @OneToMany(mappedBy = "solicitante", cascade = CascadeType.ALL)
    private List<Proyecto> proyectos = new ArrayList<Proyecto>();

    public Solicitante(){}
    public Solicitante(String nombre, String username, String correo, String password, String unidadSolicitante){
       super(nombre, username, correo, password);
       this.unidadSolicitante = unidadSolicitante;
    }

    public String getUnidadSolicitante() {
        return unidadSolicitante;
    }

    public void setUnidadSolicitante(String unidadSolicitante) {
        this.unidadSolicitante = unidadSolicitante;
    }
    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyecto(Proyecto proyecto) {
        if(!this.proyectos.contains(proyecto)) {
            this.proyectos.add(proyecto);
            proyecto.setSolicitante(this);
        }
    }
}
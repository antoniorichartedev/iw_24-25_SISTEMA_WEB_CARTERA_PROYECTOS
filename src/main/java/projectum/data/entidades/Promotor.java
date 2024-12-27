package projectum.data.entidades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Promotor extends Usuario {

    @Column(name = "cargo", nullable = true)
    private String cargo;

    @OneToMany(mappedBy = "promotor", cascade = CascadeType.ALL)
    private List<Proyecto> proyectos = new ArrayList<Proyecto>();

    public Promotor(String nombre, String username, String correo, String passwd, int importancia, String cargo) {

        // Con super(), llamo al constructor de Persona, ya que es la clase base de Promotor.
        super(nombre, username, correo, passwd);
        this.cargo = cargo;
    }

    public Promotor(){}

    public String getCargo() { return cargo; }

    public void setCargo(String cargo) { this.cargo = cargo; }
    public List<Proyecto> getProyectos() { return proyectos; }

    public void setProyecto(Proyecto proyecto) {
        if(!this.proyectos.contains(proyecto)) {
            this.proyectos.add(proyecto);
            proyecto.setPromotor(this);
        }
    }
}
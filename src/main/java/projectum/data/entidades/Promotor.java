package projectum.data.entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Promotor extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
    @Column(name = "importancia", nullable = true)
    private int importancia;

    @OneToMany(mappedBy = "promotor", cascade = CascadeType.ALL)
    private List<Proyecto> proyectos = new ArrayList<Proyecto>();

    public Promotor(String nombre, String username, String correo, String passwd, int importancia){

        // Con super(), llamo al constructor de Persona, ya que es la clase base de Promotor.
        super(nombre, username, correo, passwd);
        this.importancia = importancia;
    }

    public Promotor(){}

    public int getImportancia() {
        return importancia;
    }

    public void setImportancia(int importancia) {
        this.importancia = importancia;
    }

    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyecto(Proyecto proyecto) {
        if(!this.proyectos.contains(proyecto)) {
            this.proyectos.add(proyecto);
            proyecto.setPromotor(this);
        }
    }
}
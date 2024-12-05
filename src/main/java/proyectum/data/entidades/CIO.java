package proyectum.data.entidades;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
public class CIO extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    // Ctor. predeterminado.
    public CIO() {}

    // Relación que tiene con Formulario.
    @OneToMany(mappedBy = "cio", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Formulario> formularios;
    public List<Formulario> getFormularios() { return formularios; }
    public void setFormulario(Formulario formulario) { this.formularios.add(formulario); }

    // Ctor.
    public CIO(String nombre, String correo, String passwd)
    {
        super(nombre, correo, passwd);
    }

}

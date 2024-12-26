package projectum.data.entidades;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;


@Entity
public class CIO extends Usuario {

    // Ctor. predeterminado.
    public CIO() {}

    // Relación que tiene con Formulario.
    @OneToMany(mappedBy = "cio", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Formulario> formularios;
    public List<Formulario> getFormularios() { return formularios; }
    public void setFormulario(Formulario formulario) { this.formularios.add(formulario); }

    // Ctor.
    public CIO(String nombre, String username, String correo, String passwd)
    {
        super(nombre, username, correo, passwd);
    }

}

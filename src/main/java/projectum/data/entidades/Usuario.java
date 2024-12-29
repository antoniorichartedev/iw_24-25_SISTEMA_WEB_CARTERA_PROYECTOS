package projectum.data.entidades;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projectum.data.Rol;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Necesario importarlas explícitamente, ya que no están en el JPA.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Una tabla para toda la jerarquía
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id = UUID.randomUUID();

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // Correo que tendrá cada persona.
    @Email
    @Column(nullable = false)
    private String correo;

    @Transient
    private String contrasena;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    Rol rol = Rol.USER ;

    @Column(name = "activo")
    private boolean estado;

    private String codigoRegistro = "";

    private String hashedPassword;

    // Relación que tiene con Formulario pero de la OT.
    @OneToMany(mappedBy = "ot", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Formulario> formulariosOT = new ArrayList<Formulario>();

    public List<Formulario> getFormulariosOT() { return formulariosOT; }
    public void setFormularioOT(Formulario formulario) {
        this.formulariosOT.add(formulario);
    }

    // Relación que tiene con Formulario pero del CIO.
    @OneToMany(mappedBy = "cio", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Formulario> formulariosCIO = new ArrayList<Formulario>();

    public List<Formulario> getFormulariosCIO() { return formulariosCIO; }
    public void setFormularioCIO(Formulario formulario) {
        this.formulariosCIO.add(formulario);
    }

    /* RELACIONES COMO SOLICITANTE */
    @Column(name = "unidadSolicitante",nullable = true)
    private String unidadSolicitante;

    @OneToMany(mappedBy = "solicitante", cascade = CascadeType.ALL)
    private List<Proyecto> proyectosSol = new ArrayList<Proyecto>();

    /* RELACIONES COMO PROMOTOR */
    @Column(name = "cargo", nullable = true)
    private String cargo;

    @OneToMany(mappedBy = "promotor", cascade = CascadeType.ALL)
    private List<Proyecto> proyectosProm = new ArrayList<Proyecto>();

    // Ctor.
    public Usuario(String nombre, String username, String correo, String password){
        this.nombre = nombre;
        this.username = username;
        this.correo = correo;
        this.contrasena = password;

    }

    // Ctor. predeterminado necesario, requerido por JPA.
    public Usuario() {}

    public UUID getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }
    public String getNombre() { return nombre; }
    public String getPassword() { return contrasena; }
    public String getCorreo() { return this.correo; }
    public Rol getRol() { return rol; }
    public boolean getEstado() { return estado; }
    public String getCodigoRegistro() { return this.codigoRegistro; }
    public String getUnidadSolicitante() { return unidadSolicitante; }
    public List<Proyecto> getProyectosSolicitante() { return proyectosSol; }
    public String getCargo() { return cargo; }
    public List<Proyecto> getProyectosPromotor() { return proyectosProm; }

    public void setCargo(String cargo) { this.cargo = cargo; }
    public void setUnidadSolicitante(String unidadSolicitante) { this.unidadSolicitante = unidadSolicitante; }

    public void setProyectoSol(Proyecto proyecto) {
        if(!this.proyectosSol.contains(proyecto)) {
            this.proyectosSol.add(proyecto);
            proyecto.setSolicitante(this);
        }
    }
    public void setProyectoProm(Proyecto proyecto) {
        if(!this.proyectosProm.contains(proyecto)) {
            this.proyectosProm.add(proyecto);
            proyecto.setPromotor(this);
        }
    }
    public void setId(UUID id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCorreo(String correo){ this.correo = correo; }
    public void setRol(Rol rol) { this.rol = rol; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public void setPassword(String password){ this.contrasena = password; }
    public void setCodigoRegistro(String codigoRegistro){ this.codigoRegistro = codigoRegistro; }

    @Override
    public boolean equals(Object obj){

        // Primero, comprobamos que obj y el objeto implícito (this) son el mismo objeto.
        if(this == obj)
            return true;

        // Si no son el mismo objeto, comprobamos que obj es null o, si no lo es, comprobamos que NO sean de la misma
        // clase.
        if(obj == null || this.getClass() != obj.getClass())
            return false;

        /*
            Si llegamos aquí, quiere decir que:
                * this y obj no son el mismo objeto.
                * obj no es null.
                * this y obj son del mismo tipo de clase, es decir, de la clase Persona.
         */
        Usuario other = (Usuario) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + getRol());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.estado;
    }


    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}

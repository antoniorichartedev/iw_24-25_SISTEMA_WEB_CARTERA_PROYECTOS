package proyectum.data.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class Interesado extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    // Ctor predeterminado.
    public Interesado() {}

    @Column(name = "Financiación Aportada")
    private BigDecimal financiacionAportada;

    // Ctor.
    public Interesado(String nombre, String correo, String passwd, BigDecimal Financiacion)
    {
        super(nombre, correo, passwd);
        this.financiacionAportada = Financiacion;
    }

    public BigDecimal getFinanciacionAportada() { return financiacionAportada; }
    public void setFinanciacionAportada(BigDecimal financiacionAportada) {
        this.financiacionAportada = financiacionAportada;
    }

    /* Relación con Proyecto. Refleja los proyectos en los que está interesado esta persona (el interesado, claro) */
    @OneToMany
    public List<Proyecto> proyectos;

    public List<Proyecto> getProyectos() { return proyectos; }
}

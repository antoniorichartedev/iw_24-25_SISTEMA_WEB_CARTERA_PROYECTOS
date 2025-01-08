package projectum.data.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Convocatoria")
public class Convocatoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id = UUID.randomUUID();

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "fechaInicio")
    private Date fechaInico;

    @Column(name = "fechaFin")
    private Date fechaFin;

    @Column(name = "actividad")
    private Boolean actividad;

    public Convocatoria(){};

    public Convocatoria(UUID id, String nombre, Date fIni, Date fFin){
        this.id = id;
        this.nombre = nombre;
        this.fechaInico = fIni;
        this.fechaFin = fFin;
        this.actividad = false;
    }

    public void setId(UUID id) { this.id = id; }
    public UUID getId() { return this.id;}

    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombre() { return this.nombre; }

    public void setFechaInicio(Date fini) { this.fechaInico = fini; }
    public Date getFechaInicio() { return this.fechaInico; }

    public void setFechaFin(Date ffin) { this.fechaFin = ffin; }
    public Date getFechaFin() { return this.fechaFin; }

    public void setActividad(Boolean actividad) { this.actividad = actividad; }
    public Boolean getActividad() {return this.actividad; }

}

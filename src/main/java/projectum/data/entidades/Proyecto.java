package projectum.data.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projectum.data.Estado;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id = UUID.randomUUID();

    @Column(name = "titulo",nullable = false)
    private String titulo;

    @Column(name = "acronimo",nullable = false)
    private String acronimo;

    @Column(name = "justificacion", nullable = false, columnDefinition = "VARCHAR(1000)")
    private String justificacion;

    @Column(name = "alcance",nullable = false)
    private String alcance;

    @Lob // Para grandes archivos.
    @Column(name = "memorias", columnDefinition = "LONGBLOB") // Almacenar archivos o grandes datos binarios
    private byte[] memorias;

    @Lob
    @Column(name = "presupuestos", columnDefinition = "LONGBLOB") // Almacenar archivos o grandes datos binarios
    private byte[] presupuestos;

    @Lob
    @Column(name = "especificaciones_tecnicas", columnDefinition = "LONGBLOB") // Almacenar archivos o grandes datos binarios
    private byte[] especificaciones;

    @Column(name = "importancia",nullable = false)
    private int importancia;

    @Column(name = "financiacion",nullable = false)
    private BigDecimal financiacion;

    @Column(name = "puestaEnMarcha")
    private Date puestaMarcha;

    @Column(name = "interesado")
    private String interesado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotor_id", nullable = false)
    private Usuario promotor;

    @Column(name = "priorizacion")
    private int priorizacion;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @OneToOne
    @JoinColumn(name = "convocatoria")
    private Convocatoria convocatoria;


    public Proyecto() {
    }

    public Proyecto(String titulo, String acronimo,String justificacion ,byte[] memorias, int importancia, BigDecimal financiacion, String alcance, Date marcha, String interesado) {
        this.titulo = titulo;
        this.acronimo = acronimo;
        this.justificacion = justificacion;
        this.memorias = memorias;
        this.importancia = importancia;
        this.financiacion = financiacion;
        this.alcance = alcance;
        this.puestaMarcha = marcha;
        this.interesado = interesado;
    }

    public Proyecto (String titulo) { this.titulo = titulo; }
    // Getters y Setters

    public void setId(UUID id) { this.id = id; }
    public UUID getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String title) { this.titulo = title; }

    public String getAcronimo() { return acronimo; }
    public void setAcronimo(String acronimo) { this.acronimo = acronimo; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public String getAlcance() { return alcance; }
    public void setAlcance(String alcance) { this.alcance = alcance; }

    public int getImportancia() { return importancia; }
    public void setImportancia(int importancia) { this.importancia = importancia; }

    public Usuario getSolicitante() { return solicitante; }
    public void setSolicitante(Usuario soli) { this.solicitante = soli; }

    public BigDecimal getFinanciacion() { return financiacion; }
    public void setFinanciacion(BigDecimal financiacion) { this.financiacion = financiacion; }

    public byte[] getMemorias() { return memorias; }
    public void setMemorias(byte[] memorias) { this.memorias = memorias; }

    public byte[] getPresupuestos() { return presupuestos; }
    public void setPresupuestos(byte[] presupuestos) { this.presupuestos = presupuestos; }

    public byte[] getEspecificaciones() { return especificaciones; }
    public void setEspecificaciones(byte[] especificaciones) { this.especificaciones = especificaciones; }

    public Date getPuestaMarcha() { return puestaMarcha; }
    public void setPuestaMarcha(Date puestaMarcha) { this.puestaMarcha = puestaMarcha; }

    public void setInteresado(String interesado) { this.interesado = interesado; }
    public String getInteresado() { return interesado; }

    public Usuario getPromotor() { return promotor; }
    public void setPromotor(Usuario promotor) { this.promotor = promotor; }

    public int getPriorizacion() { return priorizacion; }
    public void setPriorizacion(int priorizacion) {
        // La priorización va del 1 al 5 siendo el uno el más importante y el 5 el que menos.
        if (priorizacion > 0 && priorizacion < 6) {
            this.priorizacion = priorizacion;
        }
    }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Convocatoria getConvocatoria() { return convocatoria; }
    public void setConvocatoria(Convocatoria convocatoria) { this.convocatoria = convocatoria; }
}

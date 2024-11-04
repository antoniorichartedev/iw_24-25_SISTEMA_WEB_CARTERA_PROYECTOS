package src.spring.Proyecto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.Date;

@Entity
public class Proyecto {

    @Id
    private Long id;

    @Column(nullable = false)
    private String titulo;
    private String acronimo;
    private String justificacion;
    private String alcance;


    @Column(columnDefinition = "BLOB") // Almacenar archivos o grandes datos binarios
    private byte[] memorias;

    private int importancia;
    private int financiacion;
    private Date puestaMarcha;


    public Proyecto(String titulo, String acronimo,String justificacion ,byte[] memorias, int importancia, int financiacion, String alcance, Date marcha) {
        this.titulo = titulo;
        this.acronimo = acronimo;
        this.justificacion = justificacion;
        this.memorias = memorias;
        this.importancia = importancia;
        this.financiacion = financiacion;
        this.alcance = alcance;
        this.puestaMarcha = marcha;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }

    public String getAcronimo() { return acronimo; }

    public String getJustificacion() { return justificacion; }

    public String getAlcance() { return alcance; }

    public int getImportancia() { return importancia; }

    public int getFinanciacion() { return financiacion; }
    public void setFinanciacion(int financiacion) { this.financiacion = financiacion; }

    public byte[] getMemorias() { return memorias; }
    public void setMemorias(byte[] memorias) { this.memorias = memorias; }

    public Date getPuestaMarcha() { return puestaMarcha; }
    public void setPuestaMarcha(Date puestaMarcha) { this.puestaMarcha = puestaMarcha; }
}

package com.example.proyecto.spring.Proyecto;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Proyecto")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @Column(name = "titulo",nullable = false)
    private String titulo;

    @Column(name = "acronimo",nullable = false)
    private String acronimo;

    @Column(name = "justificacion",nullable = false)
    private String justificacion;

    @Column(name = "alcance",nullable = false)
    private String alcance;


    @Column(name = "memorias") // Almacenar archivos o grandes datos binarios
    private byte[] memorias;

    @Column(name = "importancia",nullable = false)
    private int importancia;

    @Column(name = "financiacion",nullable = false)
    private int financiacion;

    @Column(name = "puestaEnMarcha")
    private Date puestaMarcha;

    public Proyecto() {
    }

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

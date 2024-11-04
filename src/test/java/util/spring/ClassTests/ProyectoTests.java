package util.spring.ClassTests;

import src.spring.Proyecto.Proyecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class ProyectoTests {

    private Proyecto proyecto;
    private String titulo = "Proyecto Ejemplo";
    private String acronimo = "PE";
    private String justificacion = "Justificación del proyecto";
    private String alcance = "Alcance del proyecto";
    private byte[] memorias = {1, 2, 3, 4, 5};
    private int importancia = 5;
    private int financiacion = 10000;
    private Date puestaMarcha = new Date();

    @BeforeEach
    public void setUp() {
        proyecto = new Proyecto(titulo, acronimo, justificacion, memorias, importancia, financiacion, alcance, puestaMarcha);
    }

    @Test
    public void testConstructor() {
        assertEquals(titulo, proyecto.getTitulo());
        assertEquals(acronimo, proyecto.getAcronimo());
        assertEquals(justificacion, proyecto.getJustificacion());
        assertEquals(alcance, proyecto.getAlcance());
        assertArrayEquals(memorias, proyecto.getMemorias());
        assertEquals(importancia, proyecto.getImportancia());
        assertEquals(financiacion, proyecto.getFinanciacion());
        assertEquals(puestaMarcha, proyecto.getPuestaMarcha());
    }

    @Test
    public void testSetFinanciacion() {
        int nuevaFinanciacion = 15000;
        proyecto.setFinanciacion(nuevaFinanciacion);
        assertEquals(nuevaFinanciacion, proyecto.getFinanciacion());
    }

    @Test
    public void testSetMemorias() {
        byte[] nuevasMemorias = {6, 7, 8, 9, 10};
        proyecto.setMemorias(nuevasMemorias);
        assertArrayEquals(nuevasMemorias, proyecto.getMemorias());
    }

    @Test
    public void testSetPuestaMarcha() {
        Date nuevaFecha = new Date();
        proyecto.setPuestaMarcha(nuevaFecha);
        assertEquals(nuevaFecha, proyecto.getPuestaMarcha());
    }
}


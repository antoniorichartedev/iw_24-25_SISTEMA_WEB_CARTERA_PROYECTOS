package util.spring.ClassTests;

import proyectum.data.entidades.Proyecto;
import proyectum.data.entidades.Solicitante;
import proyectum.data.entidades.Interesado;
import proyectum.data.entidades.Promotor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

//Tests de las funcionalidades de la clase proyecto.
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

    @Test
    public void testSolicitante(){
        Solicitante solicitante = new Solicitante("María", "maria@example.com", "Recursos Humanos");
        proyecto.setSolicitante(solicitante);
        assertEquals(solicitante.getId(), proyecto.getSolicitante().getId());
    }

    @Test
    public void testInteresado(){
        Interesado inter = new Interesado("Raul", "Raul@example.com", new BigDecimal("100000"));
        proyecto.setInteresado(inter);
        assertEquals(inter.getId(), proyecto.getInteresado().getId());
    }

    @Test
    public void testPromotor(){
        Promotor prom = new Promotor("Pedro", "Pedro@example.com", 4);
        proyecto.setPromotor(prom);
        assertEquals(prom.getId(), proyecto.getPromotor().getId());
    }

    @Test
    public void testID(){
        Solicitante solicitante = new Solicitante("María", "maria@example.com", "Recursos Humanos");
        proyecto.setSolicitante(solicitante);

        Promotor prom = new Promotor("Pedro", "Pedro@example.com", 4);
        proyecto.setPromotor(prom);

        Interesado inter = new Interesado("Raul", "Raul@example.com", new BigDecimal("100000"));
        proyecto.setInteresado(inter);

        assertNotEquals(proyecto.getPromotor().getId(), proyecto.getInteresado().getId());
        assertNotEquals(proyecto.getSolicitante().getId(), proyecto.getInteresado().getId());
        assertNotEquals(proyecto.getSolicitante().getId(), proyecto.getPromotor().getId());
    }
}


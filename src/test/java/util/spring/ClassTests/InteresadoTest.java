package util.spring.ClassTests;

import proyectum.data.entidades.Interesado;
import proyectum.data.entidades.Usuario;
import proyectum.data.entidades.Proyecto;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.*;

public class InteresadoTest {
    private Interesado interesado;

    @BeforeEach
    public void setUp() { interesado = new Interesado("Antonio", "antonioricharte@gmail.com", BigDecimal.valueOf(50000)); }

    @Test
    public void testConstructor() {
        assertNotNull(interesado.getId(), "El ID no puede ser nulo.");
        assertEquals("Antonio", interesado.getId(), "El nombre no está inicializado correctamente.");
        assertEquals("antonioricharte@gmail.com", interesado.getCorreo(), "El correo no está inicializado correctamente.");
        assertEquals(BigDecimal.valueOf(50000), interesado.getFinanciacionAportada(), "La financiación aportada no está bien inicializada.");
    }

    @Test
    public void testGetProyectos() {
        // Creamos proyectos de ejemplo.
        Proyecto proyecto1 = new Proyecto(
                "Sistema de Gestión de Proyectos",
                "SGP",
                "Automatizar la gestión de proyectos en empresas",
                new byte[]{1, 2, 3, 4, 5},
                5,
                100000,
                "Implementar en toda la región",
                new Date()
        );

        Proyecto proyecto2 = new Proyecto(
                "Plataforma de Aprendizaje Online",
                "PAO",
                "Justificar la importancia de la educación remota",
                new byte[]{10, 20, 30, 40, 50},
                8,
                250000,
                "Crear una plataforma que abarque 5 países",
                new Date()
        );

        // Creamos una nueva lista.
        List<Proyecto> prList = new ArrayList<Proyecto>();
        prList.add(proyecto1);
        prList.add(proyecto2);

        // Comprobamos ambas listas.
        assertEquals(prList, interesado.getProyectos());
    }

    @Test
    public void testHerenciaDePersona() {
        assertTrue(interesado instanceof Usuario, "Interesado debería ser una instancia de Persona");
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(interesado.equals(interesado), "Un objeto debería ser igual a sí mismo");
    }

    @Test
    public void testEquals_Null() {
        assertFalse(interesado.equals(null), "Un objeto no debería ser igual a null");
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(interesado.equals("String"), "Un objeto no debería ser igual a un objeto de diferente clase");
    }

    @Test
    public void testEquals_DifferentId() {
        Interesado otroInteresado = new Interesado("Juan", "juan.gomez@gmail.com", BigDecimal.valueOf(50000));
        assertNotEquals(interesado.getId(), otroInteresado.getId(), "Los interesados deben tener IDs diferentes");
        assertNotEquals(interesado, otroInteresado, "Interesados con IDs diferentes no deberían ser iguales");
    }
}

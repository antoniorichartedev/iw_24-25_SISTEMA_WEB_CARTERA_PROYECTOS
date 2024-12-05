package util.spring.ClassTests;

import com.example.proyecto.spring.CIO.*;
import com.example.proyecto.spring.Usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CIOTest {
    private CIO cio;

    @BeforeEach
    public void setUp() throws Exception { cio =

            new CIO("Juan Alberto", "juanalberto69@gmail.com"); }

    @Test
    public void testConstructor() {
        assertNotNull(cio.getId(), "El ID no tiene que ser nulo.");
        assertEquals("Juan Alberto", cio.getNombre(), "El nombre no fue inicializado correctamente.");
        assertEquals("juanalberto69@gmail.com", cio.getCorreo(), "El email no fue inicializado correctamente.");
    }

    @Test
    public void testGetFormularios() {
        // ...
    }

    @Test
    public void testHerenciaDePersona() {
        assertTrue(cio instanceof Usuario, "CIO debería ser una instancia de Persona");
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(cio.equals(cio), "Un objeto debería ser igual a sí mismo");
    }

    @Test
    public void testEquals_Null() {
        assertFalse(cio.equals(null), "Un objeto no debería ser igual a null");
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(cio.equals("String"), "Un objeto no debería ser igual a un objeto de diferente clase");
    }

    @Test
    public void testEquals_DifferentId() {
        CIO otroCIO = new CIO("Juan", "juan.gomez@gmail.com");
        assertNotEquals(cio.getId(), otroCIO.getId(), "Los CIO's deben tener IDs diferentes");
        assertNotEquals(cio, otroCIO, "CIO's con IDs diferentes no deberían ser iguales");
    }

}

package src.spring.Promotor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.spring.Persona.Persona;

import static org.junit.jupiter.api.Assertions.*;

public class PromotorTest {
    private Promotor promotor;

    @BeforeEach
    public void setUp() {
        promotor = new Promotor("Juan", 5);
    }

    @Test
    public void testConstructor() {
        assertNotNull(promotor.getId(), "El ID debería estar inicializado");
        assertEquals("Juan", promotor.getNombre(), "El nombre no fue inicializado correctamente");
        assertEquals(5, promotor.getImportancia(), "La importancia no fue inicializada correctamente");
    }

    @Test
    public void testSetImportancia() {
        promotor.setImportancia(8);
        assertEquals(8, promotor.getImportancia(), "La importancia no fue cambiada correctamente");
    }

    @Test
    public void testHerenciaDePersona() {
        assertTrue(promotor instanceof Persona, "Promotor debería ser una instancia de Persona");
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(promotor.equals(promotor), "Un objeto debería ser igual a sí mismo");
    }

    @Test
    public void testEquals_Null() {
        assertFalse(promotor.equals(null), "Un objeto no debería ser igual a null");
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(promotor.equals("String"), "Un objeto no debería ser igual a un objeto de diferente clase");
    }

    @Test
    public void testEquals_DifferentId() {
        Promotor otroPromotor = new Promotor("Juan", 5);
        assertNotEquals(promotor.getId(), otroPromotor.getId(), "Los promotores deben tener IDs diferentes");
        assertNotEquals(promotor, otroPromotor, "Promotores con IDs diferentes no deberían ser iguales");
    }
}

package src.spring.Persona;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class PersonaTest {
    private Persona persona;

    @BeforeEach
    public void setUp() {
        persona = new Persona("Carlos");
    }

    @Test
    public void testConstructor() {
        assertNotNull(persona.getId(), "El ID debería estar inicializado");
        assertEquals("Carlos", persona.getNombre(), "El nombre no fue inicializado correctamente");
    }

    @Test
    public void testSetNombre() {
        persona.setNombre("Ana");
        assertEquals("Ana", persona.getNombre(), "El nombre no fue cambiado correctamente");
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(persona.equals(persona), "Un objeto debería ser igual a sí mismo");
    }

    @Test
    public void testEquals_Null() {
        assertFalse(persona.equals(null), "Un objeto no debería ser igual a null");
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(persona.equals("String"), "Un objeto no debería ser igual a un objeto de diferente clase");
    }

    @Test
    public void testEquals_DifferentId() {
        Persona persona2 = new Persona("Carlos");
        assertNotEquals(persona.getId(), persona2.getId(), "Las personas deben tener IDs diferentes");
        assertNotEquals(persona, persona2, "Personas con IDs diferentes no deberían ser iguales");
    }
}

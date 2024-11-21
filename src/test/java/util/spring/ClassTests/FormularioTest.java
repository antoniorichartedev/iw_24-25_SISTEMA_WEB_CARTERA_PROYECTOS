package util.spring.ClassTests;

import com.example.proyecto.spring.Formulario.Formulario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FormularioTest {
    private Formulario formulario;

    @BeforeEach
    public void setUp() {
        formulario = new Formulario(7.5, "Oficina Regional");
    }

    @Test
    public void testConstructor() {
        assertNotNull(formulario.getId(), "El ID debería estar inicializado");
        assertEquals(7.5, formulario.getPuntuacion(), "La puntuación no fue inicializada correctamente");
        assertEquals("Oficina Regional", formulario.getOficinaTecnica(), "La oficina técnica no fue inicializada correctamente");
    }

    @Test
    public void testSetPuntuacion() {
        formulario.setPuntuacion(9.2);
        assertEquals(9.2, formulario.getPuntuacion(), "La puntuación no fue cambiada correctamente");
    }

    @Test
    public void testSetOficinaTecnica() {
        formulario.setOficinaTecnica("Oficina Central");
        assertEquals("Oficina Central", formulario.getOficinaTecnica(), "La oficina técnica no fue cambiada correctamente");
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(formulario.equals(formulario), "Un objeto debería ser igual a sí mismo");
    }

    @Test
    public void testEquals_Null() {
        assertFalse(formulario.equals(null), "Un objeto no debería ser igual a null");
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(formulario.equals("String"), "Un objeto no debería ser igual a un objeto de diferente clase");
    }

    @Test
    public void testEquals_DifferentId() {
        Formulario otroFormulario = new Formulario(7.5, "Oficina Regional");
        assertNotEquals(formulario.getId(), otroFormulario.getId(), "Los formularios deben tener IDs diferentes");
        assertNotEquals(formulario, otroFormulario, "Formularios con IDs diferentes no deberían ser iguales");
    }
}

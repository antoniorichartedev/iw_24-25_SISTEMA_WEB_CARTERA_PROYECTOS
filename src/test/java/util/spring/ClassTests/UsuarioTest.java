package util.spring.ClassTests;

import com.example.proyecto.spring.Usuario.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    private Usuario usuario;

    @BeforeEach

    public void setUp() {
        usuario = new Usuario("Carlos", "carlos@uca.es");
    }

    @Test
    public void testConstructor() {
        assertNotNull(usuario.getId(), "El ID debería estar inicializado");
        assertEquals("Carlos", usuario.getNombre(), "El nombre no fue inicializado correctamente");
    }

    @Test
    public void testSetNombre() {
        usuario.setNombre("Ana");
        assertEquals("Ana", usuario.getNombre(), "El nombre no fue cambiado correctamente");
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(usuario.equals(usuario), "Un objeto debería ser igual a sí mismo");
    }

    @Test
    public void testEquals_Null() {
        assertFalse(usuario.equals(null), "Un objeto no debería ser igual a null");
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(usuario.equals("String"), "Un objeto no debería ser igual a un objeto de diferente clase");
    }

    @Test
    public void testEquals_DifferentId() {
        Usuario usuario2 = new Usuario("Carlos", "carlos.ramirez@outlook.com");
        assertNotEquals(usuario.getId(), usuario2.getId(), "Las personas deben tener IDs diferentes");
        assertNotEquals(usuario, usuario2, "Personas con IDs diferentes no deberían ser iguales");
    }
}

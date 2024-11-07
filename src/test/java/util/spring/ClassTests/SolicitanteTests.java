package util.spring.ClassTests;

import src.data.spring.Solicitante.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.data.spring.Persona.Persona;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitanteTests {
    private Solicitante solicitante;

    @BeforeEach
    public void setUp() {
        solicitante = new Solicitante("María", "maria@example.com", "Recursos Humanos");
    }

    @Test
    public void testConstructor() {
        assertNotNull(solicitante.getId(), "El ID debería estar inicializado");
        assertEquals("María", solicitante.getNombre(), "El nombre no fue inicializado correctamente");
        assertEquals("maria@example.com", solicitante.getCorreo(), "El correo no fue inicializado correctamente");
        assertEquals("Recursos Humanos", solicitante.getUnidadSolicitante(), "La unidad solicitante no fue inicializada correctamente");
    }

    @Test
    public void testSetCorreo() {
        solicitante.setCorreo("nuevo_correo@example.com");
        assertEquals("nuevo_correo@example.com", solicitante.getCorreo(), "El correo no fue cambiado correctamente");
    }

    @Test
    public void testSetUnidadSolicitante() {
        solicitante.setUnidadSolicitante("Finanzas");
        assertEquals("Finanzas", solicitante.getUnidadSolicitante(), "La unidad solicitante no fue cambiada correctamente");
    }

    @Test
    public void testHerenciaDePersona() {
        assertTrue(solicitante instanceof Persona, "Solicitante debería ser una instancia de Persona");
    }

    @Test
    public void testEquals_SameObject() {
        assertTrue(solicitante.equals(solicitante), "Un objeto debería ser igual a sí mismo");
    }

    @Test
    public void testEquals_Null() {
        assertFalse(solicitante.equals(null), "Un objeto no debería ser igual a null");
    }

    @Test
    public void testEquals_DifferentClass() {
        assertFalse(solicitante.equals("String"), "Un objeto no debería ser igual a un objeto de diferente clase");
    }

    @Test
    public void testEquals_DifferentId() {
        Solicitante otroSolicitante = new Solicitante("María", "maria@example.com", "Recursos Humanos");
        assertNotEquals(solicitante.getId(), otroSolicitante.getId(), "Los solicitantes deben tener IDs diferentes");
        assertNotEquals(solicitante, otroSolicitante, "Solicitantes con IDs diferentes no deberían ser iguales");
    }
}

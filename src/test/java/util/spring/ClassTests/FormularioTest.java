package com.example.proyecto.spring.Formulario;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FormularioTest {

    @Test
    void testConstructor() {
        Formulario formulario = new Formulario(4.5, "Oficina Central");

        assertEquals(4.5, formulario.getPuntuacion());
        assertEquals("Oficina Central", formulario.getOficinaTecnica());
        assertNotNull(formulario.getId());
    }

    @Test
    void testSettersAndGetters() {
        Formulario formulario = new Formulario();

        // Probamos el setter y getter para puntuación
        formulario.setPuntuacion(8.9);
        assertEquals(8.9, formulario.getPuntuacion());

        // Probamos el setter y getter para oficinaTecnica
        formulario.setOficinaTecnica("Oficina Regional");
        assertEquals("Oficina Regional", formulario.getOficinaTecnica());
    }

    @Test
    void testIdIsUnique() {
        Formulario formulario1 = new Formulario();
        Formulario formulario2 = new Formulario();

        assertNotNull(formulario1.getId());
        assertNotNull(formulario2.getId());
        assertNotEquals(formulario1.getId(), formulario2.getId());
    }
}

package unitarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectum.data.entidades.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FormularioTest {

    private Formulario formulario;
    private Usuario cio;
    private Usuario ot;
    private Proyecto proyecto;

    @BeforeEach
    void setUp() {
        cio = new Usuario("CIO Name", "cioUser", "cio@example.com", "password");
        ot = new Usuario("OT Name", "otUser", "ot@example.com", "password");
        proyecto = new Proyecto();
        proyecto.setTitulo("Proyecto A");

        formulario = new Formulario(85, cio, ot);
        formulario.setProyecto(proyecto);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(85, formulario.getPuntuacion());
        assertEquals(cio, formulario.getCio());
        assertEquals(ot, formulario.getOt());
        assertEquals(proyecto, formulario.getProyecto());
    }

    @Test
    void testSetters() {
        Usuario newCio = new Usuario("New CIO", "newCioUser", "newcio@example.com", "newpassword");
        Usuario newOt = new Usuario("New OT", "newOtUser", "newot@example.com", "newpassword");
        Proyecto newProyecto = new Proyecto();
        newProyecto.setTitulo("Proyecto B");

        formulario.setPuntuacion(95);
        formulario.setCio(newCio);
        formulario.setOt(newOt);
        formulario.setProyecto(newProyecto);

        assertEquals(95, formulario.getPuntuacion());
        assertEquals(newCio, formulario.getCio());
        assertEquals(newOt, formulario.getOt());
        assertEquals(newProyecto, formulario.getProyecto());
    }

    @Test
    void testDefaultIdIsGenerated() {
        assertNotNull(formulario.getId());
        assertInstanceOf(UUID.class, formulario.getId());
    }

    @Test
    void testSetAndGetProyecto() {
        Proyecto proyectoTest = new Proyecto();
        proyectoTest.setTitulo("Proyecto Test");
        formulario.setProyecto(proyectoTest);

        assertEquals(proyectoTest, formulario.getProyecto());
    }
}


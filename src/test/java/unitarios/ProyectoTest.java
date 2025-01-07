package unitarios;

import projectum.data.entidades.Proyecto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class ProyectoTest {

    @Test
    void constructorAndGetters_shouldInitializeFieldsCorrectly() {
        // Datos de prueba
        String titulo = "Proyecto Alpha";
        String acronimo = "PA";
        String justificacion = "Justificación del proyecto";
        byte[] memorias = "Memorias".getBytes();
        int importancia = 3;
        BigDecimal financiacion = new BigDecimal("50000");
        String alcance = "Nacional";
        Date puestaMarcha = new Date();
        String interesado = "Cliente A";

        // Crear instancia usando el constructor
        Proyecto proyecto = new Proyecto(titulo, acronimo, justificacion, memorias, importancia, financiacion, alcance, puestaMarcha, interesado);

        // Verificar valores
        assertThat(proyecto.getTitulo()).isEqualTo(titulo);
        assertThat(proyecto.getAcronimo()).isEqualTo(acronimo);
        assertThat(proyecto.getJustificacion()).isEqualTo(justificacion);
        assertThat(proyecto.getMemorias()).isEqualTo(memorias);
        assertThat(proyecto.getImportancia()).isEqualTo(importancia);
        assertThat(proyecto.getFinanciacion()).isEqualTo(financiacion);
        assertThat(proyecto.getAlcance()).isEqualTo(alcance);
        assertThat(proyecto.getPuestaMarcha()).isEqualTo(puestaMarcha);
        assertThat(proyecto.getInteresado()).isEqualTo(interesado);
    }

    @Test
    void setters_shouldUpdateValuesCorrectly() {
        Proyecto proyecto = new Proyecto();

        // Valores de prueba
        String nuevoTitulo = "Nuevo Proyecto";
        proyecto.setTitulo(nuevoTitulo);
        assertThat(proyecto.getTitulo()).isEqualTo(nuevoTitulo);

        int nuevaImportancia = 4;
        proyecto.setImportancia(nuevaImportancia);
        assertThat(proyecto.getImportancia()).isEqualTo(nuevaImportancia);

        BigDecimal nuevaFinanciacion = new BigDecimal("75000");
        proyecto.setFinanciacion(nuevaFinanciacion);
        assertThat(proyecto.getFinanciacion()).isEqualTo(nuevaFinanciacion);
    }

    @Test
    void setPriorizacion_shouldOnlyAcceptValidValues() {
        Proyecto proyecto = new Proyecto();

        // Probar con un valor válido
        proyecto.setPriorizacion(3);
        assertThat(proyecto.getPriorizacion()).isEqualTo(3);

        // Probar con un valor inválido (fuera del rango 1-5)
        proyecto.setPriorizacion(6);
        assertThat(proyecto.getPriorizacion()).isEqualTo(3); // El valor no cambia

        // Probar con otro valor válido
        proyecto.setPriorizacion(1);
        assertThat(proyecto.getPriorizacion()).isEqualTo(1);

        // Probar con otro valor inválido
        proyecto.setPriorizacion(0);
        assertThat(proyecto.getPriorizacion()).isEqualTo(1); // El valor no cambia
    }

    @Test
    void defaultId_shouldBeGeneratedAutomatically() {
        Proyecto proyecto = new Proyecto();

        // Verificar que el ID se genera automáticamente y no es nulo
        assertThat(proyecto.getId()).isNotNull();
        assertThat(proyecto.getId()).isInstanceOf(UUID.class);
    }
}

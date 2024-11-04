package util.spring.ServiceTests; // Cambia esto según tu estructura de paquetes

import org.springframework.context.annotation.Import;
import src.spring.Proyecto.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

class ProyectoServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository; // Simularemos el repositorio

    @InjectMocks
    private ProyectoService proyectoService; // La clase que vamos a probar

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testGetAllProyectos() {
        // Arrange
        List<Proyecto> proyectos = new ArrayList<>();
        proyectos.add(new Proyecto("Proyecto 1", "P1", "Justificación 1", new byte[]{}, 3, 1000, "Alcance 1", new Date()));
        when(proyectoRepository.findAll()).thenReturn(proyectos);

        // Act
        List<Proyecto> result = proyectoService.getAllProyectos();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Proyecto 1", result.get(0).getTitulo());
        verify(proyectoRepository, times(1)).findAll(); // Verifica que se llamó al repositorio
    }

    @Test
    void testGetProyectoById() {
        // Arrange
        Long id = 1L;
        Proyecto proyecto = new Proyecto("Proyecto 1", "P1", "Justificación 1", new byte[]{}, 3, 1000, "Alcance 1", new Date());
        when(proyectoRepository.findById(id)).thenReturn(Optional.of(proyecto));

        // Act
        Optional<Proyecto> result = proyectoService.getProyectoById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Proyecto 1", result.get().getTitulo());
        verify(proyectoRepository, times(1)).findById(id); // Verifica que se llamó al repositorio
    }

    @Test
    void testSaveProyecto() {
        // Arrange
        Proyecto proyecto = new Proyecto("Proyecto 1", "P1", "Justificación 1", new byte[]{}, 3, 1000, "Alcance 1", new Date());
        when(proyectoRepository.save(proyecto)).thenReturn(proyecto);

        // Act
        Proyecto result = proyectoService.saveProyecto(proyecto);

        // Assert
        assertNotNull(result);
        assertEquals("Proyecto 1", result.getTitulo());
        verify(proyectoRepository, times(1)).save(proyecto); // Verifica que se llamó al repositorio
    }

    @Test
    void testDeleteProyecto() {
        // Arrange
        Long id = 1L;

        // Act
        proyectoService.deleteProyecto(id);

        // Assert
        verify(proyectoRepository, times(1)).deleteById(id); // Verifica que se llamó al repositorio
    }
}
package integracion;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import projectum.data.entidades.Proyecto;
import projectum.data.repositorios.ProyectoRepository;
import projectum.data.servicios.ProyectoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProyectoServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    private Proyecto proyecto;
    private UUID proyectoId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        proyectoId = UUID.randomUUID();
        proyecto = new Proyecto();
        proyecto.setId(proyectoId);
        proyecto.setTitulo("Proyecto de prueba");
    }

    @Test
    public void testAddProyecto() {
        // Simula el comportamiento de save en el repositorio
        doNothing().when(proyectoRepository).save(proyecto);

        // Llamada al método
        proyectoService.addProyecto(proyecto);

        // Verifica que el método save fue invocado
        verify(proyectoRepository).save(proyecto);
    }

    @Test
    public void testGetAllProyectos() {
        // Datos de prueba
        List<Proyecto> proyectos = List.of(proyecto);
        when(proyectoRepository.findAll()).thenReturn(proyectos);

        // Llamada al método
        List<Proyecto> result = proyectoService.getAllProyectos();

        // Verifica que la lista no esté vacía
        assertFalse(result.isEmpty());

        // Verifica que el tamaño de la lista sea 1
        assertEquals(1, result.size());

        // Verifica que el proyecto devuelto sea el mismo que el proyecto de prueba
        assertEquals(proyecto, result.getFirst());
    }

    @Test
    public void testGetProyectoById() {
        // Simula el comportamiento de findById
        when(proyectoRepository.findById(proyectoId)).thenReturn(Optional.of(proyecto));

        // Llamada al método
        Optional<Proyecto> result = proyectoService.getProyectoById(proyectoId);

        // Verifica que el proyecto esté presente
        assertTrue(result.isPresent());

        // Verifica que el proyecto devuelto sea el mismo que el proyecto de prueba
        assertEquals(proyecto, result.get());
    }

    @Test
    public void testGetProyectoByTitulo() {
        // Simula el comportamiento de findByTitulo
        when(proyectoRepository.findByTitulo("Proyecto de prueba")).thenReturn(Optional.of(proyecto));

        // Llamada al método
        Optional<Proyecto> result = proyectoService.getProyectoByTitulo("Proyecto de prueba");

        // Verifica que el proyecto esté presente
        assertTrue(result.isPresent());

        // Verifica que el proyecto devuelto sea el mismo que el proyecto de prueba
        assertEquals(proyecto, result.get());
    }

    @Test
    public void testSaveProyecto() {
        // Simula el comportamiento de save
        when(proyectoRepository.save(proyecto)).thenReturn(proyecto);

        // Llamada al método
        Proyecto result = proyectoService.saveProyecto(proyecto);

        // Verifica que el resultado sea el mismo que el proyecto de prueba
        assertEquals(proyecto, result);

        // Verifica que el método save fue invocado
        verify(proyectoRepository).save(proyecto);
    }

    @Test
    public void testDeleteProyecto() {
        // Simula el comportamiento de deleteById
        doNothing().when(proyectoRepository).deleteById(proyectoId);

        // Llamada al método
        proyectoService.deleteProyecto(proyectoId);

        // Verifica que el método deleteById fue invocado
        verify(proyectoRepository).deleteById(proyectoId);
    }

    @Test
    public void testGetProyectosBySolicitante() {
        // Simula el comportamiento de findBySolicitanteId
        UUID solicitanteId = UUID.randomUUID();
        List<Proyecto> proyectos = List.of(proyecto);
        when(proyectoRepository.findBySolicitanteId(solicitanteId)).thenReturn(proyectos);

        // Llamada al método
        List<Proyecto> result = proyectoService.getProyectosBySolicitante(solicitanteId);

        // Verifica que la lista no esté vacía
        assertFalse(result.isEmpty());

        // Verifica que el tamaño de la lista sea 1
        assertEquals(1, result.size());

        // Verifica que el proyecto devuelto sea el mismo que el proyecto de prueba
        assertEquals(proyecto, result.getFirst());
    }
}

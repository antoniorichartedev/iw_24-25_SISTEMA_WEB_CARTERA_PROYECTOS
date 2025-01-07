package unitarios.ServiceTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import projectum.data.entidades.Formulario;
import projectum.data.repositorios.FormularioRepository;
import projectum.data.repositorios.UsuarioRepository;
import projectum.data.servicios.FormularioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FormularioServiceTest {

    @Mock
    private FormularioRepository formularioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private FormularioService formularioService;

    private Formulario formulario;
    private UUID formularioId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        formularioId = UUID.randomUUID();
        formulario = new Formulario();
        formulario.setId(formularioId);
    }

    @Test
    public void testGetAllFormularios() {
        // Datos de prueba
        List<Formulario> formularios = List.of(formulario);
        when(formularioRepository.findAll()).thenReturn(formularios);

        // Llamada al método
        List<Formulario> result = formularioService.getAllFormularios();

        // Verifica que la lista no esté vacía
        assertFalse(result.isEmpty());

        // Verifica que el tamaño de la lista sea 1
        assertEquals(1, result.size());

        // Verifica que el formulario devuelto sea el mismo que el formulario de prueba
        assertEquals(formulario, result.get(0));
    }

    @Test
    public void testGetFormularioById() {
        // Simula el comportamiento de findById
        when(formularioRepository.findById(formularioId)).thenReturn(Optional.of(formulario));

        // Llamada al método
        Optional<Formulario> result = formularioService.getFormularioById(formularioId);

        // Verifica que el formulario esté presente
        assertTrue(result.isPresent());

        // Verifica que el formulario devuelto sea el mismo que el formulario de prueba
        assertEquals(formulario, result.get());
    }

    @Test
    public void testSaveFormulario_NuevoFormulario() {
        // Simula el comportamiento de findById cuando el formulario no existe
        when(formularioRepository.findById(formularioId)).thenReturn(Optional.empty());

        // Simula el comportamiento de save
        doNothing().when(formularioRepository).save(formulario);

        // Llamada al método
        formularioService.saveFormulario(formulario);

        // Verifica que el método save fue invocado
        verify(formularioRepository).save(formulario);
    }

    @Test
    public void testSaveFormulario_Existente() {
        // Simula el comportamiento de findById cuando el formulario ya existe
        when(formularioRepository.findById(formularioId)).thenReturn(Optional.of(formulario));

        // Llamada al método
        formularioService.saveFormulario(formulario);

        // Verifica que el método save NO fue invocado (el formulario ya existía)
        verify(formularioRepository, never()).save(formulario);
    }

    @Test
    public void testDeleteFormulario() {
        // Simula el comportamiento de deleteById
        doNothing().when(formularioRepository).deleteById(formularioId);

        // Llamada al método
        formularioService.deleteFormulario(formularioId);

        // Verifica que el método deleteById fue invocado
        verify(formularioRepository).deleteById(formularioId);
    }
}

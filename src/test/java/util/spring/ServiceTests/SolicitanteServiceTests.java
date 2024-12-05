package util.spring.ServiceTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import proyectum.data.entidades.Solicitante;
import proyectum.data.repositorios.SolicitanteRepository;
import proyectum.data.servicios.SolicitanteService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SolicitanteServiceTests {

    @Mock
    private SolicitanteRepository solicitanteRepository;

    @InjectMocks
    private SolicitanteService solicitanteService;

    private Solicitante solicitante;
    private UUID solicitanteId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        solicitanteId = UUID.randomUUID();
        solicitante = new Solicitante("María", "maria@example.com", "Recursos Humanos");
    }

    @Test
    public void testGetAllSolicitantes() {
        List<Solicitante> solicitantes = List.of(solicitante);
        when(solicitanteRepository.findAll()).thenReturn(solicitantes);

        List<Solicitante> result = solicitanteService.getAllSolicitantes();

        assertEquals(solicitantes, result, "La lista de solicitantes no coincide");
        verify(solicitanteRepository, times(1)).findAll();
    }

    @Test
    public void testGetSolicitanteById_Found() {
        when(solicitanteRepository.findById(solicitanteId)).thenReturn(Optional.of(solicitante));

        Optional<Solicitante> result = solicitanteService.getSolicitanteById(solicitanteId);

        assertTrue(result.isPresent(), "Solicitante debería estar presente");
        assertEquals(solicitante, result.get(), "El solicitante devuelto no coincide");
        verify(solicitanteRepository, times(1)).findById(solicitanteId);
    }

    @Test
    public void testGetSolicitanteById_NotFound() {
        when(solicitanteRepository.findById(solicitanteId)).thenReturn(Optional.empty());

        Optional<Solicitante> result = solicitanteService.getSolicitanteById(solicitanteId);

        assertFalse(result.isPresent(), "Solicitante no debería estar presente");
        verify(solicitanteRepository, times(1)).findById(solicitanteId);
    }

    @Test
    public void testSaveSolicitante() {
        when(solicitanteRepository.save(solicitante)).thenReturn(solicitante);

        Solicitante result = solicitanteService.saveSolicitante(solicitante);

        assertEquals(solicitante, result, "El solicitante guardado no coincide");
        verify(solicitanteRepository, times(1)).save(solicitante);
    }

    @Test
    public void testDeleteSolicitante() {
        doNothing().when(solicitanteRepository).deleteById(solicitanteId);

        solicitanteService.deleteSolicitante(solicitanteId);

        verify(solicitanteRepository, times(1)).deleteById(solicitanteId);
    }
}

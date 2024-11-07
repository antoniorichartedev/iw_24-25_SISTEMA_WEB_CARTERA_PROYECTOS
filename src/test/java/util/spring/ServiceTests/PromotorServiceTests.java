package util.spring.ServiceTests;

import src.spring.Promotor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PromotorServiceTests {

    @Mock
    private PromotorRepository promotorRepository;

    @InjectMocks
    private PromotorService promotorService;

    private Promotor promotor;
    private UUID promotorId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        promotorId = UUID.randomUUID();
        promotor = new Promotor("Carlos", "carlos@uca.es", 3);
    }

    @Test
    public void testGetAllPromotores() {
        List<Promotor> promotores = List.of(promotor);
        when(promotorRepository.findAll()).thenReturn(promotores);

        List<Promotor> result = promotorService.getAllPromotores();

        assertEquals(promotores, result, "La lista de promotores no coincide");
        verify(promotorRepository, times(1)).findAll();
    }

    @Test
    public void testGetPromotorById_Found() {
        when(promotorRepository.findById(promotorId)).thenReturn(Optional.of(promotor));

        Optional<Promotor> result = promotorService.getPromotorById(promotorId);

        assertTrue(result.isPresent(), "Promotor debería estar presente");
        assertEquals(promotor, result.get(), "El promotor devuelto no coincide");
        verify(promotorRepository, times(1)).findById(promotorId);
    }

    @Test
    public void testGetPromotorById_NotFound() {
        when(promotorRepository.findById(promotorId)).thenReturn(Optional.empty());

        Optional<Promotor> result = promotorService.getPromotorById(promotorId);

        assertFalse(result.isPresent(), "Promotor no debería estar presente");
        verify(promotorRepository, times(1)).findById(promotorId);
    }

    @Test
    public void testSavePromotor() {
        when(promotorRepository.save(promotor)).thenReturn(promotor);

        Promotor result = promotorService.savePromotor(promotor);

        assertEquals(promotor, result, "El promotor guardado no coincide");
        verify(promotorRepository, times(1)).save(promotor);
    }

    @Test
    public void testDeletePromotor() {
        doNothing().when(promotorRepository).deleteById(promotorId);

        promotorService.deletePromotor(promotorId);

        verify(promotorRepository, times(1)).deleteById(promotorId);
    }
}

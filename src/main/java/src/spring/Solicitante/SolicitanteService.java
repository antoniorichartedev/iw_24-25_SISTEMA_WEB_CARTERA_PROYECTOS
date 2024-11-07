package src.spring.Solicitante;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SolicitanteService {
    private final SolicitanteRepository solicitanteRepository;

    public SolicitanteService(SolicitanteRepository solicitanteRepository) {
        this.solicitanteRepository = solicitanteRepository;
    }
    public List<Solicitante> getAllSolicitantes() {
        return (List<Solicitante>) solicitanteRepository.findAll();
    }

    public Optional<Solicitante> getSolicitanteById(UUID id) {
        return solicitanteRepository.findById(id);
    }

    public Solicitante saveSolicitante(Solicitante solicitante) {
        return solicitanteRepository.save(solicitante);
    }

    public void deleteSolicitante(UUID id) {
        solicitanteRepository.deleteById(id);
    }
}
package projectum.data.servicios;

import org.springframework.stereotype.Service;
import projectum.data.entidades.Solicitante;
import projectum.data.repositorios.SolicitanteRepository;

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

    public Optional<Solicitante> findSolicitanteByUsername(String username) {
        return solicitanteRepository.findByUsername(username);
    }

    public Optional<Solicitante> findSolicitanteByCorreo(String correo) {
        return solicitanteRepository.findByCorreo(correo);
    }
}
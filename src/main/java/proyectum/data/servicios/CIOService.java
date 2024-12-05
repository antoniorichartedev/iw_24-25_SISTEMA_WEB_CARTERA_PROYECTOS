package proyectum.data.servicios;

import org.springframework.stereotype.Service;
import proyectum.data.entidades.CIO;
import proyectum.data.repositorios.CIORepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CIOService {
    private final CIORepository cioRepository;

    public CIOService(CIORepository cioRepository)
    {
        this.cioRepository = cioRepository;
    }

    public List<CIO> getAllCIOs()
    {
        return (List<CIO>) cioRepository.findAll();
    }

    public Optional<CIO> getCIOById(UUID id)
    {
        return cioRepository.findById(id);
    }

    public CIO saveCIO(CIO cio)
    {
        if(cio.getUsername() != null)
        {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        return cioRepository.save(cio);
    }

    public void deleteCIO(UUID id)
    {
        cioRepository.deleteById(id);
    }
}

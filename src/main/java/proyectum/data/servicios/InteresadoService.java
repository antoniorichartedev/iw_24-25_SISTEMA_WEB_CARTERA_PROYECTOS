package proyectum.data.servicios;

import org.springframework.stereotype.Service;
import proyectum.data.entidades.Interesado;
import proyectum.data.repositorios.InteresadoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InteresadoService {

    private final InteresadoRepository interesadoRepository;

    public InteresadoService(InteresadoRepository interesadoRepository)
    {
        this.interesadoRepository = interesadoRepository;
    }

    public List<Interesado> getAllInteresados()
    {
        return (List<Interesado>) interesadoRepository.findAll();
    }

    public Optional<Interesado> getInteresadoById(UUID id)
    {
        return interesadoRepository.findById(id);
    }

    public Interesado saveInteresado(Interesado interesado)
    {
        if(interesado.getFinanciacionAportada().compareTo(BigDecimal.ZERO) < 0)
        {
            throw new IllegalArgumentException("Financiación aportada inválida");
        }

        return interesadoRepository.save(interesado);
    }

    public void deleteInteresado(UUID id)
    {
        interesadoRepository.deleteById(id);
    }
}

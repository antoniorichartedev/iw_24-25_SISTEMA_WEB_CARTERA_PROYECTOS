package projectum.data.servicios;

import org.springframework.stereotype.Service;
import projectum.data.entidades.Convocatoria;
import projectum.data.repositorios.ConvocatoriaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConvocatoriaService {
    private final ConvocatoriaRepository convocatoriaRepository;

    public ConvocatoriaService(ConvocatoriaRepository convocatoriRepository) {
        this.convocatoriaRepository = convocatoriRepository;
    }
    public void addConvocatoria(Convocatoria convocatoria) {
        convocatoriaRepository.save(convocatoria);
    }

    public Convocatoria saveConvocatoria(Convocatoria convocatoria) {
        return convocatoriaRepository.save(convocatoria);
    }

    public List<Convocatoria> getAllConvocatorias() {
        return (List<Convocatoria>) convocatoriaRepository.findAll();
    }

    public void delete(UUID id){
        convocatoriaRepository.deleteById(id);
    }

    public Optional<Convocatoria> getConvocatoriaByNombre(String nombre) {
        return convocatoriaRepository.findByNombre(nombre);
    }

    public Optional<Convocatoria> getConvocatoriaById(UUID convocatoriaID) {
        return convocatoriaRepository.findById(convocatoriaID);
    }

    public Optional<Convocatoria> getConvocatoriaActual(){
        List<Convocatoria> conv = getAllConvocatorias();

        for (Convocatoria convocatoria : conv) {
            if (convocatoria.getActividad()) {
                return getConvocatoriaById(convocatoria.getId());
            }
        }
        return Optional.empty();
    }
}

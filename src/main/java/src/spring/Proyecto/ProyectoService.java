package src.spring.Proyecto;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Servicios para el manejo de datos en la base de datos.
@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public List<Proyecto> getAllProyectos() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    public Optional<Proyecto> getProyectoById(UUID id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto saveProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void deleteProyecto(UUID id) {
        proyectoRepository.deleteById(id);
    }
}


package projectum.data.servicios;

import org.springframework.stereotype.Service;
import projectum.data.entidades.Proyecto;
import projectum.data.repositorios.ProyectoRepository;

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
    public void addProyecto(Proyecto proyecto) {
        proyectoRepository.save(proyecto);
    }

    public List<Proyecto> getAllProyectos() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    public Optional<Proyecto> getProyectoById(UUID id) {
        return proyectoRepository.findById(id);
    }

    public Optional<Proyecto> getProyectoByTitulo(String nombre) { return proyectoRepository.findByTitulo(nombre); }

    public Proyecto saveProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void deleteProyecto(UUID id) {
        proyectoRepository.deleteById(id);
    }

    public List<Proyecto> getProyectosBySolicitante(UUID solicitanteId) {
        return proyectoRepository.findBySolicitanteId(solicitanteId);
    }

}
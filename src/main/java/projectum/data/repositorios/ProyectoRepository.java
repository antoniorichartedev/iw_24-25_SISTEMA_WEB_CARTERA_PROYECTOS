package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Proyecto;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface ProyectoRepository extends CrudRepository<Proyecto, UUID> {
    List<Proyecto> findBySolicitanteId(UUID solicitante_id);
    Optional<Proyecto> findByTitulo(String nombre);
}
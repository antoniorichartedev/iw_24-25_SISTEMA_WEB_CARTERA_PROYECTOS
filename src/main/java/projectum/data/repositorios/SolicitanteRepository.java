package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Solicitante;

import java.util.UUID;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface SolicitanteRepository extends CrudRepository<Solicitante, UUID> {
}
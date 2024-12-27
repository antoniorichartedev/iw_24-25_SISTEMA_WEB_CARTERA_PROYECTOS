package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Solicitante;

import java.util.Optional;
import java.util.UUID;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface SolicitanteRepository extends CrudRepository<Solicitante, UUID> {
    Optional<Solicitante> findByUsername(String username);
    Optional<Solicitante> findByCorreo(String correo);
}
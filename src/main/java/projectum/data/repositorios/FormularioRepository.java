package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Formulario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FormularioRepository extends CrudRepository<Formulario, UUID> {
    List<Formulario> findByProyectoId(UUID proyecto_id);
}

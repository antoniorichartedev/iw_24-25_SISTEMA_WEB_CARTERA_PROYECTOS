package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Formulario;

import java.util.UUID;

public interface FormularioRepository extends CrudRepository<Formulario, UUID> {

}

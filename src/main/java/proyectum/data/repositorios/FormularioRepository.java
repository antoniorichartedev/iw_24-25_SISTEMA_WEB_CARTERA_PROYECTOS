package proyectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import proyectum.data.entidades.Formulario;

import java.util.UUID;

public interface FormularioRepository extends CrudRepository<Formulario, UUID> {
}

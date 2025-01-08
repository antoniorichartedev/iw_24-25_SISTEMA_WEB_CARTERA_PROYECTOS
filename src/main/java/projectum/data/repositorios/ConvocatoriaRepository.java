package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Convocatoria;
import java.util.UUID;

public interface ConvocatoriaRepository extends CrudRepository<Convocatoria, UUID> {
}

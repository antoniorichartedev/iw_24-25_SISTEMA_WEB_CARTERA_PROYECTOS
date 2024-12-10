package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Promotor;

import java.util.UUID;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface PromotorRepository extends CrudRepository<Promotor, UUID> {
}

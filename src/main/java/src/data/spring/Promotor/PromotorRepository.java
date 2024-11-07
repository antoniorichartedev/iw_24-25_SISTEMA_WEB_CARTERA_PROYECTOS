package src.data.spring.Promotor;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface PromotorRepository extends CrudRepository<Promotor, UUID> {
}

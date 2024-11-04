package src.spring.Promotor;

import org.springframework.data.repository.CrudRepository;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {
}

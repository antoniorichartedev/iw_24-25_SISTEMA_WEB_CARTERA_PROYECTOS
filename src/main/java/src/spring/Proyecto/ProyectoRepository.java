package src.spring.Proyecto;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface ProyectoRepository extends CrudRepository<Proyecto, UUID> {
}


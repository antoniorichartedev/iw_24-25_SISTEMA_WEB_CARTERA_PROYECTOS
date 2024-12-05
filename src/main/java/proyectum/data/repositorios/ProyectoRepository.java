package proyectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import proyectum.data.entidades.Proyecto;

import java.util.UUID;

//Repositorio CRUD para el manejo de datos en la base de datos.
public interface ProyectoRepository extends CrudRepository<Proyecto, UUID> {
}


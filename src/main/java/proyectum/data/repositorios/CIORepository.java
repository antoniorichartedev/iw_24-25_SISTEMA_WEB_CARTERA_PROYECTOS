package proyectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import proyectum.data.entidades.CIO;

import java.util.List;
import java.util.UUID;

public interface CIORepository extends CrudRepository<CIO, UUID> {
    // Buscar CIO´s por nombre.
    List<CIO> findByNombre(String nombre);

    // Obtener el CIO por el correo.
    CIO findByCorreo(String correo);

    // Obtener CIO al que está asociado a un determinado proyecto.
    // ...
}

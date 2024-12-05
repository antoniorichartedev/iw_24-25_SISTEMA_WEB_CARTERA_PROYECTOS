package proyectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import proyectum.data.entidades.Interesado;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

public interface InteresadoRepository extends CrudRepository<Interesado, UUID>{

    // Buscar interesados por nombre.
    List<Interesado> findByNombre(String nombre);

    // Obtener el interesado por el correo.
    Interesado findByCorreo(String correo);

    // Obtener una lista de interesados que hayan aportado más de X cantidad.
    List<Interesado> findByFinanciacionAportadaGreaterThan(BigDecimal financiacionAportada);

    // Obtener una lista de interesados según el nombre del proyecto.
    //@Query("SELECT i from Interesado i JOIN i.Proyecto p WHERE p.nombre = :nombre_proyecto")
    //List<Interesado> findInteresadosByProyectoNombre(@Param("nombre_proyecto") String nombre_proyecto);

}

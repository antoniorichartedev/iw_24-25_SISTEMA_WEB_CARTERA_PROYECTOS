package projectum.data.repositorios;

import org.springframework.data.repository.CrudRepository;
import projectum.data.entidades.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
    Usuario findByNombre(String nombre);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByEstadoTrue();
}

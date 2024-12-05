package proyectum.data.servicios;

import org.springframework.stereotype.Service;
import proyectum.data.entidades.Usuario;
import proyectum.data.repositorios.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public List<Usuario> getAllPersona() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> getPersonaById(UUID id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByCorreo(String correo){ return usuarioRepository.findByCorreo(correo);}

    public Usuario savePersona(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletePersona(UUID id) {
        usuarioRepository.deleteById(id);
    }
}

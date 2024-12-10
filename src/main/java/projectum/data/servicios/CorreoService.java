package projectum.data.servicios;

import org.springframework.stereotype.Service;
import projectum.data.entidades.Usuario;

@Service
public interface CorreoService {
    boolean sendRegistrationCorreo(Usuario usuario);
}

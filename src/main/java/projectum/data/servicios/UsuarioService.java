package projectum.data.servicios;

import jakarta.persistence.Transient;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectum.data.Rol;
import projectum.data.entidades.Formulario;
import projectum.data.entidades.Usuario;
import projectum.data.repositorios.FormularioRepository;
import projectum.data.repositorios.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CorreoService correoService;
    private final PasswordEncoder passwordEncoder;
    private final FormularioRepository formularioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, CorreoService correoService, PasswordEncoder passwordEncoder, FormularioRepository formularioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.correoService = correoService;
        this.passwordEncoder = passwordEncoder;
        this.formularioRepository = formularioRepository;
    }

    public long count(){ return usuarioRepository.count(); }

    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> loadUserById(UUID id) { return usuarioRepository.findById(id); }

    public Optional<Usuario> loadUserByCorreo(String correo){ return usuarioRepository.findByCorreo(correo);}

    public Usuario loadUserByUsername(String username) { return usuarioRepository.findByUsername(username); }

    public List<Usuario> loadUsersActivados() { return usuarioRepository.findByEstadoTrue(); }

    public void save(Usuario usuario) { usuarioRepository.save(usuario); }

    public void delete(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public boolean RegistrarUsuario(Usuario usuario)
    {
        // Introducimos la contraseña cifrada...
        usuario.setHashedPassword(passwordEncoder.encode(usuario.getPassword()));

        // el código de registro...
        usuario.setCodigoRegistro(UUID.randomUUID().toString().substring(0, 5));

        // Si el usuario ya existe, evidentemente no lo registramos.
        if(usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            return false;
        }

        // No activamos el usuario, por ahora.
        usuario.setEstado(false);

        // Si no existe, debemos registrarlo, por lo que guardamos dicho usuario.
        usuarioRepository.save(usuario);

        // Aquí va la parte de enviar el email de confirmación.
        correoService.sendRegistrationCorreo(usuario);

        return true;
    }

    public boolean activarUsuario(String correo, String codigo)
    {
        // Comprobamos que ese usuario existe, sino no se puede activar. Si existe, debemos comprobar que NO está
        // activado y que el código de registro coincida con el que ha dado.
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if(usuario.isPresent() && !usuario.get().getEstado() && usuario.get().getCodigoRegistro().equals(codigo)) {
            usuario.get().setEstado(true);
            usuarioRepository.save(usuario.get());
            return true;
        }

        // Si resulta ser que no existe o ya está activo, devolvemos false.
        return false;
    }

    public boolean updateUsuario(Usuario usuario) {
        // Comprobamos que el usuario existe para poder modificar sus parámetros.
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(usuario.getCorreo());
        if(usuarioExistente.isPresent()) {
            if(!usuario.getNombre().isEmpty()) {
                usuarioExistente.get().setNombre(usuario.getNombre());
            }

            if(!usuario.getUsername().isEmpty()) {
                usuarioExistente.get().setUsername(usuario.getUsername());
            }

            if(usuario.getPassword() != null) {
                usuarioExistente.get().setHashedPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            usuarioRepository.save(usuarioExistente.get());
            return true;
        }

        // Si resulta ser que no existe, pues devolvemos false porque no podemos modificar un usuario que no existe.
        return false;
    }


}

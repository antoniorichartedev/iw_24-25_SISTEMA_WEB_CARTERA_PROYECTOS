package projectum.data.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectum.data.Rol;
import projectum.data.entidades.Usuario;
import projectum.data.repositorios.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CorreoService correoService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, CorreoService correoService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.correoService = correoService;
        this.passwordEncoder = passwordEncoder;
    }

    public long count(){ return usuarioRepository.count(); }

    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> loadUserById(UUID id) { return usuarioRepository.findById(id); }

    public Optional<Usuario> loadUserByCorreo(String correo){ return usuarioRepository.findByCorreo(correo);}

    public Usuario loadUserByUsername(String username) { return usuarioRepository.findByUsername(username); }

    public List<Usuario> loadUsersActivados() { return usuarioRepository.findByEstadoTrue(); }

    public void delete(UUID id) {
        usuarioRepository.deleteById(id);
    }

    public boolean RegistrarUsuario(Usuario usuario)
    {
        // Introducimos la contraseña cifrada...
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // el código de registro...
        usuario.setCodigoRegistro(UUID.randomUUID().toString().substring(0, 5));

        // y el rol.
        usuario.setRol(Rol.ADMIN);

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

    @Transactional
    public Usuario loadUserByNombre(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        if (usuario == null) {
            throw new UsernameNotFoundException("No userProfile present with username: " + nombre);
        } else {
            return usuario;
        }
    }
}

package projectum.data.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import projectum.ApiResponse;
import projectum.data.Rol;
import projectum.data.entidades.Usuario;
import projectum.data.repositorios.FormularioRepository;
import projectum.data.repositorios.UsuarioRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CorreoService correoService;
    private final PasswordEncoder passwordEncoder;
    private final FormularioRepository formularioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, CorreoService correoService, PasswordEncoder passwordEncoder, FormularioRepository formRepository) {
        this.usuarioRepository = usuarioRepository;
        this.correoService = correoService;
        this.passwordEncoder = passwordEncoder;
        this.formularioRepository = formRepository;
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

    public void updateContrasena(String correo, String nuevaContrasena) {
        // Buscar al usuario por correo
        Optional<Usuario> maybeUser = usuarioRepository.findByCorreo(correo);
        if (maybeUser.isPresent()) {
            Usuario user = maybeUser.get();
            user.setHashedPassword(passwordEncoder.encode(nuevaContrasena));

            // Guardar el usuario con la nueva contraseña
            usuarioRepository.save(user);
        } else {
            throw new NoSuchElementException("Usuario con el correo " + correo + " no encontrado.");
        }
    }


    public boolean validarCodigoRecuperacion(String correo, String codigo) {
        Optional<Usuario> maybeUser = usuarioRepository.findByCorreo(correo);

        if (maybeUser.isPresent()) {
            Usuario user = maybeUser.get();
            if (codigo == null || codigo.isBlank()) {
                throw new IllegalArgumentException("La nueva contraseña no puede estar vacía.");
            }

            return user.getCodigoRegistro().equals(codigo);
        }
        return false;
    }

    @Cacheable("promotores")
    public void cargarPromotoresDesdeApi() {
        if (!existenPromotoresEnBaseDeDatos()) {
            String url = "https://e608f590-1a0b-43c5-b363-e5a883961765.mock.pstmn.io/sponsors";

            RestTemplate restTemplate = new RestTemplate();

            // Mapear la respuesta al objeto ApiResponse
            ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

            if (response != null && response.getData() != null) {
                for (Usuario promotor : response.getData()) {
                    guardarPromotorSiNoExiste(promotor);
                }
            }
        }
    }

    private boolean existenPromotoresEnBaseDeDatos() {
        return !usuarioRepository.findByRol(Rol.PROMOTOR).isEmpty();
    }

    private void guardarPromotorSiNoExiste(Usuario promotor) {

        // Comprueba si el correo es nulo o vacío
        if (promotor.getCorreo() == null || promotor.getCorreo().isBlank()) {
            // Asigna un correo predeterminado basado en el nombre del promotor
            String correoGenerado = promotor.getNombre().toLowerCase().replaceAll("\\s+", ".") + "@gmail.com";
            promotor.setCorreo(correoGenerado);
        }

        if (usuarioRepository.findByCorreo(promotor.getCorreo()).isEmpty()) {
            promotor.setRol(Rol.PROMOTOR);
            promotor.setEstado(true);
            promotor.setUsername(promotor.getNombre());
            promotor.setPassword(promotor.getNombre());
            promotor.setHashedPassword(passwordEncoder.encode(promotor.getPassword()));
            usuarioRepository.save(promotor);
        }
    }

}

package unitarios.ServiceTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import projectum.data.entidades.Usuario;
import projectum.data.repositorios.UsuarioRepository;
import projectum.data.servicios.CorreoService;
import projectum.data.servicios.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CorreoService correoService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UUID usuarioId = UUID.randomUUID();
        usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setCorreo("usuario@correo.com");
        usuario.setPassword("password123");
    }

    @Test
    public void testRegistrarUsuario_Exito() {
        // Simula que el correo no está registrado
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(usuario.getPassword())).thenReturn("hashedPassword");

        // Llamada al método
        boolean result = usuarioService.RegistrarUsuario(usuario);

        // Verifica que el usuario fue registrado correctamente
        assertTrue(result);

        // Verifica que el correo de confirmación fue enviado
        verify(correoService).sendRegistrationCorreo(usuario);

        // Verifica que el usuario fue guardado
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void testRegistrarUsuario_UsuarioExistente() {
        // Simula que el correo ya está registrado
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        // Llamada al método
        boolean result = usuarioService.RegistrarUsuario(usuario);

        // Verifica que no se ha registrado el usuario
        assertFalse(result);

        // Verifica que no se haya enviado el correo de confirmación
        verify(correoService, never()).sendRegistrationCorreo(usuario);
    }

    @Test
    public void testActivarUsuario_Exito() {
        // Simula que el usuario existe y no está activado
        usuario.setEstado(false);
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        // Llamada al método
        boolean result = usuarioService.activarUsuario(usuario.getCorreo(), usuario.getCodigoRegistro());

        // Verifica que el usuario fue activado
        assertTrue(result);
        assertTrue(usuario.getEstado());

        // Verifica que el usuario fue guardado después de la activación
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void testActivarUsuario_UsuarioNoExistente() {
        // Simula que el usuario no existe
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.empty());

        // Llamada al método
        boolean result = usuarioService.activarUsuario(usuario.getCorreo(), "codigo123");

        // Verifica que la activación falla
        assertFalse(result);
    }

    @Test
    public void testUpdateUsuario_Exito() {
        // Simula que el usuario existe
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("newPassword")).thenReturn("newHashedPassword");

        // Llamada al método
        usuario.setPassword("newPassword");
        boolean result = usuarioService.updateUsuario(usuario);

        // Verifica que la actualización fue exitosa
        assertTrue(result);
        assertEquals("newHashedPassword", usuario.getHashedPassword());

        // Verifica que el usuario fue guardado
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void testUpdateUsuario_UsuarioNoExistente() {
        // Simula que el usuario no existe
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.empty());

        // Llamada al método
        boolean result = usuarioService.updateUsuario(usuario);

        // Verifica que la actualización falla
        assertFalse(result);
    }

    @Test
    public void testUpdateContrasena_Exito() {
        // Simula que el usuario existe
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("newPassword")).thenReturn("newHashedPassword");

        // Llamada al método
        usuarioService.updateContrasena(usuario.getCorreo(), "newPassword");

        // Verifica que la contraseña fue actualizada
        assertEquals("newHashedPassword", usuario.getHashedPassword());

        // Verifica que el usuario fue guardado con la nueva contraseña
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void testUpdateContrasena_UsuarioNoExistente() {
        // Simula que el usuario no existe
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.empty());

        // Llamada al método
        assertThrows(NoSuchElementException.class, () -> usuarioService.updateContrasena(usuario.getCorreo(), "newPassword"));
    }
}


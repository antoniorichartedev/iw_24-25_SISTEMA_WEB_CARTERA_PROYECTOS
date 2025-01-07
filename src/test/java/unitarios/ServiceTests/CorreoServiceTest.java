package unitarios.ServiceTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.CorreoRealService;

public class CorreoServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private CorreoRealService correoRealService;

    @Mock
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendRegistrationCorreo() {
        // Datos de prueba
        String correo = "usuario@correo.com";
        String codigoRegistro = "123456";
        when(usuario.getCorreo()).thenReturn(correo);
        when(usuario.getCodigoRegistro()).thenReturn(codigoRegistro);

        // Simula el comportamiento de enviarCorreo
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        doNothing().when(mailSender).send(any(MimeMessage.class)); // Cambiado a doNothing()

        // Llamada al método
        boolean result = correoRealService.sendRegistrationCorreo(usuario);

        // Verifica que el correo se haya enviado correctamente
        assertTrue(result);

        // Verifica que el método enviarCorreo fue invocado
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testEnviarCorreoRecuperacion() {
        // Datos de prueba
        String correo = "usuario@correo.com";
        String codigoRecuperacion = "abcdef";
        when(usuario.getCorreo()).thenReturn(correo);
        when(usuario.getCodigoRegistro()).thenReturn(codigoRecuperacion);

        // Simula el comportamiento de enviarCorreo
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        doNothing().when(mailSender).send(any(MimeMessage.class));

        // Llamada al método
        boolean result = correoRealService.enviarCorreoRecuperacion(usuario);

        // Verifica que el correo se haya enviado correctamente
        assertTrue(result);

        // Verifica que el método enviarCorreo fue invocado
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testEnviarCorreoAvalado() {
        // Datos de prueba
        String correo = "usuario@correo.com";
        String nombre = "Usuario de prueba";
        when(usuario.getCorreo()).thenReturn(correo);
        when(usuario.getNombre()).thenReturn(nombre);

        // Simula el comportamiento de enviarCorreo
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        doNothing().when(mailSender).send(any(MimeMessage.class));

        // Llamada al método
        boolean result = correoRealService.enviarCorreoAvalado(usuario);

        // Verifica que el correo se haya enviado correctamente
        assertTrue(result);

        // Verifica que el método enviarCorreo fue invocado
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    public void testEnviarCorreo_Falla() {
        // Datos de prueba
        String correo = "usuario@correo.com";
        when(usuario.getCorreo()).thenReturn(correo);

        // Simula una excepción en el envío del correo
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        doThrow(new MailException("Error de correo") {}).when(mailSender).send(any(MimeMessage.class));

        // Llamada al método
        boolean result = correoRealService.sendRegistrationCorreo(usuario);

        // Verifica que el método devuelve false cuando ocurre un error
        assertFalse(result);
    }

    @Test
    public void testConstruirCuerpoCorreo_Registro() {
        // Datos de prueba
        Usuario usuario = new Usuario();
        usuario.setCodigoRegistro("123456");

        String cuerpo = correoRealService.construirCuerpoCorreo("registro", usuario);

        // Verifica que el cuerpo del correo sea el esperado
        assertTrue(cuerpo.contains("Código de activación: 123456"));
    }

    @Test
    public void testConstruirCuerpoCorreo_Recuperacion() {
        // Datos de prueba
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario de prueba");
        usuario.setCodigoRegistro("abcdef");

        String cuerpo = correoRealService.construirCuerpoCorreo("recuperacion", usuario);

        // Verifica que el cuerpo del correo sea el esperado
        assertTrue(cuerpo.contains("Código de recuperación: abcdef"));
    }

    @Test
    public void testConstruirCuerpoCorreo_Avalado() {
        // Datos de prueba
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario de prueba");

        String cuerpo = correoRealService.construirCuerpoCorreo("avalado", usuario);

        // Verifica que el cuerpo del correo sea el esperado
        assertTrue(cuerpo.contains("¡Enhorabuena! Nos complace informarte que tu proyecto ha sido avalado exitosamente."));
    }
}



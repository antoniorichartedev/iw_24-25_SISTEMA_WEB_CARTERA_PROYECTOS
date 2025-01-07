package projectum.data.servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import projectum.data.entidades.Usuario;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class CorreoRealService implements CorreoService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.host}")
    private String HOST;

    @Value("${spring.mail.host}")
    private String PORT;

    @Value("${spring.mail.username}")
    private String correoDefault;

    @Value("${server.port}")
    private String serverPort;

    public CorreoRealService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String construirCuerpoCorreo(String tipo, Usuario usuario) {
        if ("registro".equals(tipo)) {
            return "Bienvenido a Projectum!\n\n" +
                    "Para activar tu cuenta, introduzca en la página web " + getServerUrl() + " el siguiente código:\n" +
                    "\n" +
                    "Código de activación: " + usuario.getCodigoRegistro();
        } else if ("recuperacion".equals(tipo)) {
            return "Hola " + usuario.getNombre() + ",\n\n" +
                    "Para recuperar tu contraseña, Introduzca el siguiente código en su navegador:\n" +
                    "\n\n" +
                    "Código de recuperación: " + usuario.getCodigoRegistro();
        } else if ("avalado".equals(tipo)) {
            return "Hola " + usuario.getNombre() + ",\n\n" +
                    "¡Enhorabuena! Nos complace informarte que tu proyecto ha sido avalado exitosamente.\n" +
                    "Puedes acceder a tu proyecto en el sistema para continuar gestionándolo.\n\n" +
                    "Gracias por confiar en Projectum.";
        }
        return "";
    }

    private String getServerUrl() {
        return "http://" + InetAddress.getLoopbackAddress().getHostAddress() + ":" + serverPort + "/confirmar-correo";
    }

    @Override
    public boolean sendRegistrationCorreo(Usuario usuario) {
        return enviarCorreo(usuario, "registro", "Bienvenido a Projectum");
    }

    public boolean enviarCorreoRecuperacion(Usuario usuario) {
        return enviarCorreo(usuario, "recuperacion", "Recuperación de contraseña");
    }

    public boolean enviarCorreoAvalado(Usuario usuario) {
        return enviarCorreo(usuario, "avalado", "¡Tu proyecto ha sido avalado!");
    }

    private boolean enviarCorreo(Usuario usuario, String tipo, String asunto) {
        MimeMessage mensaje = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");
            helper.setFrom(correoDefault);
            helper.setTo(usuario.getCorreo());
            helper.setSubject(asunto);
            helper.setText(construirCuerpoCorreo(tipo, usuario), false);
            mailSender.send(mensaje);
            return true;
        } catch (MailException | MessagingException ex) {
            return false;
        }
    }
}

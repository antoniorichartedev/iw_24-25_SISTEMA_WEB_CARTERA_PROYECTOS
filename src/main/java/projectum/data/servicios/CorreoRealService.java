package projectum.data.servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import projectum.data.entidades.Proyecto;
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

    public String construirCuerpoCorreo(String tipo, Usuario usuario, Proyecto pr) {
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
                    "¡Enhorabuena! Nos complace informarte que tu proyecto " + pr.getTitulo() + " ha sido avalado exitosamente.\n" +
                    "Puedes acceder a tu proyecto en el sistema para continuar gestionándolo.\n\n" +
                    "Gracias por confiar en Projectum.";
        } else if ("valorado".equals(tipo)) {
            return "Hola " + usuario.getNombre() + ",\n\n" +
                    "Tu proyecto " + pr.getTitulo() + " ha sido valorado.\n" +
                    "Quedamos a la espera de que el CIO acepte o rechaze su proyecto.\n" +
                    "Gracias por confiar en Projectum.";
        } else if ("aceptado".equals(tipo)) {
            return "Hola " + usuario.getNombre() + ",\n\n" +
                    "Tu proyecto " + pr.getTitulo() + " ha sido aceptado, ¡Enhorabuena!\n" +
                    "El CIO se pondrá en contacto con usted para más detalles.\n" +
                    "Gracias por confiar en Projectum.";
        } else if ("rechazado".equals(tipo)) {
            return "Hola " + usuario.getNombre() + ",\n\n" +
                    "Tu proyecto " + pr.getTitulo() + " ha sido rechazado.\n" +
                    "Por favor, vaya a la app para ver las razones por las que ha sido rechazado.\n" +
                    "Gracias por confiar en Projectum.";
        } else if ("valoradoCIO".equals(tipo)) {
            return "Hola " + usuario.getNombre() + ",\n\n" +
                    "Tu proyecto " + pr.getTitulo() + " ha sido valorado por el CIO.\n" +
                    "Quedamos a la espera de que la OTP valore su proyecto.\n" +
                    "Gracias por confiar en Projectum.";
        } else if ("valoradoOT".equals(tipo)) {
            return "Hola " + usuario.getNombre() + ",\n\n" +
                    "Tu proyecto " + pr.getTitulo() + " ha sido valorado por la OTP.\n" +
                    "Quedamos a la espera de que el CIO valore su proyecto.\n" +
                    "Gracias por confiar en Projectum.";
        }
        return "";
    }

    private String getServerUrl() {
        return "http://" + InetAddress.getLoopbackAddress().getHostAddress() + ":" + serverPort + "/confirmar-correo";
    }

    @Override
    public boolean sendRegistrationCorreo(Usuario usuario) {
        return enviarCorreo(usuario, "registro", "Bienvenido a Projectum", null);
    }

    public boolean enviarCorreoRecuperacion(Usuario usuario) {
        return enviarCorreo(usuario, "recuperacion", "Recuperación de contraseña", null);
    }

    public boolean enviarCorreoAvalado(Usuario usuario, Proyecto pr) {
        return enviarCorreo(usuario, "avalado", "¡Tu proyecto ha sido avalado!", pr);
    }

    public boolean enviarCorreoProyectoValorado(Usuario usuario, Proyecto pr) {
        return enviarCorreo(usuario, "valorado", "¡Tu proyecto ha sido valorado!", pr);
    }

    public boolean enviarCorreoProyectoValoradoOT(Usuario usuario, Proyecto pr) {
        return enviarCorreo(usuario, "valoradoOT", "¡Tu proyecto ha sido valorado por la OTP!", pr);
    }

    public boolean enviarCorreoProyectoValoradoCIO(Usuario usuario, Proyecto pr) {
        return enviarCorreo(usuario, "valoradoCIO", "¡Tu proyecto ha sido valorado por el CIO!", pr);
    }

    public boolean enviarCorreoProyectoAceptado(Usuario usuario, Proyecto pr) {
        return enviarCorreo(usuario, "aceptado", "¡Tu proyecto ha sido aceptado!", pr);
    }

    public boolean enviarCorreoProyectoRechazado(Usuario usuario, Proyecto pr) {
        return enviarCorreo(usuario, "rechazado", "Lo siento, tu proyecto ha sido rechazado", pr);
    }

    private boolean enviarCorreo(Usuario usuario, String tipo, String asunto, Proyecto pr) {
        MimeMessage mensaje = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");
            helper.setFrom(correoDefault);
            helper.setTo(usuario.getCorreo());
            helper.setSubject(asunto);
            helper.setText(construirCuerpoCorreo(tipo, usuario, pr), false);
            mailSender.send(mensaje);
            return true;
        } catch (MailException | MessagingException ex) {
            return false;
        }
    }
}

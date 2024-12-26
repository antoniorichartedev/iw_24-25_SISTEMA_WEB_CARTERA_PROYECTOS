package projectum.data.servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.CorreoService;
import java.net.InetAddress;

@Service
public class CorreoRealService implements CorreoService {

    // Usamos JavaMailSender para poder crear y enviar correos. Nos hace falta para poder crear y enviar el correo
    // de confirmación de cuenta.
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String correoDefault;

    @Value("${server.port}")
    private String serverPort;

    public CorreoRealService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Generador de la URL del servidor.
    private String getServerUrl() {
        String url = "http://" + InetAddress.getLoopbackAddress().getHostAddress() + ":" + serverPort + "/confirmar-correo";
        return url;
    }

    @Override
    public boolean sendRegistrationCorreo(Usuario usuario) {
        // Creamos un objeto de tipo MimeMessage, que viene a ser un correo en formato MIME.
        MimeMessage mensaje = mailSender.createMimeMessage();

        // Con este objeto, podemos construir fácilmente el correo que queremos mandar.
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");

        // Asunto del correo.
        String asunto = "Bienvenido";

        // Cuerpo del correo.
        String cuerpo = "Para poder disfrutar de los servicios de Projectum, debes activar tu cuenta.\n" +
                "Para ello, debes ir a este enlace: " + getServerUrl() + " y debes de introducir el siguiente código: "
                + usuario.getCodigoRegistro();

        // Procedemos a construir el correo.
        try {
            helper.setFrom(correoDefault);
            helper.setTo(usuario.getCorreo());
            helper.setSubject(asunto);
            helper.setText(cuerpo);

            // Mandamos el correo.
            this.mailSender.send(mensaje);
        }catch (MailException | MessagingException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}

package projectum.vistas.olvidasteContrasena;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.CorreoRealService;
import projectum.data.servicios.UsuarioService;

import java.util.Optional;

@Route("recuperar-contrasena")
@PageTitle("Recuperar Contraseña")
@AnonymousAllowed
public class RecuperarContrasenaView extends VerticalLayout {

    private final UsuarioService usuarioService;
    private final CorreoRealService  correoRealService;

    public RecuperarContrasenaView(UsuarioService userService, CorreoRealService correoService) {

        this.correoRealService =correoService;
        this.usuarioService = userService;

        // Título de la página
        H1 titulo = new H1("Recuperar contraseña");

        // Descripción para el usuario
        Text descripcion = new Text("Por favor, ingresa tu correo electrónico para recibir un enlace de recuperación de contraseña.");

        // Campo para el correo electrónico
        EmailField emailField = new EmailField("Correo electrónico");
        emailField.setPlaceholder("usuario@example.com");
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage("Por favor, introduce un correo electrónico válido.");

        // Botón de enviar
        Button enviarButton = new Button("Enviar", event -> {
            String email = emailField.getValue();
            Optional<Usuario> user = usuarioService.loadUserByCorreo(email);
            if (emailField.isInvalid() || email.isEmpty() || user.isEmpty()) {
                Notification.show("Por favor, introduce un correo electrónico válido.", 3000, Notification.Position.MIDDLE);
            } else {
                Usuario maybeUser = user.get();
                // Lógica para enviar el correo de recuperación (simulada aquí)
                correoRealService.enviarCorreoRecuperacion(maybeUser);
                Notification.show("Recibirás un enlace para recuperar tu contraseña.", 5000, Notification.Position.MIDDLE);
            }
        });

        // Añadir componentes al layout
        setAlignItems(Alignment.CENTER);
        add(titulo, descripcion, emailField, enviarButton);
    }
}

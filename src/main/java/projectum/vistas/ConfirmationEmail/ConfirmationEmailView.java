package projectum.vistas.ConfirmationEmail;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.CorreoService;
import projectum.data.servicios.UsuarioService;
import projectum.security.login.AuthenticatedUser;
import com.vaadin.flow.component.button.Button;

@Route("confirmar-correo")
@PageTitle("Confirmar correo")
@PermitAll
public class ConfirmationEmailView extends VerticalLayout {

    // Servicios del Usuario.
    private final UsuarioService usuarioService;
    private final AuthenticatedUser authenticatedUser;
    private Usuario usuario;

    // Servicios para el correo.
    private final CorreoService correoService;

    public ConfirmationEmailView(UsuarioService userService, AuthenticatedUser AuthenticatedUsr, CorreoService emailService) {
        this.usuarioService = userService;
        this.authenticatedUser = AuthenticatedUsr;
        this.correoService = emailService;

        // Buscamos al usuario que se ha autenticado para confirmar su email.
        authenticatedUser.get().ifPresent(user ->
                this.usuario = usuarioService.loadUserByCorreo(user.getCorreo()).orElse(null)
        );

        // Creamos los componentes de la página.
        TextField codigo = new TextField("Código de confirmación");
        Button button = new Button("Confirmar");
        Button reenviarCodigo = new Button("Reenviar código");

        // Configurar el diseño
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Le damos vida al botón con su acción correspondiente.
        button.addClickListener(event -> {
            String codConfirmacion = codigo.getValue();

            // Activamos el usuario con el código que nos ha proporcionado.
            if (userService.activarUsuario(usuario.getCorreo(), codConfirmacion)) {
                Notification.show("Correo confirmado exitosamente, redirigiendo a Home", 3000, Notification.Position.TOP_CENTER);
                UI.getCurrent().navigate("homeUser");
            } else {
                Notification.show("El código de confirmación es inválido o ha expirado.", 3000, Notification.Position.TOP_CENTER);
            }
        });

        reenviarCodigo.addClickListener(event -> {
            correoService.sendRegistrationCorreo(usuario);
            Notification.show("Código de confirmación reenviado, revisa tu correo por favor.", 3000, Notification.Position.TOP_CENTER);
        });

        // Añadimos los componentes.
        add(codigo, button, reenviarCodigo);
    }
}

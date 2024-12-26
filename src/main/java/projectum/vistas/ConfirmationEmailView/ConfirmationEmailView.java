package projectum.vistas.ConfirmationEmailView;

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

import java.awt.*;

@Route("confirmar-correo")
@PageTitle("Confirmar correo")
@PermitAll
public class ConfirmationEmailView extends VerticalLayout {

    // Servicios del Usuario.
    private UsuarioService usuarioService;
    private final AuthenticatedUser authenticatedUser;
    private Usuario usuario;

    // Servicios para el correo.
    private CorreoService correoService;

    public ConfirmationEmailView(UsuarioService usuarioService, AuthenticatedUser authenticatedUser, CorreoService correoService) {
        this.usuarioService = usuarioService;
        this.authenticatedUser = authenticatedUser;
        this.correoService = correoService;

        // Buscamos al usuario que se ha autenticado para confirmar su email.
        authenticatedUser.get().ifPresent(user ->
                this.usuario = usuarioService.loadUserByCorreo(user.getCorreo()).orElse(null)
        );

        // Si no está autenticado, lo mandamos al login para que lo haga.
        if (usuario == null) {
            UI.getCurrent().navigate("login");
            return;
        }

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
            if (usuarioService.activarUsuario(usuario.getCorreo(), codConfirmacion)) {
                Notification.show("Correo confirmado exitosamente, redirigiendo a Home", 3000, Notification.Position.TOP_CENTER);
                UI.getCurrent().navigate("proyectos");
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

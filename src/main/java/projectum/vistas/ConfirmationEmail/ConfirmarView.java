package projectum.vistas.ConfirmationEmail;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Route("confirmar-correo/confirmar")
@PageTitle("Confirmar Correo")
@AnonymousAllowed
public class ConfirmarView extends VerticalLayout {

    private final UsuarioService UsuarioService;

    public ConfirmarView(UsuarioService usuarioService) {

        this.UsuarioService = usuarioService;

        String correoCodificado = VaadinRequest.getCurrent().getParameter("correo");

        String token = new String(Base64.getUrlDecoder().decode(correoCodificado), StandardCharsets.UTF_8);

        Optional<Usuario> maybeUser = UsuarioService.loadUserByCorreo(token);

        UsuarioService.activarUsuario(token, maybeUser.get().getCodigoRegistro());
        add(new H1("Correo Confirmado, ¡Bienvenido a Projectum!"));

        Button loginButton = new Button("Inicia sesión ahora");
        loginButton.addClickListener(e -> UI.getCurrent().navigate("login"));
        loginButton.getStyle()
                .set("font-size", "1.5vw")
                .set("padding", "1vw 2vw")
                .set("border", "none")
                .set("border-radius", "10px")
                .set("background-color", "#007BFF")
                .set("color", "white")
                .set("cursor", "pointer");

        loginButton.getElement().addEventListener("mouseover", e -> {
            loginButton.getElement().getStyle().set("background-color", "#0056b3");
        });
        loginButton.getElement().addEventListener("mouseout", e -> {
            loginButton.getElement().getStyle().set("background-color", "#007BFF");
        });

        add(loginButton);
    }
}



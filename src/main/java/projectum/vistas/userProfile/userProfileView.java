package projectum.vistas.userProfile;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import projectum.data.entidades.Usuario;
import projectum.security.login.AuthenticatedUser;
import projectum.data.servicios.UsuarioService;
import projectum.vistas.MainLayout;


@PageTitle("Perfil")
@Route(value = "perfil", layout = MainLayout.class)
@PermitAll
public class userProfileView extends VerticalLayout {
    private final UsuarioService usuarioService;
    private final AuthenticatedUser authenticatedUser;
    private final PasswordEncoder passwordEncoder;
    private Usuario usuario;

    public userProfileView(UsuarioService usuarioService, AuthenticatedUser authenticatedUser, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.authenticatedUser = authenticatedUser;
        this.passwordEncoder = passwordEncoder;

        // Estilo principal de la vista
        setWidth("50%");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Obtener usuario autenticado
        authenticatedUser.get().ifPresent(user -> this.usuario = user);

        if (usuario == null) {
            UI.getCurrent().navigate("login");
            return;
        }

        // Título de la vista
        H1 titulo = new H1("Perfil de Usuario");
        titulo.getStyle().set("color", "#1E88E5");

        // Formulario
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1), // Móvil
                new FormLayout.ResponsiveStep("500px", 2) // Escritorio
        );

        TextField nombreUsuario = new TextField("Nombre de usuario");
        nombreUsuario.setValue(usuario.getUsername());
        nombreUsuario.setRequired(true);

        TextField nombre = new TextField("Nombre completo");
        nombre.setValue(usuario.getNombre());
        nombre.setRequired(true);

        PasswordField contrasennaVieja = new PasswordField("Contraseña antigua");
        PasswordField contrasenna = new PasswordField("Nueva contraseña");
        PasswordField confirmarContrasenna = new PasswordField("Confirmar contraseña");

        formLayout.add(
                nombreUsuario, nombre,
                contrasennaVieja, contrasenna,
                confirmarContrasenna
        );

        // Botones
        Button guardarCambios = new Button("Guardar cambios", event -> {
            if (!nombreUsuario.getValue().isEmpty() && !nombre.getValue().isEmpty()) {
                if (!contrasennaVieja.getValue().isEmpty()) {
                    if (passwordEncoder.matches(contrasennaVieja.getValue(), usuario.getHashedPassword())) {
                        usuario.setHashedPassword(passwordEncoder.encode(contrasenna.getValue()));
                    } else {
                        showErrorNotification("Contraseña antigua incorrecta.");
                        return;
                    }
                }
                usuario.setUsername(nombreUsuario.getValue());
                usuario.setNombre(nombre.getValue());
                usuarioService.save(usuario);
                showSuccessNotification("Cambios guardados satisfactoriamente.");
            } else {
                showErrorNotification("El nombre de usuario y el nombre completo son obligatorios.");
            }
        });

        Button borrarUsuario = new Button("Eliminar cuenta", event -> {
            usuarioService.delete(usuario.getId());
            authenticatedUser.logout();
            UI.getCurrent().navigate("login");
            showSuccessNotification("Cuenta eliminada con éxito.");
        });
        borrarUsuario.getStyle().set("color", "red");

        // Estilo de los botones
        HorizontalLayout buttonLayout = new HorizontalLayout(guardarCambios, borrarUsuario);
        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
        buttonLayout.setWidthFull();

        add(titulo, formLayout, buttonLayout);
    }

    // Métodos de notificación
    private void showSuccessNotification(String message) {
        Notification notification = new Notification(message, 3000, Notification.Position.TOP_END);
        notification.getElement().getThemeList().add("success");
        notification.open();
    }

    private void showErrorNotification(String message) {
        Notification notification = new Notification(message, 3000, Notification.Position.TOP_END);
        notification.getElement().getThemeList().add("error");
        notification.open();
    }
}


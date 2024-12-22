package projectum.vistas.userProfile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import projectum.data.entidades.Usuario;
import projectum.security.login.AuthenticatedUser;
import projectum.data.servicios.UsuarioService;
import projectum.vistas.MainLayout;

@PageTitle("Perfil")
@Route(value = "perfil", layout = MainLayout.class)
@PermitAll // Solo para usuarios autenticados.
public class userProfileView extends VerticalLayout {
    private final UsuarioService usuarioService;
    private final AuthenticatedUser authenticatedUser;
    private Usuario usuario;

    public userProfileView(UsuarioService usuarioService, AuthenticatedUser authenticatedUser) {
        this.usuarioService = usuarioService;
        this.authenticatedUser = authenticatedUser;

        // Cargamos al usuario que está autenticado ahora mismo.
        authenticatedUser.get().ifPresent(user ->
                this.usuario = usuarioService.loadUserById(user.getId()).orElse(null)
        );

        // Si el usuario no está autenticado, lo redirigimos al login.
        if (usuario == null) {
            UI.getCurrent().navigate("login");
            return;
        }

        H1 labelProyecto = new H1("Bienvenido/a " + usuario.getUsername());
        labelProyecto.getStyle().set("font-size", "30px").set("font-weight", "bold");
        labelProyecto.getStyle().set("color", "blue");

        //nombre usuario
        H2 username = new H2("Nombre de usuario: " + usuario.getUsername());
        username.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField usernameField = new TextField();
        usernameField.setPlaceholder("Cambiar nombre de usuario");
        usernameField.setWidth("300px");

        Button usernameButton = new Button("Guardar");
        usernameButton.addClickListener(e -> {
            String nuevousername = usernameField.getValue();
            if (nuevousername.isEmpty()) {
                Notification.show("Por favor, introduce un nombre de usuario", 3000, Notification.Position.MIDDLE);
            } else {
                usuario.setUsername(nuevousername);
                usuarioService.updateUsuario(usuario);
                Notification.show("Nombre de usuario actualizado, para ver los cambios vuelva a iniciar sesión", 3000, Notification.Position.MIDDLE);
            }
        });

        // una fila
        HorizontalLayout usernameLayout = new HorizontalLayout(username, usernameField, usernameButton);
        usernameLayout.setAlignItems(Alignment.CENTER); // Alinear al centro
        usernameLayout.setSpacing(true); // Espaciado entre elementos
        usernameLayout.getStyle().set("margin-bottom", "20px");

        // nombre
        H2 nombre = new H2("Nombre: " + usuario.getNombre());
        nombre.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField nameField = new TextField();
        nameField.setPlaceholder("Cambiar nombre");
        nameField.setWidth("300px");

        Button nombreButton = new Button("Guardar");
        nombreButton.addClickListener(e -> {
            String nuevoNombre = nameField.getValue();
            if (nuevoNombre.isEmpty()) {
                Notification.show("Por favor, introduce un nombre", 3000, Notification.Position.MIDDLE);
            } else {
                usuario.setNombre(nuevoNombre);
                usuarioService.updateUsuario(usuario);
                Notification.show("Nombre actualizado, para ver los cambios vuelva a iniciar sesión", 3000, Notification.Position.MIDDLE);
            }
        });

        // una fila
        HorizontalLayout nombreLayout = new HorizontalLayout(nombre, nameField, nombreButton);
        nombreLayout.setAlignItems(Alignment.CENTER); // Alinear al centro
        nombreLayout.setSpacing(true); // Espaciado entre elementos
        nombreLayout.getStyle().set("margin-bottom", "20px");

        //contraseña
        PasswordField passwordField = new PasswordField("Cambiar contraseña");
        passwordField.setWidth("300px");

        PasswordField password2Field = new PasswordField("Confirmar contraseña");
        password2Field.setWidth("300px");

        Button passwordButton = new Button("Guardar");
        passwordButton.getStyle().set("margin-top", "30px");
        passwordButton.addClickListener(e -> {
            String nuevaPassword = passwordField.getValue();
            String confirmacionPassword = password2Field.getValue();
            if (nuevaPassword.isEmpty() || confirmacionPassword.isEmpty()) {
                Notification.show("Por favor, introduce la nueva contraseña", 3000, Notification.Position.MIDDLE);
            } else {
                usuario.setPassword(nuevaPassword);
                usuarioService.updateUsuario(usuario);
                Notification.show("Contraseña actualizada", 3000, Notification.Position.MIDDLE);
            }
        });

        HorizontalLayout passwordlLayout = new HorizontalLayout(passwordField, password2Field, passwordButton);
        passwordlLayout.setAlignItems(Alignment.CENTER); // Alinear al centro
        passwordlLayout.setSpacing(true); // Espaciado entre elementos
        passwordlLayout.getStyle().set("margin-bottom", "20px");

        // Añadir los elementos al diseño principal
        add(labelProyecto, usernameLayout, nombreLayout, passwordlLayout);
    }
}

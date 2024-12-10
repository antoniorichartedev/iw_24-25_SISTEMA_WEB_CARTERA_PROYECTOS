package projectum.vistas.signIn;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.UI;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;

@PageTitle("Registrarse")
@Route("sign-in")
@AnonymousAllowed
public class SignInView extends VerticalLayout {

    // Necesario para poder registrar al usuario.
    private final UsuarioService usuarioService;

    public SignInView(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;

        H1 title = new H1("Registrarse");
        title.getStyle().set("color", "blue");

        H2 subtitle = new H2("Crea una cuenta para acceder a la plataforma");
        subtitle.getStyle().set("color", "darkorange");

        TextField nameField = new TextField("Nombre");
        nameField.setWidth("70%");
        EmailField emailField = new EmailField("Correo electrónico");
        emailField.setWidth("70%");
        PasswordField passwordField = new PasswordField("Contraseña");
        passwordField.setWidth("70%");
        PasswordField confirmPasswordField = new PasswordField("Confirmar contraseña");
        confirmPasswordField.setWidth("70%");

        Button registerButton = new Button("Registrarse");
        registerButton.addClickListener(e -> {
            // Aquí iría la lógica para manejar el registro
            // Ejemplo: Validar campos y enviar los datos
            String name = nameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Notification.show("Por favor, completa todos los campos", 3000, Notification.Position.MIDDLE);
            } else if (!password.equals(confirmPassword)) {
                Notification.show("Las contraseñas no coinciden", 3000, Notification.Position.MIDDLE);
            } else {
                // Creamos un nuevo usuario.
                Usuario newUsuario = new Usuario();

                // Le asignanos los parámetros correspondientes.
                newUsuario.setNombre(name);
                newUsuario.setCorreo(email);
                newUsuario.setPassword(password);

                // Llamamos al servicio para poder registrar al usuario.
                boolean registrado = usuarioService.RegistrarUsuario(newUsuario);

                // Si es true, quiere decir que hemos registrado al usuario correctamente.
                if(registrado) {
                    Notification.show("Registro exitoso", 3000, Notification.Position.MIDDLE);
                    UI.getCurrent().navigate("login");
                }else {
                    Notification.show("El usuario ya se encuentra registrado", 3000, Notification.Position.MIDDLE);
                }

            }
        });

        registerButton.getElement().addEventListener("mouseover", e -> {
            registerButton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });

        registerButton.getElement().addEventListener("mouseout", e -> {
            registerButton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        registerButton.getStyle()
                .set("font-size", "20px")
                .set("padding", "15px 30px")
                .set("font-weight", "bold");

        // Centramos el formulario y su contenido
        setAlignItems(Alignment.CENTER); // Centrar elementos verticalmente
        setJustifyContentMode(JustifyContentMode.CENTER); // Centrar contenido en el eje vertical
        setSizeFull(); // Asegura que el layout ocupe toda la pantalla

        // Añadir los componentes a la vista
        add(title, subtitle, nameField, emailField, passwordField, confirmPasswordField, registerButton);

    }

}
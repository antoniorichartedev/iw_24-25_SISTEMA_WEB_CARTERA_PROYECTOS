package projectum.vistas.signIn;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;

@PageTitle("Registrarse")
@Route("sign-in")
@AnonymousAllowed
public class SignInView extends VerticalLayout {

    // Necesario para poder registrar al usuario.
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public SignInView(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {

        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;

        H1 title = new H1("Registrarse");
        title.getStyle().set("color", "blue");

        H2 subtitle = new H2("Crea una cuenta para acceder a la plataforma");
        subtitle.getStyle().set("color", "darkorange");

        TextField nameField = new TextField("Nombre");
        nameField.setWidth("70%");
        TextField usernameField = new TextField("Nombre de usuario");
        usernameField.setWidth("70%");
        EmailField emailField = new EmailField("Correo electrónico");
        emailField.setWidth("70%");
        PasswordField passwordField = new PasswordField("Contraseña");
        passwordField.setWidth("70%");
        PasswordField confirmPasswordField = new PasswordField("Confirmar contraseña");
        confirmPasswordField.setWidth("70%");

        // Label para mostrar la fortaleza de la contraseña
        Span passwordStrengthLabel = new Span("Fortaleza de la contraseña: ");
        passwordStrengthLabel.getStyle()
                .set("font-size", "14px")
                .set("color", "#555");

        // Listener para evaluar la fortaleza de la contraseña
        passwordField.addValueChangeListener(e -> {
            String password = e.getValue();
            String strength = fortalezaContrasenna(password);
            passwordStrengthLabel.setText("Fortaleza de la contraseña: " + strength);
            switch (strength) {
                case "Débil":
                    passwordStrengthLabel.getStyle().set("color", "red");
                    break;
                case "Moderada":
                    passwordStrengthLabel.getStyle().set("color", "orange");
                    break;
                case "Fuerte":
                    passwordStrengthLabel.getStyle().set("color", "green");
                    break;
            }
        });


        Button registerButton = new Button("Registrarse");
        registerButton.addClickListener(e -> {
            // Aquí iría la lógica para manejar el registro
            // Ejemplo: Validar campos y enviar los datos
            String name = nameField.getValue();
            String username = usernameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Notification.show("Por favor, completa todos los campos", 3000, Notification.Position.MIDDLE);
            } else if (!password.equals(confirmPassword)) {
                Notification.show("Las contraseñas no coinciden", 3000, Notification.Position.MIDDLE);
            } else {
                // Creamos un nuevo usuario.
                Usuario newUsuario = new Usuario();

                // Le asignanos los parámetros correspondientes.
                newUsuario.setNombre(name);
                newUsuario.setUsername(username);
                newUsuario.setCorreo(email);
                newUsuario.setPassword(password);

                // Llamamos al servicio para poder registrar al usuario.
                boolean registrado = usuarioService.RegistrarUsuario(newUsuario);

                // Si es true, quiere decir que hemos registrado al usuario correctamente.
                if(registrado) {
                    Notification.show("Registro exitoso. Por favor, ve a tu correo y verifica la cuenta", 3000, Notification.Position.MIDDLE);
                    UI.getCurrent().navigate("confirmar-correo");
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
        add(title, subtitle, nameField, usernameField, emailField, passwordField, passwordStrengthLabel, confirmPasswordField, registerButton);

    }

    // Método que nos permitirá saber como de fuerte es la contraseña que ha introducido el usuario en tiempo real.
    private String fortalezaContrasenna(String password) {
        int puntuacion = 0;

        if (password.length() >= 8) puntuacion++;
        if (password.matches(".*[A-Z].*")) puntuacion++;
        if (password.matches(".*[a-z].*")) puntuacion++;
        if (password.matches(".*\\d.*")) puntuacion++;
        if (password.matches(".*[!@#$%^&*()-+].*")) puntuacion++;

        switch (puntuacion) {
            case 0:
            case 1:
            case 2:
                return "Débil";
            case 3:
            case 4:
                return "Moderada";
            case 5:
                return "Fuerte";
            default:
                return "Débil";
        }
    }
}
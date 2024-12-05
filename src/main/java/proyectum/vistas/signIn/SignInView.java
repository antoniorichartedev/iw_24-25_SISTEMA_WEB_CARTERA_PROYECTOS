package proyectum.vistas.SignInView;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.button.Button;
@PageTitle("Registrarse")
@Route("sign-in")
@AnonymousAllowed
public class SignInView extends VerticalLayout {
    public SignInView(){
        H1 title = new H1("Registrarse");
        title.getStyle().set("color", "blue");

        H2 subtitle = new H2("Crea una cuenta para acceder a la plataforma");
        subtitle.getStyle().set("color", "darkorange");

        TextField nameField = new TextField("Nombre");
        EmailField emailField = new EmailField("Correo electrónico");
        PasswordField passwordField = new PasswordField("Contraseña");
        PasswordField confirmPasswordField = new PasswordField("Confirmar contraseña");

        Button registerButton = new Button("Registrarse");
        registerButton.addClickListener(e -> {
            // Aquí iría la lógica para manejar el registro
            // Ejemplo: Validar campos y enviar los datos
            String name = nameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (password.equals(confirmPassword)) {
                // Aquí iría el código para registrar al usuario
                // Si la validación es exitosa, se podría redirigir a otra página
            } else {
                // Mostrar error si las contraseñas no coinciden
            }
        });

        // Diseño y disposición del formulario
        FormLayout formLayout = new FormLayout(nameField, emailField, passwordField, confirmPasswordField, registerButton);
        formLayout.setMaxWidth("400px"); // Limitar el ancho del formulario
        formLayout.setMaxWidth("400px"); // Limitar el ancho del formulario

        // Centramos el formulario y su contenido
        setAlignItems(Alignment.CENTER); // Centrar elementos verticalmente
        setJustifyContentMode(JustifyContentMode.CENTER); // Centrar contenido en el eje vertical
        setSizeFull(); // Asegura que el layout ocupe toda la pantalla

        formLayout.setWidth("100%"); // Asegura que el formulario ocupe todo el ancho disponible, pero sin exceder el contenedor

        // Añadir los componentes a la vista
        add(title, subtitle, formLayout);

    }

}
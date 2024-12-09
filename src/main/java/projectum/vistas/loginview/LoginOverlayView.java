package projectum.vistas.loginview;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Iniciar Sesión")
@Route("loginOpcional")
public class LoginOverlayView extends Composite<LoginOverlay> {

    public LoginOverlayView() {
        // Crear el LoginOverlay
        LoginOverlay loginOverlay = getContent(); // getContent() devuelve el componente principal

        // Crear un LoginForm y configurarlo
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> {
            // Aquí puedes manejar el evento de inicio de sesión.
            String username = e.getUsername();
            String password = e.getPassword();

            // Validamos las credenciales con Spring Security.
            loginForm.setAction("login");

        });

        // Configurar el LoginOverlay
        loginOverlay.setTitle("Bienvenido a Projectum");
        loginOverlay.setDescription("Inicia sesión con tus credenciales");

        // Mostrar el LoginOverlay al cargar la vista
        loginOverlay.setOpened(true);

        // Evitar que se enfoque automáticamente
        loginOverlay.getElement().setAttribute("no-autofocus", "");
    }
}

package projectum.vistas.loginview;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Iniciar Sesión")
@AnonymousAllowed

public class LogInView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();
    TextField casillaCorreo = new TextField();
    PasswordField casillaContrasenna = new PasswordField();
    Button loginButton = new Button("continuar");
    Button BacktoRegisterButton = new Button("Registrarse");

    public LogInView(){

        // Correo.
        casillaCorreo.setRequiredIndicatorVisible(true);
        casillaCorreo.setMinWidth("525px");
        casillaCorreo.setMinLength(20);
        casillaCorreo.setMaxLength(70);
        casillaCorreo.setLabel("Correo");

        // Contraseña.
        casillaContrasenna.setRequiredIndicatorVisible(true);
        casillaContrasenna.setMinWidth("525px");
        casillaContrasenna.setMinLength(8);
        casillaContrasenna.setMaxLength(50);
        casillaContrasenna.setLabel("Contraseña");

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        login.addForgotPasswordListener(event -> {
            UI.getCurrent().navigate("reset-password");
        });

        Button registerButton = new Button("Registrarse", event -> {
            UI.getCurrent().navigate("sign-in");
        });

        add(login, registerButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the userProfile about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);

        }
    }
}
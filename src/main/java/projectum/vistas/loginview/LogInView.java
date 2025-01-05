package projectum.vistas.loginview;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import projectum.data.entidades.Usuario;
import projectum.security.login.AuthenticatedUser;
import com.vaadin.flow.component.login.LoginI18n;

import java.util.Optional;
import java.util.logging.Logger;


@Route("login")
@PageTitle("Iniciar Sesión")
@AnonymousAllowed
public class LogInView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;
    public static final String HOME_ADMIN = "homeAdmin";
    public static final String HOME_OT = "homeOT";
    public static final String HOME_CIO = "homeCio";
    public static final String HOME_USER = "homeUser";

    public static final String HOME_PROMOTOR = "homePromotor";
    public static final String LOGIN = "login";

    public LogInView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        // Ahora, vamos a personalizar los textos que queremos que salga en el login, para que quede más bonito.
        LoginI18n loginCustom = LoginI18n.createDefault();

        // Cabecera.
        loginCustom.setHeader(new LoginI18n.Header());
        loginCustom.getHeader().setTitle("Projectum");
        loginCustom.setAdditionalInformation(null);

        // Cuerpo del login.
        loginCustom.setForm(new LoginI18n.Form());
        loginCustom.getForm().setTitle("Inicia sesión en Projectum");
        loginCustom.getForm().setUsername("Usuario");
        loginCustom.getForm().setPassword("Contraseña");
        loginCustom.getForm().setSubmit("Iniciar sesión");
        loginCustom.getForm().setForgotPassword("¿Olvidaste tu contraseña?");
        setI18n(loginCustom);

        // Hacemos visible el botón de "¿olvidaste la contraseña?"
        setForgotPasswordButtonVisible(true);
        setOpened(true);

        addForgotPasswordListener(event -> {
            UI.getCurrent().navigate("recuperar-contrasena");
        });

        // Acción que hará la aplicación una vez que el usuario envie el formulario de iniciar sesión.
        setAction(RouteConfiguration.forApplicationScope().getUrl(getClass()));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Usuario> opUsuario = authenticatedUser.get();

        if (opUsuario.isPresent() && opUsuario.get().getRol() != null) {
            String targetPage = determineTargetPage(opUsuario.get());
            event.forwardTo(targetPage);
        } else {
            event.forwardTo(LOGIN);
            setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
        }
    }

    private String determineTargetPage(Usuario usuario) {
        String jumpPage;
        switch (usuario.getRol()) {
            case CIO -> jumpPage = HOME_CIO;
            case ADMIN -> jumpPage = HOME_ADMIN;
            case OT -> jumpPage = HOME_OT;
            case USER -> jumpPage = HOME_USER;
            case PROMOTOR -> jumpPage = HOME_PROMOTOR;
            default -> {
                // Loggear roles inesperados para diagnóstico
                Logger.getLogger(getClass().getName())
                        .warning("Rol inesperado: " + usuario.getRol());
                jumpPage = HOME_USER;
            }
        };
        return jumpPage;
    }
}
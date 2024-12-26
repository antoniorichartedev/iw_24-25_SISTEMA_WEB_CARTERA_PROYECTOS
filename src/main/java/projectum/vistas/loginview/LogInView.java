package projectum.vistas.loginview;

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
        setI18n(loginCustom);

        // Hacemos visible el botón de "¿olvidaste la contraseña?"
        setForgotPasswordButtonVisible(true);
        setOpened(true);

        // Acción que hará la aplicación una vez que el usuario envie el formulario de iniciar sesión.
        setAction(RouteConfiguration.forApplicationScope().getUrl(getClass()));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Obtenemos el usuario autenticado, si es que lo hay.
        Optional<Usuario> opUsuario = authenticatedUser.get();

        if (opUsuario.isPresent()) {
            Usuario usuario = opUsuario.get();
            String targetPage = determinarPaginaRedireccion(usuario);
            event.forwardTo(targetPage);
        } else {
            // Manejar el caso en el que no hay un usuario autenticado.
            event.forwardTo("login");
        }
    }

    /**
     * Determina la página de redirección en función del rol del usuario.
     *
     * @param usuario El usuario autenticado.
     * @return La página a la que debe redirigirse el usuario.
     */
    private String determinarPaginaRedireccion(Usuario usuario) {
        return switch (usuario.getRol()) {
            case ADMIN -> "adminUsers";
            case OT -> "homeOT";
            case CIO -> "homeCio";
            case USER -> "proyectos";
            default -> {
                // Loggear roles inesperados para diagnóstico
                Logger.getLogger(getClass().getName())
                        .warning("Rol inesperado: " + usuario.getRol());
                yield "proyectos";
            }
        };
    }


}
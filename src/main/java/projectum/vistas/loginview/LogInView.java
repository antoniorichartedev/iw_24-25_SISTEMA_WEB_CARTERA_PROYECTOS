package projectum.vistas.loginview;

import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.route.RouteUtil;
import org.springframework.context.annotation.Role;
import projectum.data.Rol;
import projectum.data.entidades.Usuario;
import projectum.security.login.AuthenticatedUser;
import com.vaadin.flow.component.login.LoginI18n;

import java.util.Optional;
import java.util.Set;

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
        Optional<Usuario> OpUsuario = authenticatedUser.get();

        // Si hay un usuario autenticado, pues lo mandamos a según qué página dependiendo del rol que tenga.
        if(OpUsuario.isPresent()) {
            Usuario usuario = OpUsuario.get();

            if(usuario.getRol() == Rol.CIO) {
                event.forwardTo("proyectos");
            }
            else if (usuario.getRol() == Rol.ADMIN) {
                event.forwardTo("proyectos");
            }
            else if (usuario.getRol() == Rol.USER) {
                event.forwardTo("proyectos");
            }
            else if (usuario.getRol() == Rol.SOLICITANTE) {
                event.forwardTo("proyectos");
            }
            else if (usuario.getRol() == Rol.OT) {
                event.forwardTo("proyectos");
            }
        }
        else {
            event.forwardTo("/login?error=true");
        }
    }

}
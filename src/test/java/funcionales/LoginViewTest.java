package funcionales;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.vaadin.flow.router.QueryParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.vaadin.flow.router.BeforeEnterEvent;
import projectum.data.entidades.Usuario;
import projectum.data.Rol;
import projectum.security.login.AuthenticatedUser;
import projectum.vistas.loginview.LogInView;


import java.util.Optional;

public class LoginViewTest {

    @Mock
    private AuthenticatedUser authenticatedUser;

    @InjectMocks
    private LogInView logInView;

    @Mock
    private BeforeEnterEvent beforeEnterEvent;

    @Mock
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logInView = new LogInView(authenticatedUser);
    }

    @Test
    public void testBeforeEnter_RedirectionBasedOnRole_CIO() {
        // Simulamos un usuario con rol CIO
        when(authenticatedUser.get()).thenReturn(Optional.of(usuario));
        when(usuario.getRol()).thenReturn(Rol.CIO);

        // Simulamos que el evento de entrada quiere redirigir a una página según el rol
        logInView.beforeEnter(beforeEnterEvent);

        // Verificamos que se redirige a la página HOME_CIO
        verify(beforeEnterEvent).forwardTo(LogInView.HOME_CIO);
    }

    @Test
    public void testBeforeEnter_RedirectionBasedOnRole_ADMIN() {
        // Simulamos un usuario con rol ADMIN
        when(authenticatedUser.get()).thenReturn(Optional.of(usuario));
        when(usuario.getRol()).thenReturn(Rol.ADMIN);

        // Simulamos que el evento de entrada quiere redirigir a una página según el rol
        logInView.beforeEnter(beforeEnterEvent);

        // Verificamos que se redirige a la página HOME_ADMIN
        verify(beforeEnterEvent).forwardTo(LogInView.HOME_ADMIN);
    }

    @Test
    public void testBeforeEnter_RedirectionBasedOnRole_OT() {
        // Simulamos un usuario con rol ADMIN
        when(authenticatedUser.get()).thenReturn(Optional.of(usuario));
        when(usuario.getRol()).thenReturn(Rol.CIO);

        // Simulamos que el evento de entrada quiere redirigir a una página según el rol
        logInView.beforeEnter(beforeEnterEvent);

        // Verificamos que se redirige a la página HOME_ADMIN
        verify(beforeEnterEvent).forwardTo(LogInView.HOME_OT);
    }

    @Test
    public void testBeforeEnter_RedirectionBasedOnRole_USER() {
        // Simulamos un usuario con rol USER
        when(authenticatedUser.get()).thenReturn(Optional.of(usuario));
        when(usuario.getRol()).thenReturn(Rol.USER);

        // Simulamos que el evento de entrada quiere redirigir a una página según el rol
        logInView.beforeEnter(beforeEnterEvent);

        // Verificamos que se redirige a la página HOME_USER
        verify(beforeEnterEvent).forwardTo(LogInView.HOME_USER);
    }

    @Test
    public void testBeforeEnter_RedirectionBasedOnRole_PROMOTOR() {
        // Simulamos un usuario con rol ADMIN
        when(authenticatedUser.get()).thenReturn(Optional.of(usuario));
        when(usuario.getRol()).thenReturn(Rol.PROMOTOR);

        // Simulamos que el evento de entrada quiere redirigir a una página según el rol
        logInView.beforeEnter(beforeEnterEvent);

        // Verificamos que se redirige a la página HOME_ADMIN
        verify(beforeEnterEvent).forwardTo(LogInView.HOME_PROMOTOR);
    }

    @Test
    public void testBeforeEnter_NoUserAuthenticated() {
        // Simulamos que no hay un usuario autenticado
        when(authenticatedUser.get()).thenReturn(Optional.empty());

        // Simulamos el evento de entrada
        logInView.beforeEnter(beforeEnterEvent);

        // Verificamos que se redirige al login
        verify(beforeEnterEvent).forwardTo(LogInView.LOGIN);
    }

    @Test
    public void testBeforeEnter_RedirectionOnError() {
        // Simulamos un error en la autenticación (usuario con error)
        when(authenticatedUser.get()).thenReturn(Optional.of(usuario));
        when(usuario.getRol()).thenReturn(Rol.USER);

        // Simulamos el parámetro de error en la URL
        when(beforeEnterEvent.getLocation()).thenReturn(new com.vaadin.flow.router.Location("", QueryParameters.fromString("error=true")));

        // Llamamos al método
        logInView.beforeEnter(beforeEnterEvent);

        // Verificamos que se ha mostrado un error en la vista de login
        assertTrue(logInView.isError());
    }
}

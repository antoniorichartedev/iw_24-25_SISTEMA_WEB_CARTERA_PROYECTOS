package projectum.security.login;

import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;

import java.util.stream.Stream;

public final class SecurityUtils implements UserDetailsService {

    private final UsuarioService usuarioService;

    public SecurityUtils(UsuarioService userService){
        this.usuarioService = userService;
    }

    public static boolean isFrameworkInternalRequest(jakarta.servlet.http.HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario applicationUser = usuarioService.loadUserByUsername(username);

        return User.withUsername(applicationUser.getUsername())
                .password(applicationUser.getPassword())
                .roles(applicationUser.getRol().toString())
                .build();
    }

}

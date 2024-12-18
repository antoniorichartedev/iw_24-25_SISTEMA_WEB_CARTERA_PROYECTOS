package projectum.security.RolRestrictions;

import projectum.data.Rol;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioService userService;

    public UserDetailsServiceImpl(UsuarioService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userService.loadUserByNombre(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getHashedPassword(),
                    getAuthorities(user));
        }
    }

    private static List<GrantedAuthority> getAuthorities(Usuario user) {
        Rol rol = user.getRol();
        if (rol == null) {
            throw new IllegalArgumentException("El usuario no tiene rol asignado");
        }

        // Creamos una lista con un solo GrantedAuthority
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

}


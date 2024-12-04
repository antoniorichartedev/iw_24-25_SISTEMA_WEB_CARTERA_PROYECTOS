package com.example.proyecto.security.login;

import com.example.proyecto.spring.Persona.Persona;

import com.example.proyecto.spring.Persona.*;
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthenticatedUser {

    private final PersonaRepository userRepository;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext, PersonaRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    @Transactional
    public Optional<Persona> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userRepository.findByNombre(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }

}



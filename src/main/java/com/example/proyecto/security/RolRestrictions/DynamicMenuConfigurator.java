package com.example.proyecto.security.RolRestrictions;

import com.example.proyecto.spring.Rol;
import com.example.proyecto.spring.Usuario.Usuario;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

public class DynamicMenuConfigurator {

    public static boolean isUserAuthorizedForMenu(Class<?> viewClass, Rol requiredRole) {
        Usuario currentUser = VaadinSession.getCurrent().getAttribute(Usuario.class);
        if (currentUser == null || currentUser.getRol() != requiredRole) {
            return false;
        }

        // Validar si la clase tiene una anotación @Route
        if (viewClass.isAnnotationPresent(Route.class)) {
            Route route = viewClass.getAnnotation(Route.class);
            return route != null;
        }
        return false;
    }
}

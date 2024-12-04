package com.example.proyecto.vaadin.loginview;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Log In")
@Route("login")
public class LogInView extends Composite<VerticalLayout> {

    private LoginForm loginForm = new LoginForm();
    public LogInView() {

        // Establecer la acción del formulario de login para que apunte a Spring Security
        loginForm.setAction("login");  // Importante: Esta acción debe coincidir con la configuración de Spring Security. Sino NO FUNCIONA.

        // Configurar el contenedor del login
        VerticalLayout wrapper = new VerticalLayout(loginForm);
        wrapper.setWidthFull();
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);

        // Configurar el layout principal
        VerticalLayout layout = getContent();
        layout.setSizeFull();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(wrapper);
    }

    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}

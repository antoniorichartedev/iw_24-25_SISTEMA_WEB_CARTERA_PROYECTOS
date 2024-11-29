package com.example.proyecto.vaadin.loginview;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    public LogInView() {
        LoginForm loginForm = new LoginForm();

        // Contenedor envolvente para ajustar el tamaño
        VerticalLayout wrapper = new VerticalLayout(loginForm);
        wrapper.setWidthFull(); // Configura el ancho del contenedor
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER); // Centra el formulario horizontalmente

        // Configuración del contenido principal
        VerticalLayout layout = getContent();
        layout.setSizeFull(); // Asegura que ocupe toda la pantalla
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(wrapper);
    }
}

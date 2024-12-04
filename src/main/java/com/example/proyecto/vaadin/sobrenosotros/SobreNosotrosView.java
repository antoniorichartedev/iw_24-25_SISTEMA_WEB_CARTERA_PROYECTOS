package com.example.proyecto.vaadin.sobrenosotros;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;


@PageTitle("Sobre nosotros")
@Route("about-us")
@Menu(order = 10, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
@AnonymousAllowed
public class SobreNosotrosView extends Composite<VerticalLayout> {

    public SobreNosotrosView() {

        // Título principal de la página.
        H1 title = new H1("Projectum");
        title.addClassNames(LumoUtility.Margin.MEDIUM, LumoUtility.Padding.MEDIUM);

        // Descripción general de la aplicación web.
        Paragraph description = new Paragraph(
                "Donde puedes gestionar tus proyectos de manera más fácil, sencilla y rápida."
        );

        // Información de contacto.
        VerticalLayout contactInfoLayout = new VerticalLayout();
        contactInfoLayout.add(
                new H2("Contáctanos"),
                new Paragraph("Teléfono: +34 666 665 664"),
                new Paragraph("Correo: contacto@projectum.com"),
                new Paragraph("Dirección: Calle Ficticia 123, Ficticia City, FicticiaLand")
        );

        // Organización general de la página.
        getContent().add(title, description, contactInfoLayout);
        getContent().setAlignItems(Alignment.CENTER); // Centrar contenido
        getContent().setPadding(true);
        getContent().setSpacing(true);
    }
}

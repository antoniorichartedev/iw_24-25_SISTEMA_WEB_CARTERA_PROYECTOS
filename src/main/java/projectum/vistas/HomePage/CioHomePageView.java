package projectum.vistas.HomePage;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;

import org.vaadin.lineawesome.LineAwesomeIconUrl;
import projectum.data.Rol;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.vistas.formCIO.formCIOView;


@PageTitle("Home")
@Route("homeCio")
@Menu(order = 15, icon = LineAwesomeIconUrl.HOME_SOLID)
@RolesAllowed("CIO")
public class CioHomePageView extends VerticalLayout implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {return Rol.CIO;}

    public CioHomePageView() {
        H1 h1 = new H1();
        Paragraph textLarge = new Paragraph();

        h1.setText("Bienvenido de vuelta CIO");
        h1.setWidth("max-content");
        textLarge.setText("Aqui tiene todos los proyectos que no se han valorado todavia");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        Button button = new Button("Aceptar proyectos");
        button.addClickListener(e -> {
            UI.getCurrent().navigate("aceptarProyectos");
        });

        button.getElement().addEventListener("mouseover", e -> {
            button.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        button.getElement().addEventListener("mouseout", e -> {
            button.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        Button gestionarbutton = new Button("Gestionar proyectos");
        gestionarbutton.addClickListener(e -> {
            UI.getCurrent().navigate("gestionarProyectos");
        });

        gestionarbutton.getElement().addEventListener("mouseover", e -> {
            gestionarbutton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        gestionarbutton.getElement().addEventListener("mouseout", e -> {
            gestionarbutton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        Button valorarbutton = new Button("Valorar proyectos");
        valorarbutton.addClickListener(e -> {
            UI.getCurrent().navigate("valorarProyectoCIO");
        });

        valorarbutton.getElement().addEventListener("mouseover", e -> {
            valorarbutton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        valorarbutton.getElement().addEventListener("mouseout", e -> {
            valorarbutton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        HorizontalLayout botonesLayout = new HorizontalLayout(button, gestionarbutton, valorarbutton);
        botonesLayout.setSpacing(true);
        botonesLayout.setPadding(true);
        botonesLayout.setAlignItems(Alignment.CENTER);

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(h1, textLarge, botonesLayout);
    }
}
package projectum.vistas.HomePage;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;

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
        RouterLink routerLink = new RouterLink();


        h1.setText("Bienvenido de vuelta CIO");
        h1.setWidth("max-content");
        textLarge.setText("Aqui tiene todos los proyectos que no se han valorado todavia");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");


        routerLink.setText("Avalar proyectos");
        routerLink.setRoute(formCIOView.class);
        add(h1);
        add(textLarge);

        add(routerLink);
    }
}
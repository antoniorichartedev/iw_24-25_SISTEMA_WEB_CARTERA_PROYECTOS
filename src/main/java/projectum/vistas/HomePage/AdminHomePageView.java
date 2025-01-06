package projectum.vistas.HomePage;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import projectum.data.Rol;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.vistas.adminUsers.adminUsersView;
import projectum.vistas.formCIO.formCIOView;


@PageTitle("Home")
@Route("homeAdmin")
@Menu(order = 15, icon = LineAwesomeIconUrl.HOME_SOLID)
@RolesAllowed("ADMIN")
public class AdminHomePageView extends VerticalLayout implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {return Rol.ADMIN;}

    public AdminHomePageView() {
        H1 h1 = new H1();
        Paragraph textLarge = new Paragraph();

        h1.setText("Bienvenido Administrador");
        h1.setWidth("max-content");
        textLarge.setText("Seleccione que desea gestionar.");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        Button button = new Button("Administrar Usuarios");
        button.addClickListener(e -> {
            UI.getCurrent().navigate("adminUsers");
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

        //pendiente añadir boton gestionar proyectos

        add(h1, textLarge, button);

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}

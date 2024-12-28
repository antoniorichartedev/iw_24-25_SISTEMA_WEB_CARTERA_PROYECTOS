package projectum.vistas.HomePage;

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
    public Rol getRequiredRole() {return Rol.CIO;}

    public AdminHomePageView() {
        H1 h1 = new H1();
        Paragraph textLarge = new Paragraph();

        h1.setText("Bienvenido Administrador");
        h1.setWidth("max-content");
        textLarge.setText("Selecciomne que desea gestionar.");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");

        HorizontalLayout hl1 = new HorizontalLayout();
        RouterLink rl1 = new RouterLink("Gestionar Usuarios", adminUsersView.class);
        //RouterLink rl2 = new RouterLink("Gestionar Proyectos", adminProjectView.class);

        hl1.add(rl1);
        //hl1.add(rl2);

        add(h1);
        add(textLarge);

        add(hl1);
    }
}

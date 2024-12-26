package projectum.vistas.HomePage;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import projectum.data.Rol;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.vistas.formCIO.formCIOView;
import projectum.vistas.formOT.formOTView;


@PageTitle("Home")
@Route("homeOT")
@Menu(order = 15, icon = LineAwesomeIconUrl.HOME_SOLID)
@RolesAllowed("OT")
public class OTHomePageView extends VerticalLayout implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {return Rol.OT;}

    public OTHomePageView() {
        H1 h1 = new H1();
        Paragraph textLarge = new Paragraph();
        RouterLink routerLink = new RouterLink();


        h1.setText("Bienvenido de vuelta Oficina Tecnica");
        h1.setWidth("max-content");
        textLarge.setText("Aqui tiene todos los proyectos que no se han valorado todavia");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");


        routerLink.setText("Avalar proyectos");
        routerLink.setRoute(formOTView.class);
        add(h1);
        add(textLarge);

        add(routerLink);
    }
}
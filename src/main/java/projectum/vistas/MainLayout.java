package projectum.vistas;

import org.vaadin.lineawesome.LineAwesomeIcon;
import projectum.data.Rol;
import projectum.data.entidades.Usuario;
import projectum.security.login.AuthenticatedUser;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import projectum.vistas.HomePage.*;
import projectum.vistas.aceptarProyectos.AceptarProyectosView;
import projectum.vistas.adminConvocatoria.AdminConvocatoriasView;
import projectum.vistas.adminUsers.AdminUsersView;
import projectum.vistas.avalarProyecto.AvalarProyectoView;
import projectum.vistas.faq.FAQView;
import projectum.vistas.formularioProyecto.FormProyectoView;
import projectum.vistas.gestionProyectos.GestionProyectosView;
import projectum.vistas.proyectos.ProyectosByIDView;
import projectum.vistas.proyectos.ProyectosView;
import projectum.vistas.sobrenosotros.SobreNosotrosView;
import projectum.vistas.userProfile.UserProfileView;
import projectum.vistas.valoracionCIO.ValoracionCIOView;
import projectum.vistas.valoracionOT.ValoracionOTView;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("Projectum");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        Optional<Usuario> maybeUser = authenticatedUser.get();

        if (accessChecker.hasAccess(HomePageView.class) && maybeUser.isEmpty()) {
            nav.addItem(new SideNavItem("Home", HomePageView.class, LineAwesomeIcon.HOME_SOLID.create()));
        }

        if (accessChecker.hasAccess(CioHomePageView.class)) {
            nav.addItem(new SideNavItem("Home", CioHomePageView.class, LineAwesomeIcon.HOME_SOLID.create()));
        }

        if (accessChecker.hasAccess(OTHomePageView.class)) {
            nav.addItem(new SideNavItem("Home", OTHomePageView.class, LineAwesomeIcon.HOME_SOLID.create()));
        }

        if (accessChecker.hasAccess(AdminHomePageView.class)) {
            nav.addItem(new SideNavItem("Home", AdminHomePageView.class, LineAwesomeIcon.HOME_SOLID.create()));
        }

        if (accessChecker.hasAccess(HomeUserView.class)) {
            nav.addItem(new SideNavItem("Home", HomeUserView.class, LineAwesomeIcon.HOME_SOLID.create()));
        }

        if (accessChecker.hasAccess(PromotorHomePageView.class)) {
            nav.addItem(new SideNavItem("Home", PromotorHomePageView.class, LineAwesomeIcon.HOME_SOLID.create()));
        }

        if (accessChecker.hasAccess(UserProfileView.class)) {
            nav.addItem(new SideNavItem("Perfil", UserProfileView.class, LineAwesomeIcon.USER_ALT_SOLID.create()));
        }

        if (accessChecker.hasAccess(ProyectosView.class)) {
            nav.addItem(new SideNavItem("Proyectos", ProyectosView.class, LineAwesomeIcon.BOOK_SOLID.create()));
        }

        if (accessChecker.hasAccess(GestionProyectosView.class)) {
            nav.addItem(new SideNavItem("Gestionar Proyectos", GestionProyectosView.class, LineAwesomeIcon.BOOK_SOLID.create()));
        }

        if (accessChecker.hasAccess(AceptarProyectosView.class)) {
            nav.addItem(new SideNavItem("Aceptar Proyectos", AceptarProyectosView.class, LineAwesomeIcon.BOOK_SOLID.create()));
        }

        if (accessChecker.hasAccess(AvalarProyectoView.class)) {
            nav.addItem(new SideNavItem("Avalar Proyectos",AvalarProyectoView.class, LineAwesomeIcon.BOOK_SOLID.create()));
        }

        if (accessChecker.hasAccess(AdminUsersView.class)) {
            nav.addItem(new SideNavItem("Administrar usuarios", AdminUsersView.class, LineAwesomeIcon.USER_ALT_SOLID.create()));
        }

        if (accessChecker.hasAccess(AdminConvocatoriasView.class)) {
            nav.addItem(new SideNavItem("Administrar Convocatorias", AdminConvocatoriasView.class, LineAwesomeIcon.USER_ALT_SOLID.create()));
        }

        if (accessChecker.hasAccess(ValoracionOTView.class)) {
            nav.addItem(new SideNavItem("Valorar Proyectos", ValoracionOTView.class, LineAwesomeIcon.LIST_SOLID.create()));
        }

        if (accessChecker.hasAccess(ValoracionCIOView.class)) {
            nav.addItem(new SideNavItem("Valorar Proyectos", ValoracionCIOView.class, LineAwesomeIcon.LIST_SOLID.create()));
        }

        if (accessChecker.hasAccess(FormProyectoView.class)) {
            nav.addItem(new SideNavItem("Crear proyecto", FormProyectoView.class, LineAwesomeIcon.PLUS_SOLID.create()));
        }

        if (accessChecker.hasAccess(ProyectosByIDView.class) && maybeUser.isPresent() && maybeUser.get().getRol() != Rol.ADMIN) {
            nav.addItem(new SideNavItem("Proyectos de tu usuario", ProyectosByIDView.class, LineAwesomeIcon.BOOK_SOLID.create()));
        }

        if (accessChecker.hasAccess(SobreNosotrosView.class)) {
            nav.addItem(new SideNavItem("Sobre Nosotros", SobreNosotrosView.class, LineAwesomeIcon.INFO_CIRCLE_SOLID.create()));
        }

        if (accessChecker.hasAccess(FAQView.class)) {
            nav.addItem(new SideNavItem("FAQ", FAQView.class, LineAwesomeIcon.QUESTION_CIRCLE_SOLID.create()));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<Usuario> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            Usuario user = maybeUser.get();

            Avatar avatar = new Avatar(user.getUsername());
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getUsername());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Cerrar sesión", e -> {
                authenticatedUser.logout();
            });
            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Iniciar Sesión");
            layout.add(loginLink);
            Anchor signIn = new Anchor("sign-in", "Registrarte");
            layout.add(signIn);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }

}

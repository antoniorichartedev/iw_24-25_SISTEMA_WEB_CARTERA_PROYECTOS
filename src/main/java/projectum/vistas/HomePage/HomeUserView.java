package projectum.vistas.HomePage;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;
import projectum.security.login.AuthenticatedUser;
import projectum.vistas.MainLayout;

@PageTitle("home user")
@Route(value = "homeUser", layout = MainLayout.class)
@RolesAllowed({"USER", "SOLICITANTE"})
public class HomeUserView extends VerticalLayout {
    private final UsuarioService usuarioService;
    private final AuthenticatedUser authenticatedUser;
    private Usuario usuario;

    public HomeUserView(UsuarioService usuarioService, AuthenticatedUser authenticatedUser) {
        this.usuarioService = usuarioService;
        this.authenticatedUser = authenticatedUser;

        // Cargamos al usuario que está autenticado ahora mismo.
        authenticatedUser.get().ifPresent(user ->
                this.usuario = usuarioService.loadUserById(user.getId()).orElse(null)
        );

        // Si el usuario no está autenticado, lo redirigimos al login.
        if (usuario == null) {
            UI.getCurrent().navigate("login");
            return;
        }

        H1 labelProyecto = new H1("Bienvenido/a " + usuario.getUsername());
        labelProyecto.getStyle().set("font-size", "30px").set("font-weight", "bold");
        labelProyecto.getStyle().set("color", "blue");

        H2 informacion = new H2("¿Qué desea hacer?");

        Button perfilButton = new Button("Ver perfil");
        perfilButton.addClickListener(e -> {
            UI.getCurrent().navigate("perfil");
        });

        perfilButton.getElement().addEventListener("mouseover", e -> {
            perfilButton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        perfilButton.getElement().addEventListener("mouseout", e -> {
            perfilButton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        Button proyectosButton = new Button("Ver proyectos");
        proyectosButton.addClickListener(e -> {
            UI.getCurrent().navigate("proyectos");
        });

        proyectosButton.getElement().addEventListener("mouseover", e -> {
            proyectosButton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        proyectosButton.getElement().addEventListener("mouseout", e -> {
            proyectosButton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        Button crearButton = new Button("Crear proyecto");
        crearButton.addClickListener(e -> {
            UI.getCurrent().navigate("formularioProyecto");
        });

        crearButton.getElement().addEventListener("mouseover", e -> {
            crearButton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        crearButton.getElement().addEventListener("mouseout", e -> {
            crearButton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        Button misProyectosButton = new Button("Ver mis proyectos");
        misProyectosButton.addClickListener(e -> {
            UI.getCurrent().navigate("proyectosById");
        });

        misProyectosButton.getElement().addEventListener("mouseover", e -> {
            misProyectosButton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        misProyectosButton.getElement().addEventListener("mouseout", e -> {
            misProyectosButton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        HorizontalLayout botonesLayout = new HorizontalLayout(perfilButton, proyectosButton, crearButton, misProyectosButton);
        botonesLayout.setSpacing(true); // Espacio entre botones
        botonesLayout.setPadding(true); // Espacio interno
        botonesLayout.setAlignItems(Alignment.CENTER); // Centrar verticalmente los botones

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(labelProyecto, informacion, botonesLayout);
    }
}

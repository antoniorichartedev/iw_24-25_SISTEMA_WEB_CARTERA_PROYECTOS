package projectum.vistas.HomePage;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/globe-solid.svg")
@AnonymousAllowed
public class HomePageView extends VerticalLayout implements BeforeEnterObserver {

    private Button loginButton;
    private Button registerButton;

    public HomePageView() {
        // Fondo
        getStyle()
                .set("background-image", "url('images/home.jpg')")
                .set("background-size", "cover") // Ajusta la imagen al tamaño del contenedor.
                .set("background-position", "center") // Centra la imagen.
                .set("height", "100vh") // Altura dinámica al 100% de la ventana.
                .set("width", "100vw") // Anchura dinámica al 100% de la ventana.
                .set("margin", "0") // Sin márgenes.
                .set("padding", "0") // Sin relleno.
                .set("display", "flex") // Usa Flexbox para alinear elementos.
                .set("flex-direction", "column") // Coloca los elementos en columna.
                .set("align-items", "center") // Alinea elementos horizontalmente.
                .set("justify-content", "center") // Alinea elementos verticalmente.
                .set("overflow", "hidden") // Oculta cualquier desbordamiento.
                .set("color", "white") // Texto blanco.
                .set("text-shadow", "0px 0px 10px rgba(0,0,0,0.7)"); // Sombra para mayor legibilidad.

        // Título principal
        H1 welcomeMessage = new H1("¡Bienvenido a Projectum!");
        welcomeMessage.getStyle()
                .set("font-size", "5vw") // Tamaño de fuente en relación al ancho de la ventana.
                .set("text-align", "center");

        // Eslogan
        Span slogan = new Span("Transformando ideas en proyectos, juntos.");
        slogan.getStyle()
                .set("font-size", "2vw") // Ajuste dinámico para pantallas más pequeñas.
                .set("font-style", "italic");

        // Botón de inicio de sesión
        loginButton = new Button("Iniciar sesión");
        loginButton.addClickListener(e -> UI.getCurrent().navigate("login"));
        loginButton.getStyle()
                .set("font-size", "1.5vw") // Tamaño de texto relativo al ancho de la ventana.
                .set("padding", "1vw 2vw") // Ajuste de relleno dinámico.
                .set("border", "none")
                .set("border-radius", "10px")
                .set("background-color", "#007BFF")
                .set("color", "white")
                .set("cursor", "pointer");

        loginButton.getElement().addEventListener("mouseover", e -> {
            loginButton.getElement().getStyle().set("background-color", "#0056b3");
        });
        loginButton.getElement().addEventListener("mouseout", e -> {
            loginButton.getElement().getStyle().set("background-color", "#007BFF");
        });

        // Botón de registro
        registerButton = new Button("Registrarse");
        registerButton.addClickListener(e -> UI.getCurrent().navigate("sign-in"));
        registerButton.getStyle()
                .set("font-size", "1.5vw")
                .set("padding", "1vw 2vw")
                .set("border", "none")
                .set("border-radius", "10px")
                .set("background-color", "#28a745")
                .set("color", "white")
                .set("cursor", "pointer");

        registerButton.getElement().addEventListener("mouseover", e -> {
            registerButton.getElement().getStyle().set("background-color", "#1e7e34");
        });
        registerButton.getElement().addEventListener("mouseout", e -> {
            registerButton.getElement().getStyle().set("background-color", "#28a745");
        });

        // Contenedor para botones
        HorizontalLayout buttonLayout = new HorizontalLayout(loginButton, registerButton);
        buttonLayout.setSpacing(true);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.getStyle()
                .set("margin-top", "20px");

        // Alineación de todo el diseño
        setAlignItems(Alignment.CENTER);
        setSpacing(false);
        add(welcomeMessage, slogan, buttonLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (VaadinService.getCurrentRequest().getUserPrincipal() != null) {
            // Redirigimos a login para que el beforeEnter de la vista del login
            // funcione y no se vea eclipsado con este beforeEnter.
            event.forwardTo("login");
        }
    }
}

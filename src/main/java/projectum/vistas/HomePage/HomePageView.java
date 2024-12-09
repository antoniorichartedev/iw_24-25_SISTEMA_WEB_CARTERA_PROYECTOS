package projectum.vistas.HomePage;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = "line-awesome/svg/globe-solid.svg")
@AnonymousAllowed

public class HomePageView extends VerticalLayout {

    private Button loginButton;
    private Button registerButton;

    public HomePageView() {
        H1 welcomeMessage = new H1("¡Bienvenido a la cartera de proyectos!");
        welcomeMessage.getStyle().set("color", "blue");

        Label subtitleMessage = new Label("Inicia sesión para solicitar un nuevo proyecto, ver tus proyectos solicitados y su estado");
        subtitleMessage.getStyle()
                .set("color", "darkorange")
                .set("font-size", "20px");

        //IMAGEN
        Image logo = new Image("https://gabcomunicacion.uca.es/wp-content/uploads/2021/07/logo_V2_COLOR_210x297mm.png?u", "Descripción alternativa");

        logo.setWidth("200px");
        logo.setHeight("300px");
        logo.getStyle().set("max-width", "100%");
        logo.getStyle().set("object-fit", "contain");

        //BOTON LOGIN
        loginButton = new Button("Iniciar sesión");
        loginButton.addClickListener(e -> {
            UI.getCurrent().navigate("login");
        });

        loginButton.getStyle()
                .set("font-size", "20px")
                .set("padding", "15px 30px")
                .set("font-weight", "bold");

        //codigo para que se marque como seleccionado
        loginButton.getElement().addEventListener("mouseover", e -> {
            loginButton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        loginButton.getElement().addEventListener("mouseout", e -> {
            loginButton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        //BOTON REGISTRAR
        registerButton = new Button("Registrarse");
        registerButton.addClickListener(e -> {
            UI.getCurrent().navigate("sign-in");
        });

        registerButton.getStyle()
                .set("font-size", "20px")  // Tamaño de la fuente más grande
                .set("padding", "15px 30px")  // Aumenta el relleno (espaciado dentro del botón)
                .set("font-weight", "bold");

        //codigo para que se marque como seleccionado
        registerButton.getElement().addEventListener("mouseover", e -> {
            registerButton.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        registerButton.getElement().addEventListener("mouseout", e -> {
            registerButton.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true); // Añade espacio entre los botones
        buttonLayout.setAlignItems(Alignment.CENTER); // Asegura la alineación vertical centrada

        buttonLayout.add(loginButton, registerButton);

        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setMargin(false);
        setSizeFull(); // El diseño ocupa toda el área disponible
        setHeightFull(); // Asegura que el layout tenga el 100% de altura
        getStyle()
                .set("overflow", "hidden") // Elimina scrolls de diseño
                .set("box-sizing", "border-box") // Garantiza que los elementos respeten los bordes
                .set("max-width", "100vw") // Evita que se desborde horizontalmente
                .set("max-height", "100vh");

        add(welcomeMessage, subtitleMessage, logo, buttonLayout);
    }
}

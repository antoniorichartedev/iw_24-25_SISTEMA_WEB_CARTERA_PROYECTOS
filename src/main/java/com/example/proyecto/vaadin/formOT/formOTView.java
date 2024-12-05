package com.example.proyecto.vaadin.formOT;

import com.example.proyecto.security.RolRestrictions.RoleRestrictedView;
import com.example.proyecto.spring.Rol;
import com.example.proyecto.spring.Usuario.Usuario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Oficina Técnica Formulario")
@Route("formOT")
@Menu(order = 5, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
public class formOTView extends VerticalLayout implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return Rol.CIO;
    }

    public formOTView(){
        Label label = new Label("10 PREGUNTAS A 10 PUNTOS CADA UNA PARA UN TOTAL DE 100");
        label.getStyle().set("font-size", "18px").set("font-weight", "bold");
        // Crear campos
        TextField pregunta1 = new TextField("¿En qué medida la solución propuesta cumple con los requisitos funcionales establecidos?");
        pregunta1.setWidth("100%");

        TextField pregunta2 = new TextField("¿Qué tan fácil será mantener y actualizar la solución en el futuro?");
        pregunta2.setWidth("100%");

        TextField pregunta3 = new TextField("¿Qué tan bien se adapta la solución para ser utilizada en diferentes entornos tecnológicos o plataformas? ");
        pregunta3.setWidth("100%");

        TextField pregunta4 = new TextField("¿Qué tan eficiente es la solución en términos de uso de recursos (memoria, tiempo de respuesta, carga del sistema)?");
        pregunta4.setWidth("100%");

        TextField pregunta5 = new TextField("¿Qué tan compatible es la solución con otros sistemas o aplicaciones existentes en la universidad?");
        pregunta5.setWidth("100%");

        TextField pregunta6 = new TextField("¿Qué tan segura es la solución frente a amenazas externas o internas, como ciberataques o fugas de datos?");
        pregunta6.setWidth("100%");

        TextField pregunta7 = new TextField("¿Qué tan adecuada y efectiva es la garantía ofrecida por el fabricante o proveedor en cuanto a la duración y cobertura? ");
        pregunta7.setWidth("100%");

        TextField pregunta8 = new TextField("¿Qué tan satisfactorio es el soporte técnico ofrecido por el proveedor en términos de resolución de problemas?");
        pregunta8.setWidth("100%");

        TextField pregunta9 = new TextField("¿Qué tan completa y útil es la documentación proporcionada por el fabricante o proveedor?");
        pregunta9.setWidth("100%");

        TextField pregunta10 = new TextField("¿Qué tan fácil es para los usuarios finales entender y usar la solución? ");
        pregunta10.setWidth("100%");

        // Botón para enviar
        Button guardar = new Button("Enviar", event -> {
            Notification.show("Datos guardados");
        });

        //Scroll
        Div formularioContainer = new Div(label, pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, pregunta7, pregunta8, pregunta9, pregunta10, guardar);
        formularioContainer.setWidth("100%");  // Aseguramos que el contenedor ocupe todo el ancho disponible
        formularioContainer.getStyle().set("margin", "auto").set("padding", "20px");

        //centrar formulario
        formularioContainer.getStyle().set("display", "flex").set("flex-direction", "column")
                .set("align-items", "center")
                .set("max-width", "800px")
                .set("margin", "0 auto");
        add(formularioContainer);
    }
}

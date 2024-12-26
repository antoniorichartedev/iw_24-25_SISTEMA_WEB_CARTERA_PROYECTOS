package projectum.vistas.formCIO;

import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.data.Rol;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("CIO Formulario")
@Route("formCIO")
@Menu(order = 6, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
@RolesAllowed("CIO")
public class formCIOView extends VerticalLayout implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return Rol.CIO;
    }
    public formCIOView(){
        Span label = new Span("20 PREGUNTAS QUE VALEN 5 PUNTOS CADA UNA, SE VALORA EN 100 PUNTOS");
        label.getStyle().set("font-size", "18px").set("font-weight", "bold");

        Span label1 = new Span("Necesidad y urgencia");
        label1.getStyle().set("font-size", "15px").set("font-weight", "bold");

        // Crear campos
        TextField pregunta1 = new TextField("¿Qué tan urgente es cubrir esta necesidad en relación con otros proyectos en curso?");
        pregunta1.setWidth("100%");

        TextField pregunta2 = new TextField("¿Cómo de importante es para la universidad cubrir esta necesidad?");
        pregunta2.setWidth("100%");

        Span label2 = new Span("Requisitos PUCA");
        label2.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta3 = new TextField("¿Qué tan alineado está este proyecto con la necesidad de innovar, rediseñar o actualizar la oferta formativa?");
        pregunta3.setWidth("100%");

        TextField pregunta4 = new TextField("¿Cuánto contribuye el proyecto a alcanzar los niveles más altos de calidad en la oferta formativa de la universidad?");
        pregunta4.setWidth("100%");

        TextField pregunta5 = new TextField("¿Cuánto ayudará este proyecto a mejorar el posicionamiento en investigación y transferencia de la UCA a la sociedad?");
        pregunta5.setWidth("100%");

        TextField pregunta6 = new TextField("¿Qué tan bien contribuye el proyecto a consolidar un modelo de gobierno sostenible y socialmente responsable? ");
        pregunta6.setWidth("100%");

        TextField pregunta7 = new TextField("¿Qué tan bien promueve el proyecto la transparencia en la universidad?");
        pregunta7.setWidth("100%");

        Span label3 = new Span("Impacto y beneficios");
        label3.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta8 = new TextField("¿Cómo de positivo será el impacto del proyecto sobre las unidades afectadas?");
        pregunta8.setWidth("100%");

        TextField pregunta9 = new TextField("¿Qué tan significativos serán los ahorros en tiempo, personas o dinero que se obtendrán con el proyecto?");
        pregunta9.setWidth("100%");

        TextField pregunta10 = new TextField("¿Qué tanto mejorará la calidad de la gestión de los procesos con este proyecto?");
        pregunta10.setWidth("100%");

        TextField pregunta11 = new TextField("¿Qué tanto mejorará la experiencia de los integrantes de la universidad?");
        pregunta11.setWidth("100%");

        Span label4 = new Span("Riesgos y complejidad");
        label4.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta12 = new TextField("¿Cómo de complejos son los elementos técnicos del proyecto?");
        pregunta12.setWidth("100%");

        TextField pregunta13 = new TextField("¿Cuánto pueden complicar los elementos normativos la implementación del proyecto?");
        pregunta13.setWidth("100%");

        TextField pregunta14 = new TextField("¿Qué tan alta es la dimensión del proyecto en cuanto a costes?");
        pregunta14.setWidth("100%");

        TextField pregunta15 = new TextField("¿Qué tan alta es la dimensión del proyecto en cuanto a tiempo de desarrollo?");
        pregunta15.setWidth("100%");

        TextField pregunta16 = new TextField("¿Qué tan sostenible es el mantenimiento a largo plazo del proyecto?");
        pregunta16.setWidth("100%");

        Span label5 = new Span("Memorias e hitos");
        label5.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta17 = new TextField("¿Cómo de claros y medibles son los indicadores de éxito?");
        pregunta17.setWidth("100%");

        TextField pregunta18 = new TextField("¿Qué tan realistas son las metas del proyecto?");
        pregunta18.setWidth("100%");

        TextField pregunta19 = new TextField("¿Qué tan completa es la información para estimar costes y recursos que se encuentra en la memoria?");
        pregunta19.setWidth("100%");

        TextField pregunta20 = new TextField("¿Cómo de bien definidos están los hitos del proyecto?");
        pregunta20.setWidth("100%");

        // Botón para enviar
        Button guardar = new Button("Enviar", event -> {
            Notification.show("Datos guardados");
        });

        //Scroll
        Div formularioContainer = new Div(label, label1, pregunta1, pregunta2, label2, pregunta3,
                pregunta4, pregunta5, pregunta6, pregunta7, label3, pregunta8, pregunta9, pregunta10,
                pregunta11, label4, pregunta12, pregunta13, pregunta14, pregunta15, pregunta16, label5,
                pregunta17, pregunta18, pregunta19, pregunta20, guardar);
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

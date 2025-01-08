package projectum.vistas.formCIO;


import org.springframework.beans.factory.annotation.Qualifier;
import projectum.data.Estado;
import projectum.data.entidades.Formulario;
import projectum.data.entidades.Proyecto;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.*;
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
import projectum.security.login.AuthenticatedUser;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@PageTitle("CIO Formulario")
@Route("formCIO")
@Menu(order = 6, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
@RolesAllowed("CIO")
public class formCIOView extends VerticalLayout implements RoleRestrictedView {

    private final UsuarioService usuarioService;
    private final CorreoRealService correoService;

    @Override
    public Rol getRequiredRole() {
        return Rol.CIO;
    }

    private AuthenticatedUser authenticatedUser;
    private FormularioService formularioService;
    private ProyectoService proyectoService;

    // Puntuación que vamos a ir acumulando por cada pregunta respondida.
    AtomicInteger finalPuntuacionMax = new AtomicInteger(0);

    // Método auxiliar para configurar preguntas y actualizar la puntuación
    private TextField crearPregunta(String textoPregunta, AtomicInteger puntuacionMax) {
        TextField pregunta = new TextField(textoPregunta);
        pregunta.setWidth("100%");
        pregunta.addValueChangeListener(event -> {
            try {
                // Convertimos el valor ingresado a un entero.
                int valor = Integer.parseInt(event.getValue());

                // Actualizamos la puntuación acumulada.
                puntuacionMax.set(puntuacionMax.get() + valor);

                // Opcional: Mostrar en consola para depuración.
                System.out.println("Puntuación acumulada: " + puntuacionMax.get());
            } catch (NumberFormatException e) {
                Notification.show("Por favor, introduce un número válido en: " + textoPregunta);
            }
        });
        return pregunta;
    }

    public formCIOView(AuthenticatedUser authenticatedUser, FormularioService formularioService, UsuarioService usuarioService, ProyectoService proyectoService, CorreoRealService correoService){
        this.authenticatedUser = authenticatedUser;
        this.formularioService = formularioService;
        this.proyectoService = proyectoService;

        Span label = new Span("20 PREGUNTAS QUE VALEN 5 PUNTOS CADA UNA, SE VALORA EN 100 PUNTOS");
        label.getStyle().set("font-size", "18px").set("font-weight", "bold");

        TextField txtproy = new TextField("Título del proyecto");

        Span label1 = new Span("Necesidad y urgencia");
        label1.getStyle().set("font-size", "15px").set("font-weight", "bold");

        // Creamos las preguntas.
        TextField pregunta1 = crearPregunta("¿Qué tan urgente es cubrir esta necesidad en relación con otros proyectos en curso?", finalPuntuacionMax);

        TextField pregunta2 = crearPregunta("¿Cómo de importante es para la universidad cubrir esta necesidad?", finalPuntuacionMax);

        Span label2 = new Span("Requisitos PUCA");
        label2.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta3 = crearPregunta("¿Qué tan alineado está este proyecto con la necesidad de innovar, rediseñar o actualizar la oferta formativa?", finalPuntuacionMax);

        TextField pregunta4 = crearPregunta("¿Cuánto contribuye el proyecto a alcanzar los niveles más altos de calidad en la oferta formativa de la universidad?", finalPuntuacionMax);

        TextField pregunta5 = crearPregunta("¿Cuánto ayudará este proyecto a mejorar el posicionamiento en investigación y transferencia de la UCA a la sociedad?", finalPuntuacionMax);

        TextField pregunta6 = crearPregunta("¿Qué tan bien contribuye el proyecto a consolidar un modelo de gobierno sostenible y socialmente responsable?", finalPuntuacionMax);

        TextField pregunta7 = crearPregunta("¿Qué tan bien promueve el proyecto la transparencia en la universidad?", finalPuntuacionMax);

        Span label3 = new Span("Impacto y beneficios");
        label3.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta8 = crearPregunta("¿Cómo de positivo será el impacto del proyecto sobre las unidades afectadas?", finalPuntuacionMax);

        TextField pregunta9 = crearPregunta("¿Qué tan significativos serán los ahorros en tiempo, personas o dinero que se obtendrán con el proyecto?", finalPuntuacionMax);

        TextField pregunta10 = crearPregunta("¿Qué tanto mejorará la calidad de la gestión de los procesos con este proyecto?", finalPuntuacionMax);

        TextField pregunta11 = crearPregunta("¿Qué tanto mejorará la experiencia de los integrantes de la universidad?", finalPuntuacionMax);

        Span label4 = new Span("Riesgos y complejidad");
        label4.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta12 = crearPregunta("¿Cómo de complejos son los elementos técnicos del proyecto?", finalPuntuacionMax);

        TextField pregunta13 = crearPregunta("¿Cuánto pueden complicar los elementos normativos la implementación del proyecto?", finalPuntuacionMax);

        TextField pregunta14 = crearPregunta("¿Qué tan alta es la dimensión del proyecto en cuanto a costes?", finalPuntuacionMax);

        TextField pregunta15 = crearPregunta("¿Qué tan alta es la dimensión del proyecto en cuanto a tiempo de desarrollo?", finalPuntuacionMax);

        TextField pregunta16 = crearPregunta("¿Qué tan sostenible es el mantenimiento a largo plazo del proyecto?", finalPuntuacionMax);

        Span label5 = new Span("Memorias e hitos");
        label5.getStyle().set("font-size", "15px").set("font-weight", "bold");

        TextField pregunta17 = crearPregunta("¿Cómo de claros y medibles son los indicadores de éxito?", finalPuntuacionMax);

        TextField pregunta18 = crearPregunta("¿Qué tan realistas son las metas del proyecto?", finalPuntuacionMax);

        TextField pregunta19 = crearPregunta("¿Qué tan completa es la información para estimar costes y recursos que se encuentra en la memoria?", finalPuntuacionMax);

        TextField pregunta20 = crearPregunta("¿Cómo de bien definidos están los hitos del proyecto?", finalPuntuacionMax);

        // Lo guardamos todo.
        Button guardar = new Button("Enviar", event -> {
            try {
                // Obtener el usuario autenticado, si está presente
                Optional<Usuario> opUsuario = authenticatedUser.get();

                // Obtenemos el proyecto.
                Optional<Proyecto> proy = proyectoService.getProyectoByTitulo(txtproy.getValue());
                if (opUsuario.isPresent() && opUsuario.get().getRol() == Rol.CIO && proy.isPresent()) {
                    Formulario form = new Formulario();

                    // Guardar puntuación total
                    form.setPuntuacion(finalPuntuacionMax.get());
                    form.setCio(opUsuario.get());
                    form.setProyecto(proy.get());

                    // Obtenemos los formularios del proyecto.
                    List<Formulario> formsProyecto = formularioService.getFormulariosByProyectoId(proy.get().getId());

                    // Guardar el formulario en la base de datos
                    if (formsProyecto.size() < 2) {
                        formularioService.saveFormulario(form);
                        formsProyecto = formularioService.getFormulariosByProyectoId(proy.get().getId());

                        // Si tenemos dos formularios y el estado del proyecto es valoradoOT cambiamos su estado a Valorado.
                        if (formsProyecto.size() == 2) {
                            proy.get().setEstado(Estado.valorado);
                            proyectoService.saveProyecto(proy.get());
                            correoService.enviarCorreoProyectoValorado(proy.get().getSolicitante(), proy.get());
                            // Notificar al usuario
                            Notification.show("Formulario guardado correctamente", 3500, Notification.Position.TOP_CENTER);
                        } else {
                            proy.get().setEstado(Estado.valoradoCIO);
                            proyectoService.saveProyecto(proy.get());
                            correoService.enviarCorreoProyectoValoradoCIO(proy.get().getSolicitante(), proy.get());
                            // Notificar al usuario
                            Notification.show("Formulario guardado correctamente", 3500, Notification.Position.TOP_CENTER);
                        }
                    } else {
                        Notification.show("Ya has hecho el formulario para este proyecto.", 3500, Notification.Position.TOP_CENTER);
                    }

                } else {
                    Notification.show("No tienes permiso para realizar esta acción.", 3500, Notification.Position.TOP_CENTER);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Notification.show("Error al guardar el formulario.", 3500, Notification.Position.TOP_CENTER);
            }
        });

        // Agregar todo al contenedor
        Div formularioContainer = new Div(label, txtproy, label1, pregunta1, pregunta2, label2, pregunta3, pregunta4, pregunta5, pregunta6,
                pregunta7, label3, pregunta8, pregunta9, pregunta10, pregunta11, label4, pregunta12, pregunta13, pregunta14, pregunta15, pregunta16, label5,
                pregunta17, pregunta18, pregunta19, pregunta20, guardar);
        formularioContainer.setWidth("100%");
        formularioContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("max-width", "800px")
                .set("margin", "0 auto")
                .set("padding", "20px");

        add(formularioContainer);
        this.usuarioService = usuarioService;
        this.correoService = correoService;
    }
}

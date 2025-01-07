package projectum.vistas.formOT;

import jakarta.annotation.security.RolesAllowed;
import projectum.data.Estado;
import projectum.data.servicios.CorreoRealService;
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
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import projectum.data.servicios.FormularioService;
import projectum.data.servicios.ProyectoService;
import projectum.data.servicios.UsuarioService;
import projectum.security.login.AuthenticatedUser;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import projectum.data.entidades.Formulario;
import projectum.data.entidades.Proyecto;
import projectum.data.entidades.Usuario;

@PageTitle("Oficina Técnica Formulario")
@Route("formOT")
@Menu(order = 5, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
@RolesAllowed("OT")
public class formOTView extends VerticalLayout implements RoleRestrictedView {

    private final UsuarioService usuarioService;

    @Override
    public Rol getRequiredRole() {
        return Rol.OT;
    }

    private AuthenticatedUser authenticatedUser;
    private FormularioService formularioService;
    private ProyectoService proyectoService;
    private CorreoRealService correoService;

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

    public formOTView(AuthenticatedUser authenticatedUser, FormularioService formularioService, UsuarioService usuarioService, ProyectoService proyectoService, CorreoRealService correoService) {
        this.authenticatedUser = authenticatedUser;
        this.formularioService = formularioService;
        this.proyectoService = proyectoService;
        this.usuarioService = usuarioService;
        this.correoService = correoService;

        Span label = new Span("10 PREGUNTAS A 10 PUNTOS CADA UNA PARA UN TOTAL DE 100");
        label.getStyle().set("font-size", "18px").set("font-weight", "bold");

        TextField txtproy = new TextField("Título del proyecto");

        // Creamos las preguntas
        TextField pregunta1 = crearPregunta("¿En qué medida la solución propuesta cumple con los requisitos funcionales establecidos?", finalPuntuacionMax);
        TextField pregunta2 = crearPregunta("¿Qué tan fácil será mantener y actualizar la solución en el futuro?", finalPuntuacionMax);
        TextField pregunta3 = crearPregunta("¿Qué tan bien se adapta la solución para ser utilizada en diferentes entornos tecnológicos o plataformas?", finalPuntuacionMax);
        TextField pregunta4 = crearPregunta("¿Qué tan eficiente es la solución en términos de uso de recursos (memoria, tiempo de respuesta, carga del sistema)?", finalPuntuacionMax);
        TextField pregunta5 = crearPregunta("¿Qué tan compatible es la solución con otros sistemas o aplicaciones existentes en la universidad?", finalPuntuacionMax);
        TextField pregunta6 = crearPregunta("¿Qué tan segura es la solución frente a amenazas externas o internas, como ciberataques o fugas de datos?", finalPuntuacionMax);
        TextField pregunta7 = crearPregunta("¿Qué tan adecuada y efectiva es la garantía ofrecida por el fabricante o proveedor en cuanto a la duración y cobertura?", finalPuntuacionMax);
        TextField pregunta8 = crearPregunta("¿Qué tan satisfactorio es el soporte técnico ofrecido por el proveedor en términos de resolución de problemas?", finalPuntuacionMax);
        TextField pregunta9 = crearPregunta("¿Qué tan completa y útil es la documentación proporcionada por el fabricante o proveedor?", finalPuntuacionMax);
        TextField pregunta10 = crearPregunta("¿Qué tan fácil es para los usuarios finales entender y usar la solución?", finalPuntuacionMax);

        // Botón para guardar los datos
        Button guardar = new Button("Enviar", event -> {
            try {
                // Obtener el usuario autenticado
                Optional<Usuario> opUsuario = authenticatedUser.get();

                // Obtener el proyecto
                Optional<Proyecto> proy = proyectoService.getProyectoByTitulo(txtproy.getValue());
                if (opUsuario.isPresent() && opUsuario.get().getRol() == Rol.OT && proy.isPresent()) {
                    Formulario form = new Formulario();

                    // Guardar puntuación total
                    form.setPuntuacion(finalPuntuacionMax.get());
                    form.setOt(opUsuario.get());
                    form.setProyecto(proy.get());

                    // Obtenemos los formularios del proyecto.
                    List<Formulario> formsProyecto = formularioService.getFormulariosByProyectoId(proy.get().getId());

                    // Guardar el formulario en la base de datos
                    if (formsProyecto.size() < 2) {
                        formularioService.saveFormulario(form);
                        formsProyecto = formularioService.getFormulariosByProyectoId(proy.get().getId());

                        // Si tenemos dos formularios y el estado del proyecto es valoradoCIO cambiamos su estado a Valorado.
                        if (formsProyecto.size() == 2) {
                            proy.get().setEstado(Estado.valorado);
                            proyectoService.saveProyecto(proy.get());
                            correoService.enviarCorreoProyectoValorado(proy.get().getSolicitante(), proy.get());
                            // Notificar al usuario
                            Notification.show("Formulario guardado correctamente", 3500, Notification.Position.TOP_CENTER);
                        } else {
                            proy.get().setEstado(Estado.valoradoOT);
                            proyectoService.saveProyecto(proy.get());
                            correoService.enviarCorreoProyectoValoradoOT(proy.get().getSolicitante(), proy.get());
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

        // Crear contenedor para el formulario
        Div formularioContainer = new Div(label, txtproy, pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6, pregunta7, pregunta8, pregunta9, pregunta10, guardar);
        formularioContainer.setWidth("100%");
        formularioContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("max-width", "800px")
                .set("margin", "0 auto")
                .set("padding", "20px");

        add(formularioContainer);
    }
}


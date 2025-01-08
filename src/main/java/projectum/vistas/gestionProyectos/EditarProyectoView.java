package projectum.vistas.gestionProyectos;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.Estado;
import projectum.data.entidades.Proyecto;
import projectum.data.servicios.ProyectoService;
import projectum.vistas.MainLayout;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@PageTitle("Editar Proyecto")
@Route(value = "editarProyecto/:id", layout = MainLayout.class)
@RolesAllowed("CIO")
public class EditarProyectoView extends VerticalLayout implements BeforeEnterObserver {

    private final ProyectoService proyectoService;
    private UUID proyectoId;

    public EditarProyectoView(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Obtener el parámetro "id" de la ruta
        Optional<String> idParam = event.getRouteParameters().get("id");

        if (idParam.isPresent()) {
            try {
                // Convertir el ID de String a UUID
                this.proyectoId = UUID.fromString(idParam.get());
                cargarProyecto();
            } catch (IllegalArgumentException e) {
                // Manejar el caso de un ID no válido
                add(new H1("ID no válido: " + idParam.get()));
            }
        } else {
            add(new H1("No se proporcionó un ID válido en la URL."));
        }
    }

    private void cargarProyecto() {
        Optional<Proyecto> optionalProyecto = proyectoService.getProyectoById(proyectoId);
        Proyecto proyecto = optionalProyecto.orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado para el ID: " + proyectoId));

        H1 texto = new H1("Editar proyecto");
        texto.getStyle().set("font-size", "30px").set("font-weight", "bold");
        texto.getStyle().set("color", "blue");

        //titulo
        H2 titulo = new H2("Título: " + proyecto.getTitulo());
        titulo.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField tituloField = new TextField();
        tituloField.setPlaceholder("Cambiar título del proyecto");
        tituloField.setWidth("300px");

        Button tituloButton = new Button("Guardar");
        tituloButton.addClickListener(e -> {
            String nuevotitulo = tituloField.getValue();
            if (nuevotitulo.isEmpty()) {
                Notification.show("Por favor, introduce un título", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setTitulo(nuevotitulo);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Título actualizado", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout tituloLayout = new HorizontalLayout(titulo, tituloField, tituloButton);
        tituloLayout.setAlignItems(Alignment.CENTER);
        tituloLayout.setSpacing(true);
        tituloLayout.getStyle().set("margin-bottom", "20px");

        //acronimo
        H2 acronimo = new H2("Acrónimo: " + proyecto.getAcronimo());
        acronimo.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField acronimoField = new TextField();
        acronimoField.setPlaceholder("Cambiar acrónimo del proyecto");
        acronimoField.setWidth("300px");

        Button acronimoButton = new Button("Guardar");
        acronimoButton.addClickListener(e -> {
            String nuevoacronimo = acronimoField.getValue();
            if (nuevoacronimo.isEmpty()) {
                Notification.show("Por favor, introduce un acrónimo", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setAcronimo(nuevoacronimo);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Acrónimo actualizado", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout acronimoLayout = new HorizontalLayout(acronimo, acronimoField, acronimoButton);
        acronimoLayout.setAlignItems(Alignment.CENTER);
        acronimoLayout.setSpacing(true);
        acronimoLayout.getStyle().set("margin-bottom", "20px");

        //Justificacion
        H2 justificacion = new H2("Justificación: " + proyecto.getJustificacion());
        justificacion.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField justificacionField = new TextField();
        justificacionField.setPlaceholder("Cambiar justificación del proyecto");
        justificacionField.setWidth("300px");

        Button justificacionButton = new Button("Guardar");
        justificacionButton.addClickListener(e -> {
            String nuevajustificacion = justificacionField.getValue();
            if (nuevajustificacion.isEmpty()) {
                Notification.show("Por favor, introduce una justificación", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setJustificacion(nuevajustificacion);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Justificación actualizada", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout justificacionLayout = new HorizontalLayout(justificacion, justificacionField, justificacionButton);
        justificacionLayout.setAlignItems(Alignment.CENTER);
        justificacionLayout.setSpacing(true);
        justificacionLayout.getStyle().set("margin-bottom", "20px");

        //alcance
        H2 alcance = new H2("Alcance: " + proyecto.getAlcance());
        alcance.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField alcanceField = new TextField();
        alcanceField.setPlaceholder("Cambiar alcance del proyecto");
        alcanceField.setWidth("300px");

        Button alcanceButton = new Button("Guardar");
        alcanceButton.addClickListener(e -> {
            String nuevoalcance = alcanceField.getValue();
            if (nuevoalcance.isEmpty()) {
                Notification.show("Por favor, introduce un alcance", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setAlcance(nuevoalcance);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Alcance actualizado", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout alcanceLayout = new HorizontalLayout(alcance, alcanceField, alcanceButton);
        alcanceLayout.setAlignItems(Alignment.CENTER);
        alcanceLayout.setSpacing(true);
        alcanceLayout.getStyle().set("margin-bottom", "20px");

        //estado
        H2 estado = new H2("Estado: ");
        estado.getStyle().set("font-size", "20px").set("font-weight", "bold");

        ComboBox<Estado> estadoComboBox = new ComboBox<>("Cambiar estado del proyecto");
        estadoComboBox.setItems(Estado.values());
        estadoComboBox.setValue(proyecto.getEstado());
        estadoComboBox.setWidth("300px");

        Button estadoButton = new Button("Guardar");
        estadoButton.addClickListener(e -> {
            Estado nuevoestado = estadoComboBox.getValue();
            proyecto.setEstado(nuevoestado);
            proyectoService.saveProyecto(proyecto);
            Notification.show("Estado actualizado", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().getPage().reload();

        });

        HorizontalLayout estadoLayout = new HorizontalLayout(estado, estadoComboBox, estadoButton);
        estadoLayout.setAlignItems(Alignment.CENTER);
        estadoLayout.setSpacing(true);
        estadoLayout.getStyle().set("margin-bottom", "20px");

        //importancia
        H2 importancia = new H2("Importancia: ");
        importancia.getStyle().set("font-size", "20px").set("font-weight", "bold");

        IntegerField importanciaField  = new IntegerField();
        importanciaField.setPlaceholder("Cambiar importancia del proyecto");
        importanciaField.setWidth("300px");
        importanciaField.setMin(1);
        importanciaField.setMax(5);
        importanciaField.setValue(proyecto.getImportancia());

        Button importanciaButton = new Button("Guardar");
        importanciaButton.addClickListener(e -> {
            Integer nuevaimportancia = importanciaField.getValue();
            if (nuevaimportancia == null || nuevaimportancia < 1 || nuevaimportancia > 5) {
                Notification.show("Por favor, introduce una importancia válida", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setImportancia(nuevaimportancia);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Importancia actualizada", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout importanciaLayout = new HorizontalLayout(importancia, importanciaField, importanciaButton);
        importanciaLayout.setAlignItems(Alignment.CENTER);
        importanciaLayout.setSpacing(true);
        importanciaLayout.getStyle().set("margin-bottom", "20px");

        //priorizacion
        H2 priorizacion = new H2("Priorización: ");
        priorizacion.getStyle().set("font-size", "20px").set("font-weight", "bold");

        IntegerField priorizacionField  = new IntegerField();
        priorizacionField.setPlaceholder("Cambiar priorización del proyecto");
        priorizacionField.setWidth("300px");
        priorizacionField.setMin(1);
        priorizacionField.setMax(5);
        priorizacionField.setValue(proyecto.getPriorizacion());

        Button priorizacionButton = new Button("Guardar");
        priorizacionButton.addClickListener(e -> {
            Integer nuevapriorizacion = priorizacionField.getValue();
            if (nuevapriorizacion == null || nuevapriorizacion < 1 || nuevapriorizacion > 5) {
                Notification.show("Por favor, introduce una priorización válida", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setPriorizacion(nuevapriorizacion);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Priorización actualizada", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout priorizacionLayout = new HorizontalLayout(priorizacion, priorizacionField, priorizacionButton);
        priorizacionLayout.setAlignItems(Alignment.CENTER);
        priorizacionLayout.setSpacing(true);
        priorizacionLayout.getStyle().set("margin-bottom", "20px");

        //financiacion
        H2 financiacion = new H2("Financiación: ");
        financiacion.getStyle().set("font-size", "20px").set("font-weight", "bold");

        BigDecimalField financiacionField  = new BigDecimalField();
        financiacionField.setPlaceholder("Cambiar financiación del proyecto");
        financiacionField.setWidth("300px");
        financiacionField.setValue(proyecto.getFinanciacion());

        Button financiacionButton = new Button("Guardar");
        financiacionButton.addClickListener(e -> {
            BigDecimal nuevafinanciacion = financiacionField.getValue();
            if (nuevafinanciacion == null) {
                Notification.show("Por favor, introduce una financiación", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setFinanciacion(nuevafinanciacion);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Financiación actualizada", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout financiacionLayout = new HorizontalLayout(financiacion, financiacionField, financiacionButton);
        financiacionLayout.setAlignItems(Alignment.CENTER);
        financiacionLayout.setSpacing(true);
        financiacionLayout.getStyle().set("margin-bottom", "20px");

        //interesado
        H2 interesado = new H2("Interesado: " + proyecto.getInteresado());
        interesado.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField interesadoField = new TextField();
        interesadoField.setPlaceholder("Cambiar interesado del proyecto");
        interesadoField.setWidth("300px");

        Button interesadoButton = new Button("Guardar");
        interesadoButton.addClickListener(e -> {
            String nuevointeresado = interesadoField.getValue();
            if (nuevointeresado.isEmpty()) {
                Notification.show("Por favor, introduce un interesado", 3000, Notification.Position.MIDDLE);
            } else {
                proyecto.setInteresado(nuevointeresado);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Interesado actualizado", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout interesadoLayout = new HorizontalLayout(interesado, interesadoField, interesadoButton);
        interesadoLayout.setAlignItems(Alignment.CENTER);
        interesadoLayout.setSpacing(true);
        interesadoLayout.getStyle().set("margin-bottom", "20px");

        add(texto, tituloLayout, acronimoLayout, justificacionLayout, alcanceLayout, estadoLayout,
                importanciaLayout, priorizacionLayout, financiacionLayout, interesadoLayout);
    }
}
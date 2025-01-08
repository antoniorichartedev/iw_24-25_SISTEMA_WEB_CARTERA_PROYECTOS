package projectum.vistas.adminConvocatoria;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.entidades.Convocatoria;
import projectum.data.servicios.ConvocatoriaService;
import projectum.vistas.MainLayout;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@PageTitle("Editar Convocatoria")
@Route(value = "editarConvocatoria/:id", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EditarConvocatoriaView extends VerticalLayout implements BeforeEnterObserver {

    private final ConvocatoriaService convocatoriaService;
    private UUID convocatoriaId;

    public EditarConvocatoriaView(ConvocatoriaService convocatoriaService) {
        this.convocatoriaService = convocatoriaService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> idParam = event.getRouteParameters().get("id");

        if (idParam.isPresent()) {
            try {
                this.convocatoriaId = UUID.fromString(idParam.get());
                cargarConvocatoria();
            } catch (IllegalArgumentException e) {
                add(new H1("ID no válido: " + idParam.get()));
            }
        } else {
            add(new H1("No se proporcionó un ID válido en la URL."));
        }
    }

    private void cargarConvocatoria() {
        Optional<Convocatoria> optionalConvocatoria = convocatoriaService.getConvocatoriaById(convocatoriaId);
        Convocatoria convocatoria = optionalConvocatoria.orElseThrow(() -> new IllegalArgumentException("Convocatoria no encontrada para el ID: " + convocatoriaId));

        H1 texto = new H1("Editar Convocatoria");
        texto.getStyle().set("font-size", "30px").set("font-weight", "bold");
        texto.getStyle().set("color", "blue");

        // Nombre
        H2 nombre = new H2("Nombre: " + convocatoria.getNombre());
        nombre.getStyle().set("font-size", "20px").set("font-weight", "bold");

        TextField nombreField = new TextField();
        nombreField.setPlaceholder("Cambiar nombre de la convocatoria");
        nombreField.setWidth("300px");

        Button nombreButton = new Button("Guardar");
        nombreButton.addClickListener(e -> {
            String nuevoNombre = nombreField.getValue();
            if (nuevoNombre.isEmpty()) {
                Notification.show("Por favor, introduce un nombre", 3000, Notification.Position.MIDDLE);
            } else {
                convocatoria.setNombre(nuevoNombre);
                convocatoriaService.saveConvocatoria(convocatoria);
                Notification.show("Nombre actualizado", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout nombreLayout = new HorizontalLayout(nombre, nombreField, nombreButton);
        nombreLayout.setAlignItems(Alignment.CENTER);
        nombreLayout.setSpacing(true);
        nombreLayout.getStyle().set("margin-bottom", "20px");

        // Fechas inicio
        H2 fechaInicio = new H2("Fecha Inicio: " + convocatoria.getFechaInico());
        fechaInicio.getStyle().set("font-size", "20px").set("font-weight", "bold");

        DatePicker fechaInicioField = new DatePicker();
        fechaInicioField.setPlaceholder("Cambiar fecha de inicio");
        fechaInicioField.setWidth("70%");

        DatePicker fechaFinField = new DatePicker();
        fechaInicioField.setPlaceholder("Cambiar fecha de fin");
        fechaInicioField.setWidth("70%");

        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat("dd/MM/yyyy"); // Ajusta el formato de fecha aquí
        fechaInicioField.setI18n(i18n);
        fechaFinField.setI18n(i18n);

        fechaInicioField.setRequiredIndicatorVisible(true);
        fechaInicioField.setValue(convocatoria.getFechaInico().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        Button fechaInicioButton = new Button("Guardar");
        fechaInicioButton.addClickListener(e -> {
            LocalDate nuevaFechaInicio = fechaInicioField.getValue();
            if (nuevaFechaInicio == null) {
                Notification.show("Por favor, introduce una fecha de inicio", 3000, Notification.Position.MIDDLE);
            } else {
                convocatoria.setFechaInico(Date.from(nuevaFechaInicio.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
                convocatoriaService.saveConvocatoria(convocatoria);
                Notification.show("Fecha de inicio actualizada", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout fechaInicioLayout = new HorizontalLayout(fechaInicio, fechaInicioField, fechaInicioButton);
        fechaInicioLayout.setAlignItems(Alignment.CENTER);
        fechaInicioLayout.setSpacing(true);
        fechaInicioLayout.getStyle().set("margin-bottom", "20px");

        // Fecha Fin
        H2 fechaFin = new H2("Fecha Fin: " + convocatoria.getFechaFin());
        fechaFin.getStyle().set("font-size", "20px").set("font-weight", "bold");

        fechaFinField.setPlaceholder("Cambiar fecha de fin");
        fechaFinField.setValue(convocatoria.getFechaFin().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        Button fechaFinButton = new Button("Guardar");
        fechaFinButton.addClickListener(e -> {
            LocalDate nuevaFechaFin = fechaFinField.getValue();
            if (nuevaFechaFin == null) {
                Notification.show("Por favor, introduce una fecha de fin", 3000, Notification.Position.MIDDLE);
            } else {
                convocatoria.setFechaFin(Date.from(nuevaFechaFin.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
                convocatoriaService.saveConvocatoria(convocatoria);
                Notification.show("Fecha de fin actualizada", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().getPage().reload();
            }
        });

        HorizontalLayout fechaFinLayout = new HorizontalLayout(fechaFin, fechaFinField, fechaFinButton);
        fechaFinLayout.setAlignItems(Alignment.CENTER);
        fechaFinLayout.setSpacing(true);
        fechaFinLayout.getStyle().set("margin-bottom", "20px");

        // Estado
        H2 estado = new H2("Estado: " + (convocatoria.getActividad() ? "En Curso" : "Inactiva"));
        estado.getStyle().set("font-size", "20px").set("font-weight", "bold");

        ComboBox<String> estadoComboBox = new ComboBox<>("Cambiar estado de la convocatoria");
        estadoComboBox.setItems("En Curso", "Inactiva");
        estadoComboBox.setValue(convocatoria.getActividad() ? "En Curso" : "Inactiva");

        Button estadoButton = new Button("Guardar");
        estadoButton.addClickListener(e -> {
            String nuevoEstado = estadoComboBox.getValue();
            convocatoria.setActividad("En Curso".equals(nuevoEstado));
            convocatoriaService.saveConvocatoria(convocatoria);
            Notification.show("Estado actualizado", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().getPage().reload();
        });

        HorizontalLayout estadoLayout = new HorizontalLayout(estado, estadoComboBox, estadoButton);
        estadoLayout.setAlignItems(Alignment.CENTER);
        estadoLayout.setSpacing(true);
        estadoLayout.getStyle().set("margin-bottom", "20px");

        add(texto, nombreLayout, fechaInicioLayout, fechaFinLayout, estadoLayout);
    }
}

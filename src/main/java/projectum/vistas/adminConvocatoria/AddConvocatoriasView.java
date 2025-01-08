package projectum.vistas.adminConvocatoria;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.entidades.Convocatoria;
import projectum.data.servicios.ConvocatoriaService;
import projectum.vistas.MainLayout;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@PageTitle("Añadir Convocatorias")
@Route(value = "addConvocatorias", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AddConvocatoriasView extends Composite<VerticalLayout> {

    private final ConvocatoriaService convocatoriaService;

    public AddConvocatoriasView(ConvocatoriaService convocatoriService) {
        this.convocatoriaService = convocatoriService;

        // Crear componentes del formulario
        TextField nombreField = new TextField("Nombre");
        nombreField.setRequired(true);

        DatePicker fechaInicioPicker = new DatePicker("Fecha Inicio");
        fechaInicioPicker.setWidth("70%");
        fechaInicioPicker.setRequiredIndicatorVisible(true);

        DatePicker fechaFinPicker = new DatePicker("Fecha Fin");
        fechaFinPicker.setWidth("70%");
        fechaFinPicker.setRequiredIndicatorVisible(true);

        // Establecer formato de la fecha
        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat("dd/MM/yyyy"); // Ajusta el formato de fecha aquí
        fechaFinPicker.setI18n(i18n);
        fechaInicioPicker.setI18n(i18n);

        TextField estadoField = new TextField("Estado (En Curso / Inactiva)");
        estadoField.setRequired(true);

        Button guardarButton = new Button("Guardar", event -> {
            if (nombreField.isEmpty() || fechaInicioPicker.isEmpty() || fechaFinPicker.isEmpty() || estadoField.isEmpty()) {
                Notification.show("Todos los campos son obligatorios", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                // Crear nueva convocatoria
                Convocatoria nuevaConvocatoria = new Convocatoria();
                nuevaConvocatoria.setId(UUID.randomUUID());
                nuevaConvocatoria.setNombre(nombreField.getValue());
                nuevaConvocatoria.setFechaInicio(
                        Date.from(fechaInicioPicker.getValue()
                            .atStartOfDay(ZoneId.of("Europe/Madrid"))
                            .toInstant())
                );
                nuevaConvocatoria.setFechaFin(
                        Date.from(fechaFinPicker.getValue()
                                .atStartOfDay(ZoneId.of("Europe/Madrid"))
                                .toInstant())
                );
                nuevaConvocatoria.setActividad("En Curso".equalsIgnoreCase(estadoField.getValue()));

                // Guardar convocatoria
                convocatoriaService.saveConvocatoria(nuevaConvocatoria);

                Notification.show("Convocatoria creada con éxito", 3000, Notification.Position.MIDDLE);

                // Redirigir a la vista de administración
                getUI().ifPresent(ui -> ui.navigate("adminConvocatorias"));
            } catch (Exception e) {
                Notification.show("Error al crear la convocatoria: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        // Diseño del formulario
        FormLayout formLayout = new FormLayout();
        formLayout.add(nombreField, fechaInicioPicker, fechaFinPicker, estadoField, guardarButton);

        // Estilo del contenedor principal
        VerticalLayout layout = getContent();
        layout.setWidth("100%");
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.addClassName(LumoUtility.Gap.MEDIUM);

        // Agregar formulario al diseño principal
        layout.add(new Text("Crear nueva convocatoria"), formLayout);
    }
}

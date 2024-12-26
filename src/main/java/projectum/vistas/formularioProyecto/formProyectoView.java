package projectum.vistas.formularioProyecto;
import jakarta.annotation.security.RolesAllowed;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.data.Rol;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
@PageTitle("Formulario para un nuevo proyecto")
@Route("formularioProyecto")
@Menu(order = 4, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
@RolesAllowed({"USER", "SOLICITANTE"})
public class formProyectoView extends VerticalLayout implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return Rol.USER;
    }

    public formProyectoView() {
        //Informacion del proyecto
        Span labelProyecto = new Span("Información del Proyecto");
        labelProyecto.getStyle().set("font-size", "24px").set("font-weight", "bold");

        // Crear campos
        TextField titulo = new TextField("Titulo del Proyecto");
        titulo.setWidth("70%");

        TextField nombre = new TextField("Nombre Corto");
        nombre.setWidth("70%");

        //añadir archivo
        Div uploadContainer = new Div();
        Span labelMemoria = new Span("Memoria del Proyecto");
        labelMemoria.getStyle()
                .set("font-size", "15px")
                .set("font-weight", "normal")
                .set("margin-left", "0px");  // Establecer margen izquierdo
        MemoryBuffer buffer = new MemoryBuffer(); // Almacén temporal en memoria
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "application/pdf"); // Tipos permitidos
        upload.setMaxFiles(1); // Solo permite un archivo
        upload.setMaxFileSize(5 * 1024 * 1024); // Tamaño máximo (5 MB)
        upload.setDropLabel(new Span("Arrastra un archivo aquí o haz clic para cargar"));
        uploadContainer.add(labelMemoria, upload);

        //Informacion del solicitante
        Span labelSolicitante = new Span("Información del Solicitante");
        labelSolicitante.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField solicitante = new TextField("Nombre del Solicitante");
        solicitante.setWidth("70%");

        TextField correo = new TextField("Correo del Solicitante");
        correo.setWidth("70%");

        TextField unidad = new TextField("Unidad del Solicitante");
        unidad.setWidth("70%");

        //Informacion del promotor
        Span labelPromotor = new Span("Información del Promotor");
        labelPromotor.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField promotor = new TextField("Promotor");
        promotor.setWidth("70%");

        IntegerField importancia = new IntegerField("Importancia");
        importancia.setWidth("70%");
        importancia.setMax(10);
        importancia.setMin(1);

        //Informacion de los interesados
        Span labelInteresados = new Span("Información de los interesados");
        labelInteresados.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField interesados = new TextField("Nombre de los interesados");
        interesados.setWidth("70%");

        IntegerField financiacion = new IntegerField("Financiación aportada");
        financiacion.setWidth("70%");

        //Justificacion del proyecto
        Span labelJustificacion = new Span("Justificación del Proyecto");
        labelJustificacion.getStyle().set("font-size", "24px").set("font-weight", "bold");

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Alineamiento con los objetivos estratégicos:");

        checkboxGroup.setItems(
                "Innovar, rediseñar y actualizar nuestra oferta formativa para adaptarla a las necesidades sociales y económicas de nuestro entorno.",
                "Conseguir los niveles más altos de calidad en nuestra oferta formativa propia y reglada.",
                "Aumentar significativamente nuestro posicionamiento en investigación y transferir de forma relevante y útil nuestra investigación a nuestro tejido social y productivo.",
                "Consolidar un modelo de gobierno sostenible y socialmente responsable.",
                "Generar valor compartido con la Comunidad Universitaria.",
                "Reforzar la importancia del papel de la UCA en la sociedad.",
                "Conseguir que la transparencia sea un valor distintivo y relevante en la UCA."
        );

        TextField alcance = new TextField("Alcance");
        alcance.setWidth("70%");

        DatePicker datePicker = new DatePicker("Fecha requerida de puesta en marcha");
        datePicker.setWidth("70%");

        // Establecer formato de la fecha
        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat("dd/MM/yyyy"); // Ajusta el formato de fecha aquí
        datePicker.setI18n(i18n);

        TextField normativa = new TextField("Normativa de aplicación");
        normativa.setWidth("70%");

        //Documentacion adicional
        Span labelDocumentacion = new Span("Documentación Adicional");
        labelDocumentacion.getStyle().set("font-size", "24px").set("font-weight", "bold");

        //añadir archivo
        Div uploadContainer2 = new Div();
        Span labelEspecificaciones = new Span("Especificaciones técnicas");
        labelMemoria.getStyle()
                .set("font-size", "15px")
                .set("font-weight", "normal")
                .set("margin-left", "0px");  // Establecer margen izquierdo
        MemoryBuffer buffer2 = new MemoryBuffer(); // Almacén temporal en memoria
        Upload upload2 = new Upload(buffer);
        upload2.setAcceptedFileTypes("image/jpeg", "image/png", "application/pdf"); // Tipos permitidos
        upload2.setMaxFiles(1); // Solo permite un archivo
        upload2.setMaxFileSize(5 * 1024 * 1024); // Tamaño máximo (5 MB)
        upload2.setDropLabel(new Span("Arrastra un archivo aquí o haz clic para cargar"));
        uploadContainer2.add(labelEspecificaciones, upload2);

        //añadir archivo
        Div uploadContainer3 = new Div();
        Span labelPresupuesto = new Span("Presupuesto(s)");
        labelMemoria.getStyle()
                .set("font-size", "15px")
                .set("font-weight", "normal")
                .set("margin-left", "0px");  // Establecer margen izquierdo
        MemoryBuffer buffer3 = new MemoryBuffer(); // Almacén temporal en memoria
        Upload upload3 = new Upload(buffer);
        upload3.setAcceptedFileTypes("image/jpeg", "image/png", "application/pdf"); // Tipos permitidos
        upload3.setMaxFiles(1); // Solo permite un archivo
        upload3.setMaxFileSize(5 * 1024 * 1024); // Tamaño máximo (5 MB)
        upload3.setDropLabel(new Span("Arrastra un archivo aquí o haz clic para cargar"));
        uploadContainer3.add(labelPresupuesto, upload3);

        // Notificacion carga exitosa de carga de archivo
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            Notification.show("Archivo cargado: " + fileName);
        });

        // Botón para enviar
        Button guardar = new Button("Enviar", event -> {
            Notification.show("Datos guardados");
        });

        //Scroll
        Div formularioContainer = new Div(labelProyecto, titulo, nombre, uploadContainer, labelSolicitante, solicitante, correo, unidad,
                labelPromotor, promotor, importancia, labelInteresados, interesados, financiacion, labelJustificacion, checkboxGroup,
                datePicker, normativa, labelDocumentacion, uploadContainer2, uploadContainer3,  guardar);
        formularioContainer.setWidth("100%");  // Aseguramos que el contenedor ocupe todo el ancho disponible
        formularioContainer.getStyle().set("margin", "auto").set("padding", "20px");

        //centrar formulario
        formularioContainer.getStyle().set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("max-width", "800px")
                .set("margin", "0 auto");
        add(formularioContainer);

    }
}

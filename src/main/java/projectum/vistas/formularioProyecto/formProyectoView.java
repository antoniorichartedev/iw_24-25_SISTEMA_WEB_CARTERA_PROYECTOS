package projectum.vistas.formularioProyecto;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.entidades.Promotor;
import projectum.data.entidades.Proyecto;
import projectum.data.entidades.Solicitante;
import projectum.data.servicios.PromotorService;
import projectum.data.servicios.ProyectoService;
import projectum.data.servicios.SolicitanteService;
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
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.checkbox.CheckboxGroup;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@PageTitle("Formulario para un nuevo proyecto")
@Route("formularioProyecto")
@Menu(order = 4, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
@RolesAllowed({"USER", "SOLICITANTE"})
public class formProyectoView extends VerticalLayout implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return Rol.USER;
    }

    private SolicitanteService solicitanteService;
    private PromotorService promotorService;
    private ProyectoService proyectoService;

    public formProyectoView(SolicitanteService solicitanteService, PromotorService promotorService, ProyectoService proyectoService) {
        this.solicitanteService = solicitanteService;
        this.promotorService = promotorService;
        this.proyectoService = proyectoService;

        //Informacion del proyecto
        Span labelProyecto = new Span("Información del Proyecto");
        labelProyecto.getStyle().set("font-size", "24px").set("font-weight", "bold");

        // Crear campos
        TextField titulo = new TextField("Titulo del Proyecto");
        titulo.setWidth("70%");
        titulo.setRequiredIndicatorVisible(true);

        TextField nombre = new TextField("Nombre Corto");
        nombre.setWidth("70%");
        nombre.setRequiredIndicatorVisible(true);

        TextField alcanceProyecto = new TextField("Alcance del proyecto");
        alcanceProyecto.setWidth("70%");
        alcanceProyecto.setRequiredIndicatorVisible(true);

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
        solicitante.setRequiredIndicatorVisible(true);

        TextField correoSolicitante = new TextField("Correo del Solicitante");
        correoSolicitante.setWidth("70%");
        correoSolicitante.setRequiredIndicatorVisible(true);

        TextField unidad = new TextField("Unidad del Solicitante");
        unidad.setWidth("70%");
        unidad.setRequiredIndicatorVisible(true);

        //Informacion del promotor
        Span labelPromotor = new Span("Información del Promotor");
        labelPromotor.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField nombrepromotor = new TextField("Nombre del Promotor");
        nombrepromotor.setWidth("70%");
        nombrepromotor.setRequiredIndicatorVisible(true);

        TextField correopromotor = new TextField("Correo del Promotor");
        correopromotor.setWidth("70%");
        correopromotor.setRequiredIndicatorVisible(true);

        IntegerField importancia = new IntegerField("Importancia");
        importancia.setWidth("70%");
        importancia.setMax(10);
        importancia.setMin(1);
        importancia.setRequiredIndicatorVisible(true);

        //Informacion de los interesados
        Span labelInteresados = new Span("Información de los interesados");
        labelInteresados.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField interesados = new TextField("Nombre de los interesados");
        interesados.setWidth("70%");
        interesados.setRequiredIndicatorVisible(true);

        IntegerField financiacion = new IntegerField("Financiación aportada");
        financiacion.setWidth("70%");
        financiacion.setRequiredIndicatorVisible(true);

        //Justificacion del proyecto
        Span labelJustificacion = new Span("Justificación del Proyecto");
        labelJustificacion.getStyle().set("font-size", "24px").set("font-weight", "bold");

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Alineamiento con los objetivos estratégicos:");
        checkboxGroup.setRequiredIndicatorVisible(true);
        checkboxGroup.setItems(
                "Innovar, rediseñar y actualizar nuestra oferta formativa para adaptarla a las necesidades sociales y económicas de nuestro entorno.",
                "Conseguir los niveles más altos de calidad en nuestra oferta formativa propia y reglada.",
                "Aumentar significativamente nuestro posicionamiento en investigación y transferir de forma relevante y útil nuestra investigación a nuestro tejido social y productivo.",
                "Consolidar un modelo de gobierno sostenible y socialmente responsable.",
                "Generar valor compartido con la Comunidad Universitaria.",
                "Reforzar la importancia del papel de la UCA en la sociedad.",
                "Conseguir que la transparencia sea un valor distintivo y relevante en la UCA."
        );

        DatePicker datePicker = new DatePicker("Fecha requerida de puesta en marcha");
        datePicker.setWidth("70%");
        datePicker.setRequiredIndicatorVisible(true);

        // Establecer formato de la fecha
        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat("dd/MM/yyyy"); // Ajusta el formato de fecha aquí
        datePicker.setI18n(i18n);

        TextField normativa = new TextField("Normativa de aplicación");
        normativa.setWidth("70%");
        normativa.setRequiredIndicatorVisible(true);

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
        Upload upload2 = new Upload(buffer2);
        upload2.setAcceptedFileTypes("image/jpeg", "image/png", "application/pdf"); // Tipos permitidos
        upload2.setMaxFiles(1); // Solo permite un archivo
        upload2.setMaxFileSize(100 * 1024 * 1024); // Tamaño máximo (100 MB)
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
        Upload upload3 = new Upload(buffer3);
        upload3.setAcceptedFileTypes("image/jpeg", "image/png", "application/pdf"); // Tipos permitidos
        upload3.setMaxFiles(1); // Solo permite un archivo
        upload3.setMaxFileSize(100 * 1024 * 1024); // Tamaño máximo (100 MB)
        upload3.setDropLabel(new Span("Arrastra un archivo aquí o haz clic para cargar"));
        uploadContainer3.add(labelPresupuesto, upload3);

        // Notificacion carga exitosa de carga de archivo
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            Notification.show("Archivo cargado: " + fileName);
        });

        // Botón para enviar
        Button guardar = new Button("Enviar", event -> {
            // si falta algún campo, sale el aviso.

            if (titulo.isEmpty() || nombre.isEmpty() || solicitante.isEmpty() || correoSolicitante.isEmpty() ||
                    unidad.isEmpty() || nombrepromotor.isEmpty() || correopromotor.isEmpty() ||
                    importancia.isEmpty() || interesados.isEmpty() || financiacion.isEmpty() ||
                    checkboxGroup.getValue().isEmpty() || alcanceProyecto.isEmpty() || datePicker.isEmpty() ||
                    normativa.isEmpty()) {

                Notification.show("Por favor, completa todos los campos obligatorios.", 5000, Notification.Position.MIDDLE);
                return;
            }

            Proyecto proyecto = new Proyecto();

            // Añadimos los parámetros introducidos por el usuario en el Proyecto nuevo que se va a mandar a avalar.
            proyecto.setTitulo(titulo.getValue());
            proyecto.setAcronimo(nombre.getValue());
            proyecto.setJustificacion(String.join(", ", checkboxGroup.getValue()));
            proyecto.setAlcance(alcanceProyecto.getValue());
            proyecto.setImportancia(importancia.getValue());
            proyecto.setFinanciacion(BigDecimal.valueOf(financiacion.getValue()));
            proyecto.setPuestaMarcha(
                    Date.from(datePicker.getValue()
                            .atStartOfDay(ZoneId.of("Europe/Madrid"))
                            .toInstant())
            );
            proyecto.setInteresado(interesados.getValue());

            // Si hay un archivo cargado, debemos ponerlo en el proyecto.
            if (buffer.getInputStream() != null) {
                try {
                    proyecto.setMemorias(buffer.getInputStream().readAllBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Información del solicitante.
            Optional<Solicitante> sol = solicitanteService.findSolicitanteByCorreo(correoSolicitante.getValue());

            // Información del Promotor.
            Optional<Promotor> prom = promotorService.findPromotorByCorreo(correopromotor.getValue());

            // Si tanto como el solicitante como el promotor existen en la base de datos, entonces los asociamos al proyecto.
            if (sol.isPresent() && prom.isPresent()) {
                proyecto.setSolicitante(sol.get());
                proyecto.setPromotor(prom.get());
            }
            else // sino, mostramos el mensaje y no guardamos nada.
            {
                Notification.show("El solicitante o el promotor no existen en la base de datos. Verifica la información ingresada.", 5000, Notification.Position.MIDDLE);
                return;
            }

            // Si no han metido un archivo para las especificaciones...
            if (buffer2.getInputStream() != null) {
                try {
                    proyecto.setEspecificaciones(buffer2.getInputStream().readAllBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // y para los presupuestos...
            if (buffer3.getInputStream() != null) {
                try {
                    proyecto.setPresupuestos(buffer3.getInputStream().readAllBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // Guardamos el proyecto
            proyectoService.saveProyecto(proyecto);

            // Le mostramos la información al usuario de que el proyecto se ha enviado.
            Notification.show("Proyecto enviado con éxito, a la espera de que los superiores lo avalen.");
        });

        //Scroll
        Div formularioContainer = new Div(labelProyecto, titulo, nombre, alcanceProyecto, uploadContainer, labelSolicitante, solicitante, correoSolicitante, unidad,
                labelPromotor, nombrepromotor, correopromotor, importancia, labelInteresados, interesados, financiacion, labelJustificacion, checkboxGroup,
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

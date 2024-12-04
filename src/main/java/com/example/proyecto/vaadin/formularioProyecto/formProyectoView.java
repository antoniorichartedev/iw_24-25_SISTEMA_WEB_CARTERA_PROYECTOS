package com.example.proyecto.vaadin.formularioProyecto;
import com.vaadin.flow.component.button.Button;
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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Div;
@PageTitle("FormularioProyecto")
@Route("formularioProyecto")
@Menu(order = 4, icon = LineAwesomeIconUrl.INFO_CIRCLE_SOLID)
@AnonymousAllowed
public class formProyectoView extends VerticalLayout {
    public formProyectoView() {
        //Informacion del proyecto
        Label labelProyecto = new Label("Información del Proyecto");
        labelProyecto.getStyle().set("font-size", "24px").set("font-weight", "bold");

        // Crear campos
        TextField titulo = new TextField("Titulo del Proyecto");
        titulo.setWidth("70%");

        TextField nombre = new TextField("Nombre Corto");
        nombre.setWidth("70%");

        //añadir archivo
        Div uploadContainer = new Div();
        Label labelMemoria = new Label("Memoria del Proyecto");
        labelMemoria.getStyle()
                .set("font-size", "15px")
                .set("font-weight", "normal")
                .set("margin-left", "0px");  // Establecer margen izquierdo
        MemoryBuffer buffer = new MemoryBuffer(); // Almacén temporal en memoria
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "application/pdf"); // Tipos permitidos
        upload.setMaxFiles(1); // Solo permite un archivo
        upload.setMaxFileSize(5 * 1024 * 1024); // Tamaño máximo (5 MB)
        upload.setDropLabel(new com.vaadin.flow.component.html.Label("Arrastra un archivo aquí o haz clic para cargar"));
        uploadContainer.add(labelMemoria, upload);

        //Informacion del solicitante
        Label labelSolicitante = new Label("Información del Solicitante");
        labelSolicitante.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField solicitante = new TextField("Nombre del Solicitante");
        solicitante.setWidth("70%");

        TextField correo = new TextField("Correo del Solicitante");
        correo.setWidth("70%");

        TextField unidad = new TextField("Unidad del Solicitante");
        unidad.setWidth("70%");

        //Informacion del promotor
        Label labelPromotor = new Label("Información del Promotor");
        labelPromotor.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField promotor = new TextField("Promotor");
        promotor.setWidth("70%");

        IntegerField importancia = new IntegerField("Importancia");
        importancia.setWidth("70%");
        importancia.setMax(10);
        importancia.setMin(1);

        //Informacion de los interesados
        Label labelInteresados = new Label("Información de los interesados");
        labelInteresados.getStyle().set("font-size", "24px").set("font-weight", "bold");

        TextField interesados = new TextField("Nombre de los interesados");
        interesados.setWidth("70%");

        IntegerField financiacion = new IntegerField("Financiación aportada");
        financiacion.setWidth("70%");


        // Notificacion carga exitosa de carga de archivo
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            Notification.show("Archivo cargado: " + fileName);
        });

        // Botón para enviar
        Button guardar = new Button("Guardar", event -> {
            Notification.show("Datos guardados");
        });

        //Scroll
        Div formularioContainer = new Div(labelProyecto, titulo, nombre, uploadContainer, labelSolicitante, solicitante, correo, unidad, labelPromotor, promotor, importancia, labelInteresados, interesados, financiacion, guardar);
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

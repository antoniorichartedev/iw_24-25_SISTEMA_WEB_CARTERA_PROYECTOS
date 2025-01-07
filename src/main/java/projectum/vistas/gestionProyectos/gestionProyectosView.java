package projectum.vistas.gestionProyectos;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.Estado;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.data.entidades.Proyecto;
import projectum.data.servicios.ProyectoService;
import com.vaadin.flow.component.Composite;
import projectum.data.Rol;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.springframework.beans.factory.annotation.Autowired;
import projectum.vistas.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import java.text.SimpleDateFormat;

@PageTitle("Gestionar Proyectos")
@Route(value = "gestionarProyectos", layout = MainLayout.class)
@RolesAllowed("CIO")
public class gestionProyectosView extends Composite<VerticalLayout> implements RoleRestrictedView {
    @Override
    public Rol getRequiredRole() {
        return Rol.ADMIN;
    }

    public gestionProyectosView(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;

        HorizontalLayout layoutRow = new HorizontalLayout();
        H2 h2 = new H2();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Grid<Proyecto> stripedGrid = new Grid<>(Proyecto.class);
        stripedGrid.removeAllColumns();

        // Agregar columnas para todos los campos relevantes de la clase Proyecto
        stripedGrid.addComponentColumn(proyecto -> {
            Span span = new Span(proyecto.getTitulo());
            span.getElement().setAttribute("title", proyecto.getTitulo());
            return span;
        }).setHeader("Título").setSortable(true);

        stripedGrid.addComponentColumn(proyecto -> {
            Span span = new Span(proyecto.getAcronimo());
            span.getElement().setAttribute("title", proyecto.getAcronimo());
            return span;
        }).setHeader("Acrónimo");

        stripedGrid.addComponentColumn(proyecto -> {
            Span span = new Span(proyecto.getJustificacion());
            span.getElement().setAttribute("title", proyecto.getJustificacion());
            return span;
        }).setHeader("Justificación");

        stripedGrid.addComponentColumn(proyecto -> {
            Span span = new Span(proyecto.getAlcance());
            span.getElement().setAttribute("title", proyecto.getAlcance());
            return span;
        }).setHeader("Alcance");

        stripedGrid.addComponentColumn(proyecto -> {
            Span span;
            if(proyecto.getEstado() == Estado.en_desarrollo)
            {
                span = new Span("En desarrollo");
                span.getElement().setAttribute("title", "En desarrollo");
            }
            else if(proyecto.getEstado() == Estado.sin_avalar)
            {
                span = new Span("Sin avalar");
                span.getElement().setAttribute("title", "Sin avalar");
            }
            else if(proyecto.getEstado() == Estado.en_valoracion)
            {
                span = new Span("En valoración");
                span.getElement().setAttribute("title", "En valoración");
            }
            else if(proyecto.getEstado() == Estado.rechazado)
            {
                span = new Span("Rechazado");
                span.getElement().setAttribute("title", "Rechazado");
            }
            else if(proyecto.getEstado() == Estado.valorado)
            {
                span = new Span("Valorado");
                span.getElement().setAttribute("title", "Valorado");
            }
            else if(proyecto.getEstado() == Estado.valoradoCIO)
            {
                span = new Span("Valorado por el CIO");
                span.getElement().setAttribute("title", "Valorado por el CIO");
            }
            else if(proyecto.getEstado() == Estado.valoradoOT)
            {
                span = new Span("Valorado por la OT");
                span.getElement().setAttribute("title", "Valorado por la OT");
            }
            else {
                span = new Span("Completado");
                span.getElement().setAttribute("title", "Completado");
            }
            return span;
        }).setHeader("Estado");

        stripedGrid.addColumn(Proyecto::getImportancia).setHeader("Importancia");

        stripedGrid.addColumn(Proyecto::getFinanciacion).setHeader("Financiación");

        stripedGrid.addColumn(Proyecto::getPriorizacion).setHeader("Priorización");

        stripedGrid.addColumn(proyecto -> {
            // Formatear la fecha si está presente
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return proyecto.getPuestaMarcha() != null ? formatter.format(proyecto.getPuestaMarcha()) : "Sin fecha";
        }).setHeader("Puesta en marcha");

        stripedGrid.addComponentColumn(proyecto -> {
            String interesado = proyecto.getInteresado()!= null ? proyecto.getInteresado().toString() : "Sin interesado";
            Span span = new Span(interesado);
            span.getElement().setAttribute("title", interesado);
            return span;
        }).setHeader("Interesado");

        stripedGrid.addComponentColumn(proyecto -> {
            String solicitante = proyecto.getSolicitante() != null ? proyecto.getSolicitante().getNombre() : "Sin solicitante";
            Span span = new Span(solicitante);
            span.getElement().setAttribute("title", solicitante);
            return span;
        }).setHeader("Solicitante");

        stripedGrid.addComponentColumn(proyecto -> {
            String promotor = proyecto.getPromotor() != null ? proyecto.getPromotor().getNombre() : "Sin promotor";
            Span span = new Span(promotor);
            span.getElement().setAttribute("title", promotor);
            return span;
        }).setHeader("Promotor");

        // Columna para el botón de "Editar"
        stripedGrid.addComponentColumn(proyecto -> {
            Button editarProyecto = new Button("Editar");
            editarProyecto.getStyle().set("color", "blue");

            // Manejar el evento de clic para redirigir a otra vista
            editarProyecto.addClickListener(event -> {
                // Redirigir a la vista de edición pasando el ID del proyecto
                UI.getCurrent().navigate("editarProyecto/" + proyecto.getId());
            });

            return editarProyecto;
        }).setHeader("Editar").setAutoWidth(true);

        // Columna para el botón de "Eliminar"
        stripedGrid.addComponentColumn(proyecto -> {
            Button borrarProyecto = new Button("Eliminar");
            borrarProyecto.getStyle().set("color", "red");

            // Crear el diálogo de confirmación
            Dialog confirmDialog = new Dialog();
            confirmDialog.setHeaderTitle("Confirmación");
            confirmDialog.add(new Text("¿Estás seguro de que deseas eliminar este proyecto? Esta acción no se puede deshacer."));

            // Botón para confirmar la eliminación
            Button confirmButton = new Button("Confirmar", event -> {
                proyectoService.deleteProyecto(proyecto.getId());
                stripedGrid.setItems(proyectoService.getAllProyectos());
                Notification.show("El proyecto ha sido eliminado con éxito");
                confirmDialog.close();
            });

            // Botón para cancelar la acción
            Button cancelButton = new Button("Cancelar", event -> confirmDialog.close());

            HorizontalLayout dialogButtons = new HorizontalLayout(confirmButton, cancelButton);
            dialogButtons.setSpacing(true);
            confirmDialog.add(dialogButtons);

            borrarProyecto.addClickListener(event -> confirmDialog.open());

            return borrarProyecto;
        }).setHeader("Eliminar");



        // Configuración del diseño
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        h2.setText("Proyectos");
        h2.setWidth("max-content");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        stripedGrid.setWidth("100%");
        stripedGrid.getStyle().set("flex-grow", "0");

        // Establecer los datos en el grid
        setGridSampleData(stripedGrid);

        // Añadir componentes al layout
        getContent().add(layoutRow);
        layoutRow.add(h2);
        getContent().add(layoutColumn2);
        layoutColumn2.add(stripedGrid);
    }

    private void setGridSampleData(Grid<Proyecto> grid) {
        grid.setItems(proyectoService.getAllProyectos());
    }

    @Autowired
    private ProyectoService proyectoService;
}

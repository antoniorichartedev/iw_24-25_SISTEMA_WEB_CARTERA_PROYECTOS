package projectum.vistas.proyectos;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.Estado;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.data.entidades.Proyecto;
import projectum.data.servicios.ProyectoService;
import projectum.data.Rol;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import com.vaadin.flow.component.html.Span;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Proyectos")
@Route("proyectos")
@Menu(order = 1, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Uses(Icon.class)
@RolesAllowed({"USER", "SOLICITANTE"})
public class ProyectosView extends Composite<VerticalLayout> implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return null;
    }

    public ProyectosView(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;

        HorizontalLayout layoutRow = new HorizontalLayout();
        H2 h2 = new H2();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Grid<Proyecto> stripedGrid = new Grid<>(Proyecto.class);
        stripedGrid.removeAllColumns();

        // Agregar columnas para todos los campos de la clase Proyecto
        // Mostrar string completo al poner el raton encima
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
            else {
                span = new Span("Completado");
                span.getElement().setAttribute("title", "Completado");
            }
            return span;
        }).setHeader("Estado");

        stripedGrid.addColumn(proyecto -> {
            // Convertir bytes a base64 si están presentes
            byte[] memorias = proyecto.getMemorias();
            return memorias != null ? Base64.getEncoder().encodeToString(memorias) : "Sin datos";
        }).setHeader("Memorias");

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

        List<Proyecto> proyectosEnDesarrollo = proyectoService.getAllProyectos().stream()
                .filter(proyecto -> proyecto.getEstado() == Estado.en_desarrollo)
                .collect(Collectors.toList());

        grid.setItems(proyectosEnDesarrollo);
    }

    @Autowired()
    private ProyectoService proyectoService;
}
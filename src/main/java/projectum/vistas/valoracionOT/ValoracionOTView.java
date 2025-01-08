package projectum.vistas.valoracionOT;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import projectum.data.Estado;
import projectum.data.Rol;
import projectum.data.entidades.Proyecto;
import projectum.data.servicios.ProyectoService;
import projectum.security.RolRestrictions.RoleRestrictedView;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Valorar proyectos")
@Route("valorarProyectoOT")
@Menu(order = 1, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Uses(Icon.class)
@RolesAllowed("OT")
public class ValoracionOTView extends Composite<VerticalLayout> implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return null;
    }

    public ValoracionOTView(ProyectoService proyectoService){
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

        stripedGrid.addComponentColumn(proyecto -> {
            Button valorarProyecto = new Button("Valorar");
            valorarProyecto.getStyle().set("color", "blue");

            valorarProyecto.addClickListener(event -> {
                UI.getCurrent().navigate("formOT");
            });
            return valorarProyecto;
        }).setHeader("Valorar").setAutoWidth(true);

        // Configuración del diseño
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
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

        List<Proyecto> proyectosFiltrados = proyectoService.getAllProyectos().stream()
                .filter(proyecto -> proyecto.getEstado() == Estado.en_valoracion || proyecto.getEstado() == Estado.valoradoCIO)
                .collect(Collectors.toList());

        grid.setItems(proyectosFiltrados);
    }

    @Autowired()
    private ProyectoService proyectoService;
}

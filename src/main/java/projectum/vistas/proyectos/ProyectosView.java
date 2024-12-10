package projectum.vistas.proyectos;

import com.vaadin.flow.server.auth.AnonymousAllowed;
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

import java.text.SimpleDateFormat;
import java.util.Base64;

@PageTitle("Proyectos")
@Route("proyectos")
@Menu(order = 1, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Uses(Icon.class)
@AnonymousAllowed
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
        stripedGrid.addColumn(Proyecto::getTitulo).setHeader("Título").setSortable(true);
        stripedGrid.addColumn(Proyecto::getAcronimo).setHeader("Acrónimo");
        stripedGrid.addColumn(Proyecto::getJustificacion).setHeader("Justificación");
        stripedGrid.addColumn(Proyecto::getAlcance).setHeader("Alcance");
        stripedGrid.addColumn(proyecto -> {
            // Convertir bytes a base64 si están presentes
            byte[] memorias = proyecto.getMemorias();
            return memorias != null ? Base64.getEncoder().encodeToString(memorias) : "Sin datos";
        }).setHeader("Memorias");
        stripedGrid.addColumn(Proyecto::getImportancia).setHeader("Importancia");
        stripedGrid.addColumn(Proyecto::getFinanciacion).setHeader("Financiación");
        stripedGrid.addColumn(proyecto -> {
            // Formatear la fecha si está presente
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return proyecto.getPuestaMarcha() != null ? formatter.format(proyecto.getPuestaMarcha()) : "Sin fecha";
        }).setHeader("Puesta en marcha");
        stripedGrid.addColumn(Proyecto::getInteresado).setHeader("Interesado");
        stripedGrid.addColumn(proyecto ->
                proyecto.getSolicitante() != null ? proyecto.getSolicitante().toString() : "Sin solicitante"
        ).setHeader("Solicitante");
        stripedGrid.addColumn(proyecto ->
                proyecto.getPromotor() != null ? proyecto.getPromotor().toString() : "Sin promotor"
        ).setHeader("Promotor");

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

    @Autowired()
    private ProyectoService proyectoService;
}

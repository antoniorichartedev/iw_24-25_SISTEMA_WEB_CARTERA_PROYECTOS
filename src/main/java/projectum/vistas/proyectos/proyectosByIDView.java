package projectum.vistas.proyectos;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.servicios.UsuarioService;

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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.UUID;
import java.util.Optional;

@PageTitle("Tus proyectos")
@Route("proyectosById")
@Menu(order = 1, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Uses(Icon.class)
@AnonymousAllowed
public class proyectosByIDView extends Composite<VerticalLayout> implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return Rol.USER; // Cambia según los roles permitidos para esta vista
    }

    private final AuthenticationContext authenticationContext;
    private final UsuarioService usuarioService;
    public proyectosByIDView(ProyectoService proyectoService, AuthenticationContext authenticationContext, UsuarioService usuarioService) {
        this.proyectoService = proyectoService;
        this.authenticationContext = authenticationContext;
        this.usuarioService = usuarioService;

        // Obtener el ID del solicitante autenticado
        UUID solicitanteId = getSolicitanteIdFromSession();

        HorizontalLayout layoutRow = new HorizontalLayout();
        H2 h2 = new H2("Proyectos");
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Grid<Proyecto> stripedGrid = new Grid<>(Proyecto.class);
        stripedGrid.removeAllColumns();

        // Configurar columnas
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

        stripedGrid.addColumn(proyecto -> {
            byte[] memorias = proyecto.getMemorias();
            return memorias != null ? Base64.getEncoder().encodeToString(memorias) : "Sin datos";
        }).setHeader("Memorias");

        stripedGrid.addColumn(Proyecto::getImportancia).setHeader("Importancia");
        stripedGrid.addColumn(Proyecto::getFinanciacion).setHeader("Financiación");

        stripedGrid.addColumn(proyecto -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return proyecto.getPuestaMarcha() != null ? formatter.format(proyecto.getPuestaMarcha()) : "Sin fecha";
        }).setHeader("Puesta en marcha");

        stripedGrid.addComponentColumn(proyecto -> {
            String interesado = proyecto.getInteresado() != null ? proyecto.getInteresado().toString() : "Sin interesado";
            Span span = new Span(interesado);
            span.getElement().setAttribute("title", interesado);
            return span;
        }).setHeader("Interesado");

        stripedGrid.addComponentColumn(proyecto -> {
            String solicitante = proyecto.getSolicitante() != null ? proyecto.getSolicitante().toString() : "Sin solicitante";
            Span span = new Span(solicitante);
            span.getElement().setAttribute("title", solicitante);
            return span;
        }).setHeader("Solicitante");

        stripedGrid.addComponentColumn(proyecto -> {
            String promotor = proyecto.getPromotor() != null ? proyecto.getPromotor().toString() : "Sin promotor";
            Span span = new Span(promotor);
            span.getElement().setAttribute("title", promotor);
            return span;
        }).setHeader("Promotor");

        // Configurar diseño
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        stripedGrid.setWidth("100%");

        // Establecer los datos en el grid
        stripedGrid.setItems(proyectoService.getProyectosBySolicitante(solicitanteId));

        // Añadir componentes al layout
        getContent().add(layoutRow);
        layoutRow.add(h2);
        getContent().add(layoutColumn2);
        layoutColumn2.add(stripedGrid);
    }

    public UUID getSolicitanteIdFromSession() {
        return authenticationContext.getAuthenticatedUser(Object.class)
                .map(principal -> {
                    if (principal instanceof UserDetails) {
                        String username = ((UserDetails) principal).getUsername();
                        // Obtener el usuario como un Optional
                        Optional<projectum.data.entidades.Usuario> usuarioOpt = usuarioService.loadUsuarioByCorreo(username);

                        // Si el usuario existe, extraemos el ID
                        return usuarioOpt.map(projectum.data.entidades.Usuario::getId)
                                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado en la base de datos."));
                    }
                    throw new IllegalStateException("El usuario autenticado no tiene un ID válido.");
                })
                .orElseThrow(() -> new IllegalStateException("No hay un usuario autenticado."));
    }

    @Autowired
    private ProyectoService proyectoService;
}

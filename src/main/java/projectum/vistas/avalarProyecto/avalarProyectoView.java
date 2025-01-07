package projectum.vistas.avalarProyecto;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import projectum.data.Estado;
import projectum.data.Rol;
import projectum.data.entidades.Proyecto;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.CorreoRealService;
import projectum.data.servicios.ProyectoService;
import projectum.data.servicios.UsuarioService;
import projectum.security.RolRestrictions.RoleRestrictedView;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@PageTitle("Tus proyectos asignados")
@Route("avalarProyecto")
@Menu(order = 1, icon = LineAwesomeIconUrl.PENCIL_RULER_SOLID)
@Uses(Icon.class)
@RolesAllowed("PROMOTOR")
public class avalarProyectoView extends Composite<VerticalLayout> implements RoleRestrictedView {
    @Override
    public Rol getRequiredRole() {
        return null;
    }

    public avalarProyectoView(ProyectoService proyectoService,
                              AuthenticationContext authenticationContext,
                              UsuarioService usuarioService,
                              CorreoRealService correoService) {
        this.proyectoService = proyectoService;
        this.authenticationContext = authenticationContext;
        this.usuarioService = usuarioService;
        this.correoService = correoService;

        UUID solicitanteId = getSolicitanteIdFromSession();
        Usuario promotorActual = usuarioService.loadUserById(solicitanteId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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

        stripedGrid.addComponentColumn(proyecto -> {
            Button avalarProyecto = new Button("Avalar");
            avalarProyecto.getStyle().set("color", "blue");

            // Manejar el evento de clic para redirigir a otra vista
            avalarProyecto.addClickListener(event -> {
                proyecto.setEstado(Estado.en_valoracion);
                proyectoService.saveProyecto(proyecto);
                Notification.show("Estado actualizado", 3000, Notification.Position.MIDDLE);
                correoService.enviarCorreoAvalado(proyecto.getSolicitante(), proyecto);
                UI.getCurrent().getPage().reload();
            });

            return avalarProyecto;
        }).setHeader("Avalar").setAutoWidth(true);

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
        setGridSampleData(stripedGrid, promotorActual);

        // Añadir componentes al layout
        getContent().add(layoutRow);
        layoutRow.add(h2);
        getContent().add(layoutColumn2);
        layoutColumn2.add(stripedGrid);
    }

    private void setGridSampleData(Grid<Proyecto> grid, Usuario promotorActual) {

        List<Proyecto> proyectosEnDesarrollo = proyectoService.getAllProyectos().stream()
                .filter(proyecto -> proyecto.getEstado() == Estado.sin_avalar)
                .filter(proyecto -> proyecto.getPromotor().getNombre().equals(promotorActual.getNombre()))
                .collect(Collectors.toList());

        grid.setItems(proyectosEnDesarrollo);
    }

    private UUID getSolicitanteIdFromSession() {
        return authenticationContext.getAuthenticatedUser(Object.class)
                .map(principal -> {
                    if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
                        String username = userDetails.getUsername();
                        Usuario usuario = usuarioService.loadUserByUsername(username);
                        if (usuario != null) {
                            return usuario.getId();

                        } else {
                            throw new IllegalStateException("Usuario no encontrado en la base de datos.");
                        }
                    }
                    throw new IllegalStateException("El usuario autenticado no tiene un ID válido.");
                })
                .orElseThrow(() -> new IllegalStateException("No hay un usuario autenticado."));
    }

    @Autowired()
    private ProyectoService proyectoService;
    private AuthenticationContext authenticationContext;
    private UsuarioService usuarioService;
    private CorreoRealService correoService;
}

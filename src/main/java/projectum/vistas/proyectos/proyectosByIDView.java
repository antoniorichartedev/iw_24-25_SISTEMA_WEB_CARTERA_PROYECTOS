package projectum.vistas.proyectos;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import projectum.data.entidades.Proyecto;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.ProyectoService;
import projectum.data.servicios.UsuarioService;
import com.vaadin.flow.spring.security.AuthenticationContext;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PageTitle("Tus proyectos")
@Route("proyectosById")
@RolesAllowed({"USER", "SOLICITANTE"})
public class proyectosByIDView extends Composite<VerticalLayout> {

    private final ProyectoService proyectoService;
    private final AuthenticationContext authenticationContext;
    private final UsuarioService usuarioService;

    @Autowired
    public proyectosByIDView(ProyectoService proyectoService,
                             AuthenticationContext authenticationContext,
                             UsuarioService usuarioService) {
        this.proyectoService = proyectoService;
        this.authenticationContext = authenticationContext;
        this.usuarioService = usuarioService;

        VerticalLayout content = getContent();
        content.setWidthFull();
        content.setSpacing(true);

        // Obtener proyectos del usuario autenticado
        UUID solicitanteId = getSolicitanteIdFromSession();
        List<Proyecto> proyectos = proyectoService.getProyectosBySolicitante(solicitanteId);

        // Comprobar si hay proyectos
        if (proyectos.isEmpty()) {
            content.add(new H2("No tienes proyectos asociados."));
            return;
        }

        // Crear grid para mostrar los proyectos
        Grid<Proyecto> proyectosGrid = createProyectoGrid();
        proyectosGrid.setItems(proyectos);

        content.add(proyectosGrid);
    }

    private Grid<Proyecto> createProyectoGrid() {
        Grid<Proyecto> grid = new Grid<>(Proyecto.class, false);
        grid.addColumn(Proyecto::getTitulo).setHeader("Título").setSortable(true);
        grid.addColumn(Proyecto::getAcronimo).setHeader("Acrónimo");
        grid.addColumn(Proyecto::getJustificacion).setHeader("Justificación");
        grid.addColumn(Proyecto::getAlcance).setHeader("Alcance");
        grid.addColumn(Proyecto::getPriorizacion).setHeader("Priorización");
        grid.addColumn(proyecto -> proyecto.getEstado().toString()).setHeader("Estado");
        grid.addColumn(proyecto -> proyecto.getMemorias() != null
                ? "Tiene memorias" : "Sin datos").setHeader("Memorias");
        grid.addColumn(Proyecto::getImportancia).setHeader("Importancia");
        grid.addColumn(Proyecto::getFinanciacion).setHeader("Financiación");
        grid.addColumn(proyecto -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return proyecto.getPuestaMarcha() != null
                    ? formatter.format(proyecto.getPuestaMarcha()) : "Sin fecha";
        }).setHeader("Puesta en marcha");
        grid.addColumn(proyecto -> Optional.ofNullable(proyecto.getInteresado())
                .map(Object::toString).orElse("Sin interesado")).setHeader("Interesado");
        grid.addColumn(proyecto -> Optional.ofNullable(proyecto.getSolicitante().getNombre())
                .map(Object::toString).orElse("Sin solicitante")).setHeader("Solicitante");
        grid.addColumn(proyecto -> Optional.ofNullable(proyecto.getPromotor().getNombre())
                .map(Object::toString).orElse("Sin promotor")).setHeader("Promotor");

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setWidthFull();
        return grid;
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
}

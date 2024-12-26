package projectum.vistas.adminUsers;

import jakarta.annotation.security.RolesAllowed;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;
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

@PageTitle("Administrar Usuarios")
@Route(value = "adminUsers", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class adminUsersView extends Composite<VerticalLayout> implements RoleRestrictedView {
    @Override
    public Rol getRequiredRole() {
        return Rol.ADMIN;
    }

    public adminUsersView (UsuarioService usuarioService){
        this.usuarioService = usuarioService;

        HorizontalLayout layoutRow = new HorizontalLayout();
        H2 h2 = new H2();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Grid<Usuario> stripedGrid = new Grid<>(Usuario.class);
        stripedGrid.removeAllColumns();

        // Agregar columnas para todos los campos relevantes de la clase Usuario
        stripedGrid.addColumn(Usuario::getNombre).setHeader("Nombre").setSortable(true);
        stripedGrid.addColumn(Usuario::getUsername).setHeader("Nombre de usuario").setSortable(true);
        stripedGrid.addColumn(Usuario::getCorreo).setHeader("Correo").setSortable(true);
        stripedGrid.addColumn(usuario -> usuario.getRol().toString()).setHeader("Rol");
        stripedGrid.addColumn(usuario -> usuario.getEstado() ? "Activo" : "Inactivo").setHeader("Estado");

        // Configuración del diseño
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        h2.setText("Usuarios");
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
    private void setGridSampleData(Grid<Usuario> grid) {
        grid.setItems(usuarioService.getAllUsuarios());
    }

    @Autowired()
    private UsuarioService usuarioService;
}

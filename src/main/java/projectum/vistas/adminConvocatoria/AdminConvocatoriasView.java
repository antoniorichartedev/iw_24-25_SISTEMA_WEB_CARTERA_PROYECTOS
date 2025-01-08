package projectum.vistas.adminConvocatoria;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import projectum.data.Rol;
import projectum.data.entidades.Convocatoria;
import projectum.data.servicios.ConvocatoriaService;
import projectum.security.RolRestrictions.RoleRestrictedView;
import projectum.vistas.MainLayout;

@PageTitle("Administrar Convocatorias")
@Route(value = "adminConvocatorias", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminConvocatoriasView extends Composite<VerticalLayout> implements RoleRestrictedView {

    @Override
    public Rol getRequiredRole() {
        return Rol.ADMIN;
    }

    private final ConvocatoriaService convocatoriaService;

    private void setGridSampleData(Grid<Convocatoria> grid) {
        grid.setItems(convocatoriaService.getAllConvocatorias());
    }


    public AdminConvocatoriasView(ConvocatoriaService convocatoriService) {
        this.convocatoriaService = convocatoriService;

        HorizontalLayout layoutRow = new HorizontalLayout();
        H2 h2 = new H2();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Grid<Convocatoria> stripedGrid = new Grid<>(Convocatoria.class);
        stripedGrid.removeAllColumns();

        // Agregar columnas para todos los campos relevantes de la clase Convocatoria
        stripedGrid.addColumn(Convocatoria::getNombre).setHeader("Nombre").setSortable(true);
        stripedGrid.addColumn(Convocatoria::getFechaInico).setHeader("Fecha Inicio").setSortable(true);
        stripedGrid.addColumn(Convocatoria::getFechaFin).setHeader("Fecha Fin").setSortable(true);
        stripedGrid.addColumn(convocatoria -> convocatoria.getActividad() ? "En Curso" : "Inactiva").setHeader("Estado");

        stripedGrid.addComponentColumn(convocatoria -> {
            Button editarConvocatoria = new Button("Editar");
            editarConvocatoria.getStyle().set("color", "blue");

            // Manejar el evento de clic para redirigir a otra vista
            editarConvocatoria.addClickListener(event -> {
                // Redirigir a la vista de edición pasando el ID del proyecto
                UI.getCurrent().navigate("editarConvocatoria/" + convocatoria.getId());
            });

            return editarConvocatoria;
        }).setHeader("Editar").setAutoWidth(true);


        // Agregar columna para el botón de eliminar
        stripedGrid.addComponentColumn(convocatoria -> {
            Button borrarConvocatoria = new Button("Eliminar");
            borrarConvocatoria.getStyle().set("color", "red");

            // Crear el diálogo de confirmación
            Dialog confirmDialog = new Dialog();
            confirmDialog.setHeaderTitle("Confirmación");
            confirmDialog.add(new Text("¿Estás seguro de que deseas eliminar esta convocatoria? Esta acción no se puede deshacer."));

            // Botón para confirmar la eliminación
            Button confirmButton = new Button("Confirmar", event -> {
                convocatoriaService.delete(convocatoria.getId());
                stripedGrid.setItems(convocatoriaService.getAllConvocatorias());
                Notification.show("La convocatoria ha sido eliminada con éxito");
                confirmDialog.close();
            });

            // Botón para cancelar la acción
            Button cancelButton = new Button("Cancelar", event -> confirmDialog.close());

            HorizontalLayout dialogButtons = new HorizontalLayout(confirmButton, cancelButton);
            dialogButtons.setSpacing(true);
            confirmDialog.add(dialogButtons);

            borrarConvocatoria.addClickListener(event -> confirmDialog.open());

            return borrarConvocatoria;
        }).setHeader("Eliminar");

        // Configuración del diseño
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        h2.setText("Convocatorias");
        h2.setWidth("max-content");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        stripedGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        stripedGrid.setWidth("100%");
        stripedGrid.getStyle().set("flex-grow", "0");

        // Establecer los datos en el grid
        setGridSampleData(stripedGrid);


        Button button = new Button("Añadir convocatorias");
        button.addClickListener(e -> {
            UI.getCurrent().navigate("addConvocatorias");
        });

        button.getElement().addEventListener("mouseover", e -> {
            button.getElement().getStyle()
                    .set("background-color", "#FF8000")
                    .set("color", "white");
        });
        button.getElement().addEventListener("mouseout", e -> {
            button.getElement().getStyle()
                    .remove("background-color")
                    .remove("color");
        });

        // Añadir componentes al layout
        getContent().add(layoutRow);
        layoutRow.add(h2);
        getContent().add(layoutColumn2);
        layoutColumn2.add(stripedGrid);
        getContent().add(button);
    }

}

package projectum.vistas.userProfile;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import projectum.data.entidades.Usuario;
import projectum.security.login.AuthenticatedUser;
import projectum.data.servicios.UsuarioService;
import projectum.vistas.MainLayout;

@PageTitle("Perfil")
@Route(value = "perfil", layout = MainLayout.class)
@PermitAll // Solo para usuarios autenticados.
public class userProfileView extends VerticalLayout {
    private final UsuarioService usuarioService;
    private final AuthenticatedUser authenticatedUser;
    private Usuario usuario;

    public userProfileView(UsuarioService usuarioService, AuthenticatedUser authenticatedUser) {
        this.usuarioService = usuarioService;
        this.authenticatedUser = authenticatedUser;

        // Cargamos al usuario que está autenticado ahora mismo.
        authenticatedUser.get().ifPresent(user -> this.usuario = user);

        // Pero, si resulta ser que el usuario no está autenticado, lo redirigimos al login.
        if (usuario == null) {
            UI.getCurrent().navigate("login");
            return;
        }

        // Título de la vista.
        H1 titulo = new H1("Tu perfil");
        add(titulo);

        FormLayout form = new FormLayout();

        // Parámetros que podemos editar.
        TextField nombreUsuario = new TextField("Nombre de usuario");
        nombreUsuario.setValue(usuario.getUsername());
        nombreUsuario.setRequired(true);

        TextField nombre = new TextField("Nombre");
        nombre.setValue(usuario.getNombre());
        nombre.setRequired(true);

        PasswordField contrasenna = new PasswordField("Contraseña");
        contrasenna.setPlaceholder("Nueva contraseña (opcional)");

        PasswordField confirmarContrasenna = new PasswordField("Confirmar contraseña");
        confirmarContrasenna.setPlaceholder("Confirmar contraseña (Opcional)");
        form.add(nombreUsuario, nombre, contrasenna, confirmarContrasenna);

        // Botón que nos servirá para guardar los cambios.
        Button guardarCambios = new Button("Guardar cambios", event -> {
            usuario.setUsername(nombreUsuario.getValue());
            usuario.setNombre(nombre.getValue());

            // Si el campo tiene una nueva contraseña añadida, la cambiamos
            if(!contrasenna.getValue().isEmpty() && !confirmarContrasenna.getValue().isEmpty()) {
                if(contrasenna.getValue().equals(confirmarContrasenna.getValue())) {
                    usuario.setPassword(contrasenna.getValue());
                }
            }

            usuarioService.updateUsuario(usuario);
            Notification.show("Cambios realizados correctamente.");
        });

        // Botón que nos servirá para borrar la contraseña.
        Button borrarUsuario = new Button("Borrar usuario", event -> {
            usuarioService.delete(usuario.getId());
            authenticatedUser.logout();
            Notification.show("Tu cuenta ha sido eliminada con éxito");
        });

        // Le ponemos un color rojo al botón.
        borrarUsuario.getStyle().set("color", "red");

        add(form, guardarCambios, borrarUsuario);
    }
}

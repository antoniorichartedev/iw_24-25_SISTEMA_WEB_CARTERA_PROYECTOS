package projectum.vistas.olvidasteContrasena;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.CorreoRealService;
import projectum.data.servicios.CorreoService;
import projectum.data.servicios.UsuarioService;

import java.util.Optional;

@Route("recuperar-contrasena")
@PageTitle("Recuperar Contraseña")
@AnonymousAllowed
public class RecuperarContrasenaView extends VerticalLayout {

    private final UsuarioService usuarioService;
    private final CorreoRealService correoService;

    public RecuperarContrasenaView(UsuarioService userService, CorreoRealService emailService) {
        this.usuarioService = userService;
        this.correoService = emailService;

        // Configurar el diseño
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Crear componentes
        TextField correoField = new TextField("Correo electrónico");
        Button enviarCodigoButton = new Button("Enviar código");

        PasswordField codigoField = new PasswordField("Código de recuperación");
        PasswordField nuevaContrasenaField = new PasswordField("Nueva contraseña");
        PasswordField confirmarContrasenaField = new PasswordField("Confirmar contraseña");
        Button cambiarContrasenaButton = new Button("Cambiar contraseña");

        // Configuración inicial
        codigoField.setVisible(false);
        nuevaContrasenaField.setVisible(false);
        confirmarContrasenaField.setVisible(false);
        cambiarContrasenaButton.setVisible(false);

        // Lógica del botón para enviar el código
        enviarCodigoButton.addClickListener(event -> {
            String correo = correoField.getValue().trim();
            if (correo.isEmpty()) {
                Notification.show("Por favor, ingresa un correo electrónico válido.", 3000, Notification.Position.TOP_CENTER);
                return;
            }

            Optional<Usuario> usuarioOpt = usuarioService.loadUserByCorreo(correo);
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                if (correoService.enviarCorreoRecuperacion(usuario)) {
                    Notification.show("Código de recuperación enviado. Revisa tu correo.", 3000, Notification.Position.TOP_CENTER);
                    codigoField.setVisible(true);
                    nuevaContrasenaField.setVisible(true);
                    confirmarContrasenaField.setVisible(true);
                    cambiarContrasenaButton.setVisible(true);
                } else {
                    Notification.show("No se pudo enviar el correo de recuperación. Inténtalo más tarde.", 3000, Notification.Position.TOP_CENTER);
                }
            } else {
                Notification.show("No existe un usuario con ese correo.", 3000, Notification.Position.TOP_CENTER);
            }
        });

        // Lógica del botón para cambiar la contraseña
        cambiarContrasenaButton.addClickListener(event -> {
            String codigo = codigoField.getValue().trim();
            String nuevaContrasena = nuevaContrasenaField.getValue().trim();
            String confirmarContrasena = confirmarContrasenaField.getValue().trim();

            if (codigo.isEmpty() || nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                Notification.show("Por favor, completa todos los campos.", 3000, Notification.Position.TOP_CENTER);
                return;
            }

            if (!nuevaContrasena.equals(confirmarContrasena)) {
                Notification.show("Las contraseñas no coinciden.", 3000, Notification.Position.TOP_CENTER);
                return;
            }

            if (usuarioService.validarCodigoRecuperacion(correoField.getValue(), codigo)) {
                usuarioService.updateContrasena(correoField.getValue(), nuevaContrasena);
                Notification.show("Contraseña cambiada exitosamente. Ahora puedes iniciar sesión.", 3000, Notification.Position.TOP_CENTER);
                UI.getCurrent().navigate("login");
            } else {
                Notification.show("El código de recuperación es inválido o ha expirado.", 3000, Notification.Position.TOP_CENTER);
            }
        });

        // Añadir componentes al diseño
        add(new H1("Recuperar Contraseña"), correoField, enviarCodigoButton, codigoField, nuevaContrasenaField, confirmarContrasenaField, cambiarContrasenaButton);
    }
}

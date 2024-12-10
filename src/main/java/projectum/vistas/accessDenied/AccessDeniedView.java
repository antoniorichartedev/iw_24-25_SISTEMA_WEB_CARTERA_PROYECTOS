package projectum.vistas.accessDenied;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("access-denied")
public class AccessDeniedView extends Div {
    public AccessDeniedView() {
        setText("Acceso denegado. No tienes permiso para ver esta página.");
    }
}
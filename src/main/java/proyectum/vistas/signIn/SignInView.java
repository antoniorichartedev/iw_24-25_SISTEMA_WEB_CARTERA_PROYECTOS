package proyectum.vistas.signIn;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Registrarse")
@Route("sign-in")
@AnonymousAllowed
public class SignInView extends Composite<VerticalLayout> {
    public SignInView(){

    }

}
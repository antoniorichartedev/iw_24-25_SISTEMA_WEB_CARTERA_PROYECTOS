package vaadin.src;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        Button button = new Button("Haz clic aquí",
                e -> Notification.show("¡Hola, Vaadin y Spring Boot!"));

        add(button);
    }
}

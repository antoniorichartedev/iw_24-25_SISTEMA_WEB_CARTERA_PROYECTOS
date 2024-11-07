package src.data.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route("")
public class Home extends VerticalLayout {

    public Home() {
        Button button = new Button("Click me",
                event -> Notification.show("¡Hola desde Vaadin y Spring Boot!"));
        add(button);
    }
}

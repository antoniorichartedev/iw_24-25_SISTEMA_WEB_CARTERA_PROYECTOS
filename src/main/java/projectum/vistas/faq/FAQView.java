package projectum.vistas.faq;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.*;

@Route("faq")
@PageTitle("Preguntas frecuentes")
@AnonymousAllowed
public class FAQView extends VerticalLayout {

    public FAQView() {
        // Configuración general de la vista
        setSpacing(true);
        setPadding(true);
        setWidthFull();
        setAlignItems(Alignment.CENTER);

        // Título principal
        H1 title = new H1("Preguntas Frecuentes");
        title.getStyle()
                .set("text-align", "center")
                .set("margin-bottom", "20px")
                .set("color", "#333")
                .set("font-size", "2.5rem");

        add(title);

        // Preguntas frecuentes
        addFAQ("¿Cómo puedo recuperar mi contraseña?",
                "Puedes hacer clic en el enlace '¿Olvidaste tu contraseña?' en la página de inicio de sesión para restablecerla.");
        addFAQ("¿Cómo puedo crear un proyecto?",
                "Ve a la sección 'Proyectos' y haz clic en el botón 'Crear Proyecto'. Llena el formulario y guarda los cambios.");
        addFAQ("¿Cómo puedo saber si el proyecto que he solicitado para su desarrollo, se ha enviado o no?",
                "Recibirás un correo con la notificación correspondiente a la solicitud de tu proyecto.");
        addFAQ("¿Dónde puedo ver mis proyectos?",
                "Puedes ver tus proyectos dándole clic a 'Proyectos de tu usuario'.");
        addFAQ("Si necesito cambiar algún dato de mi perfil, ¿dónde puedo hacerlo?",
                "Puedes cambiar tus datos en el menú 'Perfil'. ¡Recuerda que no se verán reflejados los cambios que hagas hasta que no vuelvas a iniciar sesión!");
        addFAQ("¿Cuándo puedo saber que mi proyecto se llevará a cabo?",
                "Cuando el CIO y la Oficina Técnica valoren tu proyecto económica y técnicamente hablando, en el estado del proyecto se verá si se lleva a cabo o te lo están valorando todavía.");
        addFAQ("¿Qué quieren decir las puntuaciones que tanto el CIO y la OT le han dado a mi proyecto?",
                "Ambas puntuaciones son calculadas de los informes que tienen cada uno para evaluar tu proyecto, de 0 a 100. " +
                        "Cuanta más puntuación tengas, más viable es tu proyecto y más posibilidades tendrás para que se lleve a cabo.");

        // Párrafo con enlace a "Sobre Nosotros"
        Paragraph correoDudas = new Paragraph();
        correoDudas.getElement().setProperty("innerHTML",
                "Si resulta ser que estas preguntas no resuelven tus dudas, no dudes en contactarnos en las diferentes formas de comunicación que tenemos en <a href='/about-us' style='color: #007BFF; text-decoration: none;'>Sobre Nosotros</a>. ¡Estaremos encantados de ayudarte!");
        correoDudas.getStyle()
                .set("font-family", "'Arial', sans-serif")
                .set("font-size", "16px")
                .set("color", "#333")
                .set("line-height", "1.6")
                .set("text-align", "justify")
                .set("margin-top", "30px");

        // Párrafo con el horario
        Paragraph texthorarios = new Paragraph("Horario para consultas");
        texthorarios.getStyle()
                .set("font-family", "'Georgia', serif")
                .set("font-size", "18px")
                .set("color", "#007BFF")
                .set("font-weight", "bold")
                .set("line-height", "1.8")
                .set("text-align", "center");

        Paragraph horarios = new Paragraph("L-V 09:00 a 14:00 y 16:00 a 20:00");

        add(correoDudas, texthorarios, horarios);
    }

    private void addFAQ(String question, String answer) {
        // Pregunta como título
        H3 questionTitle = new H3(question);
        questionTitle.getStyle()
                .set("color", "#007BFF")
                .set("cursor", "pointer")
                .set("transition", "color 0.3s ease-in-out, transform 0.2s ease-in-out")
                .set("margin-bottom", "10px");

        // Respuesta como párrafo
        Paragraph answerText = new Paragraph(answer);
        answerText.getStyle()
                .set("padding-left", "20px")
                .set("font-size", "14px")
                .set("color", "#555")
                .set("line-height", "1.6")
                .set("margin-bottom", "20px");

        // Animaciones al pasar el cursor por la pregunta
        questionTitle.getElement().addEventListener("mouseover", e -> {
            questionTitle.getStyle().set("color", "#0056b3");
            questionTitle.getStyle().set("transform", "scale(1.05)");
        });
        questionTitle.getElement().addEventListener("mouseout", e -> {
            questionTitle.getStyle().set("color", "#007BFF");
            questionTitle.getStyle().set("transform", "scale(1)");
        });

        // Agregar los elementos a la vista
        add(questionTitle, answerText);
    }
}


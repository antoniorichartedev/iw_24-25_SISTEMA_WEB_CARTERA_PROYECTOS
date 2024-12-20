package projectum.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/* ESTA CLASE SIRVE PARA PODER RECOGER LOS DATOS DE LOS POTENCIALES PROMOTORES DE LA SIGUIENTE WEB:
*   https://e608f590-1a0b-43c5-b363-e5a883961765.mock.pstmn.io/sponsors
*/

@Configuration
public class WebClientConfig {
    @Bean(name = "customWebClient")
    public WebClient webClientConfig(WebClient.Builder builder) {
        return builder.build();
    }
}

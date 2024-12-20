package projectum.data.servicios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import projectum.data.entidades.Promotor;
import projectum.data.entidades.Usuario;
import projectum.data.repositorios.PromotorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromotorService {
    private final PromotorRepository promotorRepository;
    private final WebClient webClient;
    private final BCryptPasswordEncoder passwordEncoder;

    public PromotorService(PromotorRepository promotorRepository, WebClient webClient, BCryptPasswordEncoder passwordEncoder) {
        this.promotorRepository = promotorRepository;
        this.webClient = webClient;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Promotor> getAllPromotores() {
        return (List<Promotor>) promotorRepository.findAll();
    }

    public Optional<Promotor> getPromotorById(UUID id) {
        return promotorRepository.findById(id);
    }

    public Promotor savePromotor(Promotor promotor) {
        return promotorRepository.save(promotor);
    }

    public void deletePromotor(UUID id) {
        promotorRepository.deleteById(id);
    }
/*
    public List<Usuario> recogerPromotoresWeb() {
        String url = "https://e608f590-1a0b-43c5-b363-e5a883961765.mock.pstmn.io/sponsors";

        String jsonResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("JSON Response: " + jsonResponse); // Imprime el JSON para verificar.

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(Usuario.class)
                .collectList()
                .block();
    }

    public void guardarPromotoresWeb() {
        List<Usuario> promotores = recogerPromotoresWeb();
        System.out.println(promotores); // Verifica aquí los valores obtenidos.

        int i = 0;

        for (Usuario p : promotores) {
            System.out.println("Promotor: " + p.getNombre()); // Verifica cada nombre individualmente.
            boolean existePromotor = promotorRepository.existsById(p.getId());
            if (!existePromotor) {
                Promotor prom = new Promotor();
                prom.setNombre(p.getNombre());
                prom.setCargo(p.getCargo());
                prom.setPassword(passwordEncoder.encode("soyunpromotorcualquiera"));
                prom.setUsername("Promotor_" + i);
                prom.setCorreo("correodepromotor" + i + "@gmail.com");
                i++;
                promotorRepository.save(prom);
            }
        }
    }
*/
}

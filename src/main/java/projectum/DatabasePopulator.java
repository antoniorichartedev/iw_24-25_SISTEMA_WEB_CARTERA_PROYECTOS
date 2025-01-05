package projectum;

import com.github.javafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import projectum.data.Estado;
import projectum.data.entidades.*;
import projectum.data.servicios.UsuarioService;
import projectum.data.Rol;
import projectum.data.servicios.ProyectoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DatabasePopulator implements CommandLineRunner {

    private final UsuarioService userService;
    private final ProyectoService proyectService;


    public DatabasePopulator(UsuarioService userService, ProyectoService proyectService) {
        this.proyectService = proyectService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        // Creamos admin
        if (userService.count() < 5) {

            // Cargar promotores desde la API solo si no están cargados previamente
            userService.cargarPromotoresDesdeApi();
            System.out.println("Promotores cargados (si no estaban previamente).");


            Usuario user = new Usuario();
            user.setNombre("admin");
            user.setUsername("adminXulo");
            user.setPassword("admin");
            user.setCorreo("admin@uca.es");
            user.setRol(Rol.ADMIN);
            userService.RegistrarUsuario(user);
            userService.activarUsuario(user.getCorreo(), user.getCodigoRegistro());
            System.out.println("Admin created");

            Usuario cio = new Usuario();
            cio.setNombre("CIO");
            cio.setUsername("cioXulo");
            cio.setPassword("cio");
            cio.setCorreo("cio@uca.es");
            cio.setRol(Rol.CIO);
            userService.RegistrarUsuario(cio);
            userService.activarUsuario(cio.getCorreo(), cio.getCodigoRegistro());
            System.out.println("CIO created");

            Usuario ot = new Usuario();
            ot.setNombre("oficina tecnica");
            ot.setPassword("oficinatecnica");
            ot.setUsername("oficinatecnicaXula");
            ot.setCorreo("oficinatecnica@uca.es");
            ot.setRol(Rol.OT);
            userService.RegistrarUsuario(ot);
            userService.activarUsuario(ot.getCorreo(), ot.getCodigoRegistro());
            System.out.println("Oficina Técnica created");

            Usuario sol = new Usuario();
            sol.setNombre("Solicitante");
            sol.setUsername("solicitanteXulo");
            sol.setPassword("solicitante");
            sol.setUnidadSolicitante("Mi unidad");
            sol.setCorreo("solicitante@uca.es");
            sol.setRol(Rol.USER);
            userService.RegistrarUsuario(sol);
            userService.activarUsuario(sol.getCorreo(), sol.getCodigoRegistro());
            System.out.println("Solicitante created");

            Usuario promo = new Usuario();
            promo.setNombre("Promotor");
            promo.setUsername("promotorXulo");
            promo.setPassword("promotor");
            promo.setCorreo("promotor@uca.es");
            promo.setCargo("Ninguno");
            promo.setRol(Rol.PROMOTOR);
            userService.RegistrarUsuario(promo);
            userService.activarUsuario(promo.getCorreo(), promo.getCodigoRegistro());
            System.out.println("Promotor created");

            Proyecto pr = new Proyecto();
            pr.setTitulo("Proyecto de ejemplo");
            pr.setAcronimo("PEJ");
            pr.setJustificacion("No hay");
            pr.setAlcance("Trebujena");
            pr.setMemorias(null);
            pr.setImportancia(2);
            pr.setFinanciacion(BigDecimal.valueOf(33.33));
            pr.setPuestaMarcha(new Date());
            pr.setInteresado("admin");
            pr.setEstado(Estado.en_desarrollo);
            pr.setPriorizacion(5);

            // Rescatamos de nuevo el promotor y el solicitante del proyecto de ejemplo.
            promo = userService.loadUserByUsername(promo.getUsername());
            sol = userService.loadUserByUsername(sol.getUsername());

            if(promo != null && sol != null) {
                pr.setPromotor(promo);
                pr.setSolicitante(sol);

                proyectService.saveProyecto(pr);
                System.out.println("Proyecto created y guardado.");
                System.out.println("Relaciones con el proyecto realizadas.");
            }
        }


    }

}

package projectum;

import com.github.javafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import projectum.data.Estado;
import projectum.data.entidades.*;
import projectum.data.servicios.ConvocatoriaService;
import projectum.data.servicios.UsuarioService;
import projectum.data.Rol;
import projectum.data.servicios.ProyectoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Component
public class DatabasePopulator implements CommandLineRunner {

    private final UsuarioService userService;
    private final ProyectoService proyectService;
    private final ConvocatoriaService convocatoriaService;


    public DatabasePopulator(UsuarioService userService, ProyectoService proyectService, ConvocatoriaService convocatoriaService) {
        this.proyectService = proyectService;
        this.userService = userService;
        this.convocatoriaService = convocatoriaService;
    }

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        // Creamos instancias
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
            pr.setTitulo("Proyecto 1");
            pr.setAcronimo("PEJ");
            pr.setJustificacion("No hay");
            pr.setAlcance("Trebujena");
            pr.setMemorias(null);
            pr.setImportancia(0);
            pr.setFinanciacion(BigDecimal.valueOf(33.33));
            pr.setPuestaMarcha(new Date());
            pr.setInteresado("admin");
            pr.setEstado(Estado.en_desarrollo);
            pr.setPriorizacion(0);

            Proyecto pr2 = new Proyecto();
            pr2.setTitulo("Proyecto 2");
            pr2.setAcronimo("PEF");
            pr2.setJustificacion("No hay");
            pr2.setAlcance("Campus PR");
            pr2.setMemorias(null);
            pr2.setImportancia(0);
            pr2.setFinanciacion(BigDecimal.valueOf(44.33));
            pr2.setPuestaMarcha(new Date());
            pr2.setInteresado("admin");
            pr2.setEstado(Estado.valorado);
            pr2.setPriorizacion(0);

            Proyecto pr3 = new Proyecto();
            pr3.setTitulo("Proyecto 3");
            pr3.setAcronimo("PEG");
            pr3.setJustificacion("No hay");
            pr3.setAlcance("Campus Jerez");
            pr3.setMemorias(null);
            pr3.setImportancia(0);
            pr3.setFinanciacion(BigDecimal.valueOf(33.33));
            pr3.setPuestaMarcha(new Date());
            pr3.setInteresado("admin");
            pr3.setEstado(Estado.valoradoCIO);
            pr3.setPriorizacion(0);

            Proyecto pr4 = new Proyecto();
            pr4.setTitulo("Proyecto 4");
            pr4.setAcronimo("PEP");
            pr4.setJustificacion("No hay");
            pr4.setAlcance("Campus Cádiz");
            pr4.setMemorias(null);
            pr4.setImportancia(0);
            pr4.setFinanciacion(BigDecimal.valueOf(22.33));
            pr4.setPuestaMarcha(new Date());
            pr4.setInteresado("admin");
            pr4.setEstado(Estado.valoradoOT);
            pr4.setPriorizacion(0);

            Proyecto pr5 = new Proyecto();
            pr5.setTitulo("Proyecto 5");
            pr5.setAcronimo("PET");
            pr5.setJustificacion("No hay");
            pr5.setAlcance("ESI");
            pr5.setMemorias(null);
            pr5.setImportancia(0);
            pr5.setFinanciacion(BigDecimal.valueOf(55.33));
            pr5.setPuestaMarcha(new Date());
            pr5.setInteresado("admin");
            pr5.setEstado(Estado.en_valoracion);
            pr5.setPriorizacion(0);

            Proyecto pr6 = new Proyecto();
            pr6.setTitulo("Proyecto 6");
            pr6.setAcronimo("PER");
            pr6.setJustificacion("No hay");
            pr6.setAlcance("Facultad Educación");
            pr6.setMemorias(null);
            pr6.setImportancia(0);
            pr6.setFinanciacion(BigDecimal.valueOf(66.33));
            pr6.setPuestaMarcha(new Date());
            pr6.setInteresado("admin");
            pr6.setEstado(Estado.sin_avalar);
            pr6.setPriorizacion(0);

            Proyecto pr7 = new Proyecto();
            pr7.setTitulo("Proyecto 7");
            pr7.setAcronimo("PEQ");
            pr7.setJustificacion("No hay");
            pr7.setAlcance("Campus Algeciras");
            pr7.setMemorias(null);
            pr7.setImportancia(0);
            pr7.setFinanciacion(BigDecimal.valueOf(77.33));
            pr7.setPuestaMarcha(new Date());
            pr7.setInteresado("admin");
            pr7.setEstado(Estado.rechazado);
            pr7.setPriorizacion(0);

            Proyecto pr8 = new Proyecto();
            pr8.setTitulo("Proyecto 8");
            pr8.setAcronimo("PES");
            pr8.setJustificacion("No hay");
            pr8.setAlcance("ESI");
            pr8.setMemorias(null);
            pr8.setImportancia(0);
            pr8.setFinanciacion(BigDecimal.valueOf(11.33));
            pr8.setPuestaMarcha(new Date());
            pr8.setInteresado("admin");
            pr8.setEstado(Estado.completado);
            pr8.setPriorizacion(0);

            Convocatoria conv = new Convocatoria();
            conv.setNombre("Convocatoria de enero");
            conv.setFechaInicio(new Date());
            conv.setFechaFin(new Date("31/01/2025"));
            conv.setActividad(true);
            convocatoriaService.saveConvocatoria(conv);
            System.out.println("Convocatoria created");

            // Asociamos la convocatoria al proyecto
            Optional<Convocatoria> convNueva = convocatoriaService.getConvocatoriaByNombre(conv.getNombre());
            if (convNueva.isPresent()) {
                pr.setConvocatoria(convNueva.get());
                pr2.setConvocatoria(convNueva.get());
                pr3.setConvocatoria(convNueva.get());
                pr4.setConvocatoria(convNueva.get());
                pr5.setConvocatoria(convNueva.get());
                pr6.setConvocatoria(convNueva.get());
                pr7.setConvocatoria(convNueva.get());
                pr8.setConvocatoria(convNueva.get());

            }

            // Rescatamos de nuevo el promotor y el solicitante del proyecto de ejemplo.
            promo = userService.loadUserByUsername(promo.getUsername());
            sol = userService.loadUserByUsername(sol.getUsername());

            if(promo != null && sol != null) {
                pr.setPromotor(promo);
                pr.setSolicitante(sol);
                pr2.setPromotor(promo);
                pr2.setSolicitante(sol);
                pr3.setPromotor(promo);
                pr3.setSolicitante(sol);
                pr4.setPromotor(promo);
                pr4.setSolicitante(sol);
                pr5.setPromotor(promo);
                pr5.setSolicitante(sol);
                pr6.setPromotor(promo);
                pr6.setSolicitante(sol);
                pr7.setPromotor(promo);
                pr7.setSolicitante(sol);
                pr8.setPromotor(promo);
                pr8.setSolicitante(sol);

                proyectService.saveProyecto(pr);
                proyectService.saveProyecto(pr2);
                proyectService.saveProyecto(pr3);
                proyectService.saveProyecto(pr4);
                proyectService.saveProyecto(pr5);
                proyectService.saveProyecto(pr6);
                proyectService.saveProyecto(pr7);
                proyectService.saveProyecto(pr8);


                System.out.println("Proyectos created y guardados.");
                System.out.println("Relaciones con el proyecto realizadas.");
            }
        }
    }
}
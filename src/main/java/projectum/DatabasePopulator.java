package projectum;

import com.github.javafaker.Faker;
import projectum.data.entidades.Usuario;
import projectum.data.servicios.UsuarioService;
import projectum.data.Rol;
import projectum.data.entidades.Proyecto;
import projectum.data.servicios.ProyectoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator implements CommandLineRunner {

    UsuarioService userService;

    ProyectoService proyectService;

    public DatabasePopulator(UsuarioService userService, ProyectoService proyectService) {
        this.proyectService = proyectService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        // Creamos admin
        if (userService.count() < 3) {
            Usuario user = new Usuario();
            user.setNombre("admin");
            user.setPassword("admin");
            user.setCorreo("admin@uca.es");
            user.setRol(Rol.ADMIN);
            userService.RegistrarUsuario(user);
            userService.activarUsuario(user.getCorreo(), user.getCodigoRegistro());
            System.out.println("Admin created");

            Usuario cio = new Usuario();
            cio.setNombre("CIO");
            cio.setPassword("cio");
            cio.setCorreo("cio@uca.es");
            cio.setRol(Rol.CIO);
            userService.RegistrarUsuario(cio);
            userService.activarUsuario(cio.getCorreo(), cio.getCodigoRegistro());
            System.out.println("CIO created");

            Usuario ot = new Usuario();
            ot.setNombre("oficina tecnica");
            ot.setPassword("oficinatecnica");
            ot.setCorreo("oficinatecnica@uca.es");
            ot.setRol(Rol.OT);
            userService.RegistrarUsuario(ot);
            userService.activarUsuario(ot.getCorreo(), ot.getCodigoRegistro());
            System.out.println("Oficina Tencinca created");
        }
    }
}

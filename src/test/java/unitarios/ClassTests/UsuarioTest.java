package unitarios.ClassTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projectum.data.Rol;
import projectum.data.entidades.Proyecto;
import projectum.data.entidades.Usuario;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Juan Perez", "juanp", "juan.perez@example.com", "password123");
        usuario.setId(UUID.randomUUID());
        usuario.setRol(Rol.USER);
        usuario.setEstado(true);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Juan Perez", usuario.getNombre());
        assertEquals("juanp", usuario.getUsername());
        assertEquals("juan.perez@example.com", usuario.getCorreo());
        assertEquals("password123", usuario.getPassword());
        assertEquals(Rol.USER, usuario.getRol());
        assertTrue(usuario.getEstado());
    }

    @Test
    void testSetters() {
        usuario.setNombre("Maria Lopez");
        usuario.setUsername("marial");
        usuario.setCorreo("maria.lopez@example.com");
        usuario.setPassword("newpassword");
        usuario.setRol(Rol.ADMIN);
        usuario.setEstado(false);

        assertEquals("Maria Lopez", usuario.getNombre());
        assertEquals("marial", usuario.getUsername());
        assertEquals("maria.lopez@example.com", usuario.getCorreo());
        assertEquals("newpassword", usuario.getPassword());
        assertEquals(Rol.ADMIN, usuario.getRol());
        assertFalse(usuario.getEstado());
    }

    @Test
    void testAddProyectoSolicitante() {
        Proyecto proyecto = new Proyecto();
        usuario.setProyectoSol(proyecto);

        assertEquals(1, usuario.getProyectosSolicitante().size());
        assertTrue(usuario.getProyectosSolicitante().contains(proyecto));
        assertEquals(usuario, proyecto.getSolicitante());
    }

    @Test
    void testAddProyectoPromotor() {
        Proyecto proyecto = new Proyecto();
        usuario.setProyectoProm(proyecto);

        assertEquals(1, usuario.getProyectosPromotor().size());
        assertTrue(usuario.getProyectosPromotor().contains(proyecto));
        assertEquals(usuario, proyecto.getPromotor());
    }

    @Test
    void testHashCodeAndEquals() {
        UUID id = UUID.randomUUID();
        usuario.setId(id);

        Usuario other = new Usuario();
        other.setId(id);

        assertEquals(usuario, other);
        assertEquals(usuario.hashCode(), other.hashCode());
    }

    @Test
    void testUserDetailsMethods() {
        assertEquals(1, usuario.getAuthorities().size());
        assertEquals("ROLE_USER", usuario.getAuthorities().iterator().next().getAuthority());

        assertTrue(usuario.isAccountNonExpired());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isCredentialsNonExpired());
        assertTrue(usuario.isEnabled());
    }

    @Test
    void testSetCodigoRegistro() {
        usuario.setCodigoRegistro("ABC123");
        assertEquals("ABC123", usuario.getCodigoRegistro());
    }

    @Test
    void testSetUnidadSolicitante() {
        usuario.setUnidadSolicitante("IT Department");
        assertEquals("IT Department", usuario.getUnidadSolicitante());
    }

    @Test
    void testSetCargo() {
        usuario.setCargo("Manager");
        assertEquals("Manager", usuario.getCargo());
    }

    @Test
    void testSetAndGetHashedPassword() {
        usuario.setHashedPassword("hashed_password");
        assertEquals("hashed_password", usuario.getHashedPassword());
    }
}

package com.example.proyecto.spring.Usuario;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
    Usuario findByNombre(String username);
    Optional<Usuario> findByCorreo(String username);
}

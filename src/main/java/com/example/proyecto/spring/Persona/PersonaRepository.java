package com.example.proyecto.spring.Persona;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonaRepository extends CrudRepository<Persona, UUID> {
    Persona findByNombre(String username);
    Optional<Persona> findByCorreo(String username);
}

package com.example.proyecto.spring.Persona;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
    public List<Persona> getAllPersona() {
        return (List<Persona>) personaRepository.findAll();
    }

    public Optional<Persona> getPersonaById(UUID id) {
        return personaRepository.findById(id);
    }

    public Persona savePersona(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deletePersona(UUID id) {
        personaRepository.deleteById(id);
    }
}

package com.example.proyecto.spring.Usuario;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public List<Usuario> getAllPersona() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Optional<Usuario> getPersonaById(UUID id) {
        return usuarioRepository.findById(id);
    }

    public Usuario savePersona(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletePersona(UUID id) {
        usuarioRepository.deleteById(id);
    }
}

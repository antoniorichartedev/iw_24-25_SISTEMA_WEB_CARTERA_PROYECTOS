package com.example.proyecto.spring.Formulario;

import com.example.proyecto.spring.Promotor.Promotor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FormularioRepository extends CrudRepository<Formulario, UUID> {
}

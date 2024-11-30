package com.example.proyecto.spring.CIO;

import com.example.proyecto.spring.Interesado.Interesado;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CIOService {
    private final CIORepository cioRepository;

    public CIOService(CIORepository cioRepository)
    {
        this.cioRepository = cioRepository;
    }

    public List<CIO> getAllCIOs()
    {
        return (List<CIO>) cioRepository.findAll();
    }

    public Optional<CIO> getCIOById(UUID id)
    {
        return cioRepository.findById(id);
    }

    public CIO saveCIO(CIO cio)
    {
        if(cio.getNombre() != null)
        {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        return cioRepository.save(cio);
    }

    public void deleteCIO(UUID id)
    {
        cioRepository.deleteById(id);
    }
}

package com.example.proyecto.spring.Promotor;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromotorService {
    private final PromotorRepository promotorRepository;

    public PromotorService(PromotorRepository promotorRepository) {
        this.promotorRepository = promotorRepository;
    }
    public List<Promotor> getAllPromotores() {
        return (List<Promotor>) promotorRepository.findAll();
    }

    public Optional<Promotor> getPromotorById(UUID id) {
        return promotorRepository.findById(id);
    }

    public Promotor savePromotor(Promotor promotor) {
        return promotorRepository.save(promotor);
    }

    public void deletePromotor(UUID id) {
        promotorRepository.deleteById(id);
    }
}
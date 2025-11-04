package com.unir.bbdd.service.impl;

import com.unir.bbdd.entity.Provincia;
import com.unir.bbdd.repository.ProvinciaRepository;
import com.unir.bbdd.service.ProvinciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProvinciaServiceImpl implements ProvinciaService {

    private final ProvinciaRepository provinciaRepository;

    @Override
    @Transactional
    public Provincia getOrCreateProvincia(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la provincia no puede estar vacío");
        }

        return provinciaRepository.findByNombre(nombre)
                .orElseGet(() -> {
                    Provincia provincia = new Provincia();
                    provincia.setNombre(nombre);
                    return provinciaRepository.save(provincia);
                });
    }
}


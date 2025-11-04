package com.unir.bbdd.service.impl;

import com.unir.bbdd.entity.Municipio;
import com.unir.bbdd.entity.Provincia;
import com.unir.bbdd.repository.MunicipioRepository;
import com.unir.bbdd.service.MunicipioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MunicipioServiceImpl implements MunicipioService {

    private final MunicipioRepository municipioRepository;

    @Override
    @Transactional
    public Municipio getOrCreateMunicipio(String nombreMunicipio, Provincia provincia) {
        if (nombreMunicipio == null || nombreMunicipio.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del municipio no puede estar vacío");
        }
        if (provincia == null) {
            throw new IllegalArgumentException("La provincia no puede ser null");
        }

        return municipioRepository.findByNombreAndProvincia(nombreMunicipio, provincia)
                .orElseGet(() -> {
                    Municipio municipio = new Municipio();
                    municipio.setNombre(nombreMunicipio);
                    municipio.setProvincia(provincia);
                    return municipioRepository.save(municipio);
                });
    }
}


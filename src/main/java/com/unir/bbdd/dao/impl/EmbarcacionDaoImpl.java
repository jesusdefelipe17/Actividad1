package com.unir.bbdd.dao.impl;

import com.unir.bbdd.dao.EmbarcacionDao;
import com.unir.bbdd.dto.ProvinciaGasolina95MasCaraDto;
import com.unir.bbdd.projection.ProvinciaGasolina95Projection;
import com.unir.bbdd.repository.EmbarcacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmbarcacionDaoImpl implements EmbarcacionDao {

    private final EmbarcacionRepository embarcacionRepository;

    @Override
    public String getEmpresaConMasEmbarcaciones() {

        return embarcacionRepository.findEmpresaConMasEmbarcaciones();
    }

    @Override
    public ProvinciaGasolina95MasCaraDto getProvinciaConGasolina95E5MasCara() {
        ProvinciaGasolina95Projection result = embarcacionRepository.findProvinciaConGasolina95E5MasCara();

        if (result != null) {
            ProvinciaGasolina95MasCaraDto dto = new ProvinciaGasolina95MasCaraDto();
            dto.setProvincia(result.getProvincia());
            dto.setPrecioMaximo(result.getPrecioMaximo());
            dto.setEmbarcacionDireccion(result.getEmbarcacionDireccion());
            dto.setMunicipio(result.getMunicipio());
            return dto;
        }

        return null;
    }
}


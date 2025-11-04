package com.unir.bbdd.service.impl;

import com.unir.bbdd.dao.EmbarcacionDao;
import com.unir.bbdd.dto.ProvinciaGasolina95MasCaraDto;
import com.unir.bbdd.service.EmbarcacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbarcacionServiceImpl implements EmbarcacionService {

    private final EmbarcacionDao embarcacionDao;


    @Override
    public String getEmpresaConMasEmbarcaciones() {
        return embarcacionDao.getEmpresaConMasEmbarcaciones();
    }

    @Override
    public ProvinciaGasolina95MasCaraDto getProvinciaConGasolina95E5MasCara() {
        return embarcacionDao.getProvinciaConGasolina95E5MasCara();
    }
}


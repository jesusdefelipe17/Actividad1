package com.unir.bbdd.service;

import com.unir.bbdd.dto.ProvinciaGasolina95MasCaraDto;

public interface EmbarcacionService {


    String getEmpresaConMasEmbarcaciones();

    ProvinciaGasolina95MasCaraDto getProvinciaConGasolina95E5MasCara();
}


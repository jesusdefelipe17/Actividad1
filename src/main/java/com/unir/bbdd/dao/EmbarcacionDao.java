package com.unir.bbdd.dao;

import com.unir.bbdd.dto.ProvinciaGasolina95MasCaraDto;

public interface EmbarcacionDao {


    String getEmpresaConMasEmbarcaciones();

    ProvinciaGasolina95MasCaraDto getProvinciaConGasolina95E5MasCara();
}


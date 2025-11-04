package com.unir.bbdd.service;

import com.unir.bbdd.dto.EstacionPrecioMinimoDto;

public interface EstacionServicioService {


    String getEmpresaConMasEstaciones();

    EstacionPrecioMinimoDto getEstacionPrecioMinimoGasolina95E5Madrid();

    EstacionPrecioMinimoDto getEstacionPrecioMinimoGasoleoAAlbacete();
}


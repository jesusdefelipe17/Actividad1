package com.unir.bbdd.service.impl;

import com.unir.bbdd.dao.EstacionServicioDao;
import com.unir.bbdd.dto.EstacionPrecioMinimoDto;
import com.unir.bbdd.service.EstacionServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstacionServicioServiceImpl implements EstacionServicioService {

    private final EstacionServicioDao estacionServicioDao;


    @Override
    public String getEmpresaConMasEstaciones() {
        return estacionServicioDao.findEmpresaConMasEstaciones();
    }

    @Override
    public EstacionPrecioMinimoDto getEstacionPrecioMinimoGasolina95E5Madrid() {
        return estacionServicioDao.findEstacionPrecioMinimoGasolina95E5Madrid();
    }

    @Override
    public EstacionPrecioMinimoDto getEstacionPrecioMinimoGasoleoAAlbacete() {
        return estacionServicioDao.findEstacionPrecioMinimoGasoleoAAlbacete();
    }
}


package com.unir.bbdd.dao.impl;

import com.unir.bbdd.dao.EstacionServicioDao;
import com.unir.bbdd.dto.EstacionPrecioMinimoDto;
import com.unir.bbdd.projection.EstacionPrecioProjection;
import com.unir.bbdd.repository.EstacionServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EstacionServicioDaoImpl implements EstacionServicioDao {

    private final EstacionServicioRepository estacionServicioRepository;

    @Override
    public String findEmpresaConMasEstaciones() {
        List<String> empresas = estacionServicioRepository.findEmpresasOrderByNumEstaciones();
        return empresas.isEmpty() ? "Sin datos" : empresas.get(0);
    }

    @Override
    public EstacionPrecioMinimoDto findEstacionPrecioMinimoGasolina95E5Madrid() {
        EstacionPrecioProjection estacion = estacionServicioRepository.findEstacionesConGasolina95E5EnMadridOrderByPrecio();

        if (estacion == null) {
            return new EstacionPrecioMinimoDto("Sin datos", "Sin datos", "Sin datos", 0.0);
        }

        return new EstacionPrecioMinimoDto(
            estacion.getLocalizacion(),
            estacion.getNombreEmpresa(),
            estacion.getMargen(),
            estacion.getPrecio()
        );
    }

    @Override
    public EstacionPrecioMinimoDto findEstacionPrecioMinimoGasoleoAAlbacete() {
        double centroLatitud = 38.9943;
        double centroLongitud = -1.8585;
        double radioKm = 10.0;

        EstacionPrecioProjection estacion = estacionServicioRepository.findEstacionesConGasoleoAEnAlbaceteOrderByPrecio(
            centroLatitud, centroLongitud, radioKm);

        if (estacion == null) {
            return new EstacionPrecioMinimoDto("Sin datos", "Sin datos", "Sin datos", 0.0);
        }

        return new EstacionPrecioMinimoDto(
            estacion.getLocalizacion(),
            estacion.getNombreEmpresa(),
            estacion.getMargen(),
            estacion.getPrecio()
        );
    }
}


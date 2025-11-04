package com.unir.bbdd.dao;

import com.unir.bbdd.dto.EstacionPrecioMinimoDto;

public interface EstacionServicioDao {


    String findEmpresaConMasEstaciones();

    EstacionPrecioMinimoDto findEstacionPrecioMinimoGasolina95E5Madrid();

    EstacionPrecioMinimoDto findEstacionPrecioMinimoGasoleoAAlbacete();
}


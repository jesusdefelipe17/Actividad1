package com.unir.bbdd.service;

import com.unir.bbdd.entity.TipoCombustible;

public interface TipoCombustibleService {
    TipoCombustible getOrCreateTipoCombustible(String codigo, String nombre, String categoria);
    void initializeTiposCombustible();
}


package com.unir.bbdd.service;

import com.unir.bbdd.entity.Provincia;

public interface ProvinciaService {
    Provincia getOrCreateProvincia(String nombre);
}


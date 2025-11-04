package com.unir.bbdd.service;

import com.unir.bbdd.entity.Municipio;
import com.unir.bbdd.entity.Provincia;

public interface MunicipioService {
    Municipio getOrCreateMunicipio(String nombreMunicipio, Provincia provincia);
}


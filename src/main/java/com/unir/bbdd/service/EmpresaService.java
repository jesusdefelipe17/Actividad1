package com.unir.bbdd.service;

import com.unir.bbdd.dto.EmpresaDto;

public interface EmpresaService {


    EmpresaDto getOrCreateEmpresa(String rotulo);
}


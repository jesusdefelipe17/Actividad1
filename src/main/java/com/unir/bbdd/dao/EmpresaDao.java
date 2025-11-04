package com.unir.bbdd.dao;

import com.unir.bbdd.dto.EmpresaDto;

import java.util.Optional;

public interface EmpresaDao {

    EmpresaDto save(EmpresaDto empresaDto);

    Optional<EmpresaDto> findByRotulo(String rotulo);
}


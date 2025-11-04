package com.unir.bbdd.dao.impl;

import com.unir.bbdd.dao.EmpresaDao;
import com.unir.bbdd.dto.EmpresaDto;
import com.unir.bbdd.entity.Empresa;
import com.unir.bbdd.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmpresaDaoImpl implements EmpresaDao {

    private final EmpresaRepository empresaRepository;

    @Override
    public EmpresaDto save(EmpresaDto empresaDto) {
        Empresa empresa = toEntity(empresaDto);
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return toDto(savedEmpresa);
    }

    @Override
    public Optional<EmpresaDto> findByRotulo(String rotulo) {
        return empresaRepository.findByRotulo(rotulo).map(this::toDto);
    }


    private EmpresaDto toDto(Empresa empresa) {
        EmpresaDto dto = new EmpresaDto();
        dto.setId(empresa.getId());
        dto.setRotulo(empresa.getRotulo());
        return dto;
    }

    private Empresa toEntity(EmpresaDto dto) {
        Empresa empresa = new Empresa();
        empresa.setId(dto.getId());
        empresa.setRotulo(dto.getRotulo());
        return empresa;
    }
}


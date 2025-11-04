package com.unir.bbdd.service.impl;

import com.unir.bbdd.dao.EmpresaDao;
import com.unir.bbdd.dto.EmpresaDto;
import com.unir.bbdd.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaDao empresaDao;


    @Override
    @Transactional
    public EmpresaDto getOrCreateEmpresa(String rotulo) {
        Optional<EmpresaDto> empresaExistente = empresaDao.findByRotulo(rotulo);
        if (empresaExistente.isPresent()) {
            return empresaExistente.get();
        } else {
            EmpresaDto nuevaEmpresa = new EmpresaDto();
            nuevaEmpresa.setRotulo(rotulo);
            return empresaDao.save(nuevaEmpresa);
        }
    }
}


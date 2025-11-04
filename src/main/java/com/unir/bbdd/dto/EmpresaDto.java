package com.unir.bbdd.dto;

import com.unir.bbdd.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDto {

    private Long id;
    private String rotulo;

    public Empresa toEntity() {
        Empresa empresa = new Empresa();
        empresa.setId(this.id);
        empresa.setRotulo(this.rotulo);
        return empresa;
    }
}


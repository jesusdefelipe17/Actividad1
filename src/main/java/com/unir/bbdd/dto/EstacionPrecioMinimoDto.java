package com.unir.bbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionPrecioMinimoDto {
    private String localizacion;
    private String nombreEmpresa;
    private String margen;
    private Double precio;
}


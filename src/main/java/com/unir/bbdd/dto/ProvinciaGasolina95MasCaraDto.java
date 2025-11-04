package com.unir.bbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinciaGasolina95MasCaraDto {
    private String provincia;
    private BigDecimal precioMaximo;
    private String embarcacionDireccion;
    private String municipio;
}


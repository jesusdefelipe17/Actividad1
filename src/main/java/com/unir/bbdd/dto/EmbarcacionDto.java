package com.unir.bbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbarcacionDto {

    private Long id;
    private String provincia;
    private String municipio;
    private String localidad;
    private String codigoPostal;
    private String direccion;
    private String longitud;
    private String latitud;
    private BigDecimal precioGasolina95E5;
    private BigDecimal precioGasolina95E10;
    private BigDecimal precioGasolina95E25;
    private BigDecimal precioGasolina95E85;
    private BigDecimal precioGasolinaRenovable;
    private BigDecimal precioGasoleoA;
    private BigDecimal precioGasoleoB;
    private BigDecimal precioGasoleoUsoMaritimo;
    private BigDecimal precioDieselRenovable;
    private BigDecimal precioAdBlue;
    private BigDecimal precioMetanol;
    private BigDecimal precioAmoniaco;
    private BigDecimal precioBGNC;
    private BigDecimal precioBGNL;
    private String tipoVenta;
    private String rem;
    private String horario;
    private Long empresaId;
    private String rotulo;
}


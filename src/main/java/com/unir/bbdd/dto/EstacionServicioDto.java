package com.unir.bbdd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionServicioDto {

    private Long id;
    private String provincia;
    private String municipio;
    private String localidad;
    private String codigoPostal;
    private String direccion;
    private String margen;
    private String longitud;
    private String latitud;
    private LocalDateTime tomaDatos;
    private BigDecimal precioGasolina95E5;
    private BigDecimal precioGasolina95E10;
    private BigDecimal precioGasolina95E5Premium;
    private BigDecimal precioGasolina98E5;
    private BigDecimal precioGasolina98E10;
    private BigDecimal precioGasolina95E25;
    private BigDecimal precioGasolina95E85;
    private BigDecimal precioGasolinaRenovable;
    private BigDecimal precioGasoleoA;
    private BigDecimal precioGasoleoPremium;
    private BigDecimal precioGasoleoB;
    private BigDecimal precioGasoleoC;
    private BigDecimal precioDieselRenovable;
    private BigDecimal precioBioetanol;
    private BigDecimal porcentajeBioalcohol;
    private BigDecimal precioBiodiesel;
    private BigDecimal porcentajeEsterMetilico;
    private BigDecimal precioGLP;
    private BigDecimal precioGNC;
    private BigDecimal precioGNL;
    private BigDecimal precioBGNC;
    private BigDecimal precioBGNL;
    private BigDecimal precioHidrogeno;
    private BigDecimal precioAdBlue;
    private BigDecimal precioMetanol;
    private BigDecimal precioAmoniaco;
    private String tipoVenta;
    private String rem;
    private String horario;
    private String tipoServicio;
    private Long empresaId;
    private String rotulo;
}


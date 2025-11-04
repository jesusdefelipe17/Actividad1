package com.unir.bbdd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "precios_combustible",
       indexes = {
           @Index(name = "idx_estacion_combustible", columnList = "estacion_id,tipo_combustible_id"),
           @Index(name = "idx_fecha_precio", columnList = "fecha_precio")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecioCombustible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacion_id", nullable = true)
    private EstacionServicio estacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_combustible_id", nullable = true)
    private TipoCombustible tipoCombustible;

    @Column(name = "precio", precision = 10, scale = 3, nullable = true)
    private BigDecimal precio;

    @Column(name = "fecha_precio", nullable = true)
    private LocalDateTime fechaPrecio;

    @Column(name = "porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;
}


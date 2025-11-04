package com.unir.bbdd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "precios_embarcacion",
       indexes = {
           @Index(name = "idx_embarcacion_combustible", columnList = "embarcacion_id,tipo_combustible_id"),
           @Index(name = "idx_fecha_precio_emb", columnList = "fecha_precio")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecioEmbarcacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "embarcacion_id", nullable = true)
    private Embarcacion embarcacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_combustible_id", nullable = true)
    private TipoCombustible tipoCombustible;

    @Column(name = "precio", precision = 10, scale = 3, nullable = true)
    private BigDecimal precio;

    @Column(name = "fecha_precio", nullable = true)
    private LocalDateTime fechaPrecio;
}


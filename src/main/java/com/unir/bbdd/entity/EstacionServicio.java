package com.unir.bbdd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "estaciones_servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacionServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", nullable = true)
    private Municipio municipio;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "margen")
    private String margen;

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "toma_datos")
    private LocalDateTime tomaDatos;

    @Column(name = "tipo_venta")
    private String tipoVenta;

    @Column(name = "rem")
    private String rem;

    @Column(name = "horario", length = 500)
    private String horario;

    @Column(name = "tipo_servicio")
    private String tipoServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    @OneToMany(mappedBy = "estacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrecioCombustible> precios;
}


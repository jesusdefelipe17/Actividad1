package com.unir.bbdd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "embarcaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Embarcacion {

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

    @Column(name = "longitud")
    private String longitud;

    @Column(name = "latitud")
    private String latitud;

    @Column(name = "tipo_venta")
    private String tipoVenta;

    private String rem;

    @Column(name = "horario", length = 500)
    private String horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    @OneToMany(mappedBy = "embarcacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrecioEmbarcacion> precios;
}


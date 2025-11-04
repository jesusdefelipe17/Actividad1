package com.unir.bbdd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tipos_combustible")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoCombustible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = true, length = 50)
    private String codigo;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @OneToMany(mappedBy = "tipoCombustible", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrecioCombustible> precios;
}


package com.unir.bbdd.repository;

import com.unir.bbdd.entity.EstacionServicio;
import com.unir.bbdd.projection.EstacionPrecioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstacionServicioRepository extends JpaRepository<EstacionServicio, Long> {


    @Query("SELECT emp.rotulo FROM EstacionServicio e " +
           "JOIN e.empresa emp " +
           "GROUP BY emp.id, emp.rotulo " +
           "ORDER BY COUNT(e.id) DESC")
    List<String> findEmpresasOrderByNumEstaciones();

    @Query(value = "SELECT CONCAT(e.direccion, ', ', e.localidad, ', ', m.nombre) AS localizacion, " +
           "emp.rotulo AS nombreEmpresa, " +
           "e.margen AS margen, " +
           "pc.precio AS precio " +
           "FROM estaciones_servicio e " +
           "JOIN precios_combustible pc ON e.id = pc.estacion_id " +
           "JOIN tipos_combustible tc ON pc.tipo_combustible_id = tc.id " +
           "JOIN municipios m ON e.municipio_id = m.id " +
           "JOIN provincias p ON m.provincia_id = p.id " +
           "JOIN empresas emp ON e.empresa_id = emp.id " +
           "WHERE tc.codigo = 'GASOLINA_95_E5' " +
           "AND p.nombre = 'MADRID' " +
           "ORDER BY pc.precio ASC LIMIT 1",
           nativeQuery = true)
    EstacionPrecioProjection findEstacionesConGasolina95E5EnMadridOrderByPrecio();

    @Query(value = "SELECT CONCAT(e.direccion, ', ', e.localidad, ', ', m.nombre) AS localizacion, " +
           "emp.rotulo AS nombreEmpresa, " +
           "e.margen AS margen, " +
           "pc.precio AS precio " +
           "FROM estaciones_servicio e " +
           "JOIN precios_combustible pc ON e.id = pc.estacion_id " +
           "JOIN tipos_combustible tc ON pc.tipo_combustible_id = tc.id " +
           "JOIN municipios m ON e.municipio_id = m.id " +
           "JOIN empresas emp ON e.empresa_id = emp.id " +
           "WHERE tc.codigo = 'GASOLEO_A' " +
           "AND m.nombre = 'ALBACETE' " +
           "AND e.latitud IS NOT NULL " +
           "AND e.longitud IS NOT NULL " +
           "AND (6371 * acos(cos(radians(:latitud)) * cos(radians(CAST(REPLACE(e.latitud, ',', '.') AS DOUBLE PRECISION))) * " +
           "cos(radians(CAST(REPLACE(e.longitud, ',', '.') AS DOUBLE PRECISION)) - radians(:longitud)) + " +
           "sin(radians(:latitud)) * sin(radians(CAST(REPLACE(e.latitud, ',', '.') AS DOUBLE PRECISION))))) <= :distanciaMaxKm " +
           "ORDER BY pc.precio ASC LIMIT 1",
           nativeQuery = true)
    EstacionPrecioProjection findEstacionesConGasoleoAEnAlbaceteOrderByPrecio(
            @Param("latitud") double latitud,
            @Param("longitud") double longitud,
            @Param("distanciaMaxKm") double distanciaMaxKm);
}


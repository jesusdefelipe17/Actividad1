package com.unir.bbdd.repository;

import com.unir.bbdd.entity.Embarcacion;
import com.unir.bbdd.projection.ProvinciaGasolina95Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmbarcacionRepository extends JpaRepository<Embarcacion, Long> {

    @Query("SELECT emp.rotulo FROM Empresa emp " +
           "LEFT JOIN emp.embarcaciones emb " +
           "GROUP BY emp.id, emp.rotulo " +
           "ORDER BY COUNT(emb.id) DESC " +
           "LIMIT 1")
    String findEmpresaConMasEmbarcaciones();

    @Query(value = "SELECT p.nombre AS provincia, " +
           "pe.precio AS precioMaximo, " +
           "e.direccion AS embarcacionDireccion, " +
           "m.nombre AS municipio " +
           "FROM precios_embarcacion pe " +
           "JOIN embarcaciones e ON pe.embarcacion_id = e.id " +
           "JOIN municipios m ON e.municipio_id = m.id " +
           "JOIN provincias p ON m.provincia_id = p.id " +
           "JOIN tipos_combustible tc ON pe.tipo_combustible_id = tc.id " +
           "WHERE tc.codigo = 'GASOLINA_95_E5' " +
           "AND pe.precio IS NOT NULL " +
           "ORDER BY pe.precio DESC " +
           "LIMIT 1",
           nativeQuery = true)
    ProvinciaGasolina95Projection findProvinciaConGasolina95E5MasCara();
}


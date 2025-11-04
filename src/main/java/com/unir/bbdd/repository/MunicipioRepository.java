package com.unir.bbdd.repository;

import com.unir.bbdd.entity.Municipio;
import com.unir.bbdd.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    Optional<Municipio> findByNombre(String nombre);
    Optional<Municipio> findByNombreAndProvincia(String nombre, Provincia provincia);
}


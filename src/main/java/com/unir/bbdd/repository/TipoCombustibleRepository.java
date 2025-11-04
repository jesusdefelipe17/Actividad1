package com.unir.bbdd.repository;

import com.unir.bbdd.entity.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoCombustibleRepository extends JpaRepository<TipoCombustible, Long> {
    Optional<TipoCombustible> findByCodigo(String codigo);
}


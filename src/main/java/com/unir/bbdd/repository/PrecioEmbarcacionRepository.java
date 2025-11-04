package com.unir.bbdd.repository;

import com.unir.bbdd.entity.PrecioEmbarcacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioEmbarcacionRepository extends JpaRepository<PrecioEmbarcacion, Long> {
}


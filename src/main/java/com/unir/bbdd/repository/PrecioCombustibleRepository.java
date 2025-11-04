package com.unir.bbdd.repository;

import com.unir.bbdd.entity.PrecioCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioCombustibleRepository extends JpaRepository<PrecioCombustible, Long> {
}


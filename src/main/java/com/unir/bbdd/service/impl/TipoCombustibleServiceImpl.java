package com.unir.bbdd.service.impl;

import com.unir.bbdd.entity.TipoCombustible;
import com.unir.bbdd.repository.TipoCombustibleRepository;
import com.unir.bbdd.service.TipoCombustibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCombustibleServiceImpl implements TipoCombustibleService {

    private final TipoCombustibleRepository tipoCombustibleRepository;

    @Override
    @Transactional
    public TipoCombustible getOrCreateTipoCombustible(String codigo, String nombre, String categoria) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del tipo de combustible no puede estar vacío");
        }

        return tipoCombustibleRepository.findByCodigo(codigo)
                .orElseGet(() -> {
                    TipoCombustible tipo = new TipoCombustible();
                    tipo.setCodigo(codigo);
                    tipo.setNombre(nombre);
                    tipo.setCategoria(categoria);
                    return tipoCombustibleRepository.save(tipo);
                });
    }

    @Override
    @PostConstruct
    @Transactional
    public void initializeTiposCombustible() {
        List<TipoCombustible> tipos = Arrays.asList(
            createTipo("GASOLINA_95_E5", "Gasolina 95 E5", "GASOLINA"),
            createTipo("GASOLINA_95_E10", "Gasolina 95 E10", "GASOLINA"),
            createTipo("GASOLINA_95_E5_PREMIUM", "Gasolina 95 E5 Premium", "GASOLINA"),
            createTipo("GASOLINA_98_E5", "Gasolina 98 E5", "GASOLINA"),
            createTipo("GASOLINA_98_E10", "Gasolina 98 E10", "GASOLINA"),
            createTipo("GASOLINA_95_E25", "Gasolina 95 E25", "GASOLINA"),
            createTipo("GASOLINA_95_E85", "Gasolina 95 E85", "GASOLINA"),
            createTipo("GASOLINA_RENOVABLE", "Gasolina Renovable", "RENOVABLE"),

            createTipo("GASOLEO_A", "Gasóleo A", "DIESEL"),
            createTipo("GASOLEO_PREMIUM", "Gasóleo Premium", "DIESEL"),
            createTipo("GASOLEO_B", "Gasóleo B", "DIESEL"),
            createTipo("GASOLEO_C", "Gasóleo C", "DIESEL"),
            createTipo("DIESEL_RENOVABLE", "Diésel Renovable", "RENOVABLE"),

            createTipo("BIOETANOL", "Bioetanol", "RENOVABLE"),
            createTipo("BIODIESEL", "Biodiésel", "RENOVABLE"),

            createTipo("GLP", "GLP (Gas Licuado del Petróleo)", "GAS"),
            createTipo("GNC", "GNC (Gas Natural Comprimido)", "GAS"),
            createTipo("GNL", "GNL (Gas Natural Licuado)", "GAS"),
            createTipo("BGNC", "BGNC (Biometano Comprimido)", "GAS"),
            createTipo("BGNL", "BGNL (Biometano Licuado)", "GAS"),

            createTipo("HIDROGENO", "Hidrógeno", "OTROS"),
            createTipo("ADBLUE", "AdBlue", "OTROS"),
            createTipo("METANOL", "Metanol", "OTROS"),
            createTipo("AMONIACO", "Amoníaco", "OTROS")
        );

        tipos.forEach(tipo -> {
            if (!tipoCombustibleRepository.findByCodigo(tipo.getCodigo()).isPresent()) {
                tipoCombustibleRepository.save(tipo);
            }
        });
    }

    private TipoCombustible createTipo(String codigo, String nombre, String categoria) {
        TipoCombustible tipo = new TipoCombustible();
        tipo.setCodigo(codigo);
        tipo.setNombre(nombre);
        tipo.setCategoria(categoria);
        return tipo;
    }
}


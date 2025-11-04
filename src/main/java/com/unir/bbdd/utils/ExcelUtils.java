package com.unir.bbdd.utils;

import com.unir.bbdd.entity.Embarcacion;
import com.unir.bbdd.entity.PrecioEmbarcacion;
import com.unir.bbdd.entity.TipoCombustible;
import com.unir.bbdd.repository.TipoCombustibleRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExcelUtils {

    public static String getString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    public static BigDecimal getBigDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return BigDecimal.valueOf(cell.getNumericCellValue());
                case STRING:
                    String value = cell.getStringCellValue().trim();
                    if (value.isEmpty()) {
                        return null;
                    }
                    value = value.replace(",", ".");
                    return new BigDecimal(value);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }


    public static LocalDateTime getDate(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            } else if (cell.getCellType() == CellType.STRING) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String determinarCategoria(String nombre) {
        if (nombre == null) {
            return "OTROS";
        }

        String nombreLower = nombre.toLowerCase();

        if (nombreLower.contains("gasolina")) {
            return "GASOLINA";
        }
        if (nombreLower.contains("gasóleo") || nombreLower.contains("gasoleo") || nombreLower.contains("diesel")) {
            return "DIESEL";
        }
        if (nombreLower.contains("renovable")) {
            return "RENOVABLE";
        }
        if (nombreLower.contains("gnc") || nombreLower.contains("gnl") ||
            nombreLower.contains("bgnc") || nombreLower.contains("bgnl")) {
            return "GAS";
        }

        return "OTROS";
    }

    public static String generateCodigoCombustible(String nombre) {
        if (nombre == null) {
            return null;
        }

        String nombreNormalizado = nombre.toLowerCase()
                .replace("precio ", "")
                .trim();

        return nombreNormalizado.toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U")
                .replace(" ", "_")
                .replace("/", "_")
                .replaceAll("[^A-Z0-9_]", "");
    }


    public static void addPrecioEmbarcacionIfPresent(
            List<PrecioEmbarcacion> precios,
            Embarcacion embarcacion,
            String codigo,
            String nombre,
            BigDecimal precio,
            LocalDateTime fechaPrecio,
            TipoCombustibleRepository tipoCombustibleRepository) {

        if (precio != null && precio.compareTo(BigDecimal.ZERO) > 0) {
            TipoCombustible tipoCombustible = tipoCombustibleRepository.findByCodigo(codigo)
                    .orElseGet(() -> {
                        TipoCombustible nuevo = new TipoCombustible();
                        nuevo.setCodigo(codigo);
                        nuevo.setNombre(nombre);
                        nuevo.setCategoria(determinarCategoria(nombre));
                        return tipoCombustibleRepository.save(nuevo);
                    });

            PrecioEmbarcacion precioEmbarcacion = new PrecioEmbarcacion();
            precioEmbarcacion.setEmbarcacion(embarcacion);
            precioEmbarcacion.setTipoCombustible(tipoCombustible);
            precioEmbarcacion.setPrecio(precio);
            precioEmbarcacion.setFechaPrecio(fechaPrecio);

            precios.add(precioEmbarcacion);
        }
    }


    public static List<PrecioEmbarcacion> crearPreciosEmbarcacion(
            Embarcacion embarcacion,
            BigDecimal precioGasolina95E5,
            BigDecimal precioGasolina95E10,
            BigDecimal precioGasolina95E25,
            BigDecimal precioGasolina95E85,
            BigDecimal precioGasolinaRenovable,
            BigDecimal precioGasoleoA,
            BigDecimal precioGasoleoB,
            BigDecimal precioGasoleoUsoMaritimo,
            BigDecimal precioDieselRenovable,
            BigDecimal precioAdBlue,
            BigDecimal precioMetanol,
            BigDecimal precioAmoniaco,
            BigDecimal precioBGNC,
            BigDecimal precioBGNL,
            LocalDateTime fechaPrecio,
            TipoCombustibleRepository tipoCombustibleRepository) {

        List<PrecioEmbarcacion> precios = new ArrayList<>();

        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLINA_95_E5", "Gasolina 95 E5",
                precioGasolina95E5, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLINA_95_E10", "Gasolina 95 E10",
                precioGasolina95E10, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLINA_95_E25", "Gasolina 95 E25",
                precioGasolina95E25, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLINA_95_E85", "Gasolina 95 E85",
                precioGasolina95E85, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLINA_RENOVABLE", "Gasolina Renovable",
                precioGasolinaRenovable, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLEO_A", "Gasóleo A",
                precioGasoleoA, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLEO_B", "Gasóleo B",
                precioGasoleoB, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "GASOLEO_USO_MARITIMO", "Gasóleo de Uso Marítimo",
                precioGasoleoUsoMaritimo, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "DIESEL_RENOVABLE", "Diésel Renovable",
                precioDieselRenovable, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "ADBLUE", "AdBlue",
                precioAdBlue, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "METANOL", "Metanol",
                precioMetanol, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "AMONIACO", "Amoníaco",
                precioAmoniaco, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "BGNC", "BGNC",
                precioBGNC, fechaPrecio, tipoCombustibleRepository);
        addPrecioEmbarcacionIfPresent(precios, embarcacion, "BGNL", "BGNL",
                precioBGNL, fechaPrecio, tipoCombustibleRepository);

        return precios;
    }
}


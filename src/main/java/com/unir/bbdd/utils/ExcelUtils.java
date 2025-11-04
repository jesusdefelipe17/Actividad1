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

}


package com.unir.bbdd.service.impl;

import com.unir.bbdd.entity.*;
import com.unir.bbdd.repository.*;
import com.unir.bbdd.service.*;
import com.unir.bbdd.utils.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExcelUploadServiceImpl implements ExcelUploadService {

    private final EstacionServicioRepository estacionServicioRepository;
    private final EmpresaService empresaService;
    private final ProvinciaService provinciaService;
    private final MunicipioService municipioService;
    private final TipoCombustibleService tipoCombustibleService;
    private final PrecioCombustibleRepository precioCombustibleRepository;
    private final EmbarcacionRepository embarcacionRepository;
    private final PrecioEmbarcacionRepository precioEmbarcacionRepository;

    @Override
    public void uploadEstacionesExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            deleteAllEstacionesData();

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = 0;

            Map<Integer, CombustibleHeader> combustibleHeaders = readCombustibleHeaders(sheet);

            for (Row row : sheet) {
                if (row.getRowNum() < 4) {
                    continue;
                }

                try {
                    processEstacionRow(row, combustibleHeaders);
                    rowCount++;
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo Excel: " + e.getMessage(), e);
        }
    }

    @Transactional
    private void processEstacionRow(Row row, Map<Integer, CombustibleHeader> combustibleHeaders) {
        String provinciaNombre = ExcelUtils.getString(row.getCell(0));
        String municipioNombre = ExcelUtils.getString(row.getCell(1));
        String localidad = ExcelUtils.getString(row.getCell(2));
        String codigoPostal = ExcelUtils.getString(row.getCell(3));
        String direccion = ExcelUtils.getString(row.getCell(4));
        String margen = ExcelUtils.getString(row.getCell(5));
        String longitud = ExcelUtils.getString(row.getCell(6));
        String latitud = ExcelUtils.getString(row.getCell(7));
        LocalDateTime tomaDatos = ExcelUtils.getDate(row.getCell(8));
        if (tomaDatos == null) {
            tomaDatos = LocalDateTime.now();
        }
        String rotulo = ExcelUtils.getString(row.getCell(35));
        String tipoVenta = ExcelUtils.getString(row.getCell(36));
        String rem = ExcelUtils.getString(row.getCell(37));
        String horario = ExcelUtils.getString(row.getCell(38));
        String tipoServicio = ExcelUtils.getString(row.getCell(39));

        Provincia provincia = null;
        if (provinciaNombre != null && !provinciaNombre.trim().isEmpty()) {
            provincia = provinciaService.getOrCreateProvincia(provinciaNombre);
        }

        Municipio municipio = null;
        if (municipioNombre != null && !municipioNombre.trim().isEmpty() && provincia != null) {
            municipio = municipioService.getOrCreateMunicipio(municipioNombre, provincia);
        }

        Empresa empresa = null;
        if (rotulo != null && !rotulo.trim().isEmpty()) {
            empresa = empresaService.getOrCreateEmpresa(rotulo).toEntity();
        }

        EstacionServicio estacion = new EstacionServicio();
        estacion.setMunicipio(municipio);
        estacion.setLocalidad(localidad);
        estacion.setCodigoPostal(codigoPostal);
        estacion.setDireccion(direccion);
        estacion.setMargen(margen);
        estacion.setLongitud(longitud);
        estacion.setLatitud(latitud);
        estacion.setTomaDatos(tomaDatos);
        estacion.setTipoVenta(tipoVenta);
        estacion.setRem(rem);
        estacion.setHorario(horario);
        estacion.setTipoServicio(tipoServicio);
        if (empresa != null) {
            estacion.setEmpresa(empresa);
        }

        estacion = estacionServicioRepository.save(estacion);

        if (estacion == null || estacion.getId() == null) {
            throw new RuntimeException("No se pudo guardar la estación correctamente");
        }

        final LocalDateTime fechaPrecioFinal = (tomaDatos != null) ? tomaDatos : LocalDateTime.now();

        procesarPreciosCombustibles(row, estacion, combustibleHeaders, fechaPrecioFinal);
    }

    private Map<Integer, CombustibleHeader> readCombustibleHeaders(Sheet sheet) {
        Map<Integer, CombustibleHeader> headers = new HashMap<>();

        String[] nombresCombustibles = {
            "Precio gasolina 95 E5", "Precio gasolina 95 E10", "Precio gasolina 95 E5 Premium",
            "Precio gasolina 98 E5", "Precio gasolina 98 E10", "Precio gasóleo A",
            "Precio gasóleo Premium", "Precio gasóleo B", "Precio gasóleo C",
            "Precio bioetanol", "% bioalcohol", "Precio biodiésel",
            "% éster metílico", "Precio gases licuados del petróleo", "Precio gas natural comprimido",
            "Precio gas natural licuado", "Precio hidrógeno", "Precio gasolina 95 E25",
            "Precio gasolina 95 E85", "Precio AdBlue", "Precio diesel renovable",
            "Precio gasolina renovable", "Precio metanol", "Precio amoniaco",
            "Precio BGNC", "Precio BGNL"
        };

        for (int i = 0; i < nombresCombustibles.length; i++) {
            int colIndex = 9 + i;
            String nombreCompleto = nombresCombustibles[i];
            CombustibleHeader header = parseCombustibleHeader(nombreCompleto, colIndex);
            if (header != null) {
                headers.put(colIndex, header);
            }
        }

        return headers;
    }

    private CombustibleHeader parseCombustibleHeader(String headerName, int columnIndex) {
        String nombreLimpio = headerName.trim();
        String codigo = ExcelUtils.generateCodigoCombustible(nombreLimpio);
        String categoria = ExcelUtils.determinarCategoria(nombreLimpio);

        boolean esPorcentaje = nombreLimpio.toLowerCase().contains("% ");

        return new CombustibleHeader(codigo, nombreLimpio, categoria, esPorcentaje, columnIndex);
    }



    private void procesarPreciosCombustibles(Row row, EstacionServicio estacion,
                                            Map<Integer, CombustibleHeader> headers,
                                            LocalDateTime fechaPrecio) {
        if (estacion == null || estacion.getId() == null) {
            return;
        }

        final LocalDateTime fechaFinal = (fechaPrecio != null) ? fechaPrecio : LocalDateTime.now();

        Map<String, BigDecimal> preciosPorCodigo = new HashMap<>();
        Map<String, BigDecimal> porcentajesPorCodigo = new HashMap<>();

        for (Map.Entry<Integer, CombustibleHeader> entry : headers.entrySet()) {
            CombustibleHeader header = entry.getValue();
            BigDecimal valor = ExcelUtils.getBigDecimal(row.getCell(header.columnIndex));

            if (valor != null) {
                if (header.esPorcentaje) {
                    String codigoBase = header.codigo.replace("_PORCENTAJE", "").replace("_PERCENT", "");
                    porcentajesPorCodigo.put(codigoBase, valor);
                } else {
                    preciosPorCodigo.put(header.codigo, valor);
                }
            }
        }

        for (Map.Entry<Integer, CombustibleHeader> entry : headers.entrySet()) {
            CombustibleHeader header = entry.getValue();

            if (header.esPorcentaje) {
                continue;
            }

            BigDecimal precio = preciosPorCodigo.get(header.codigo);

            if (precio != null && precio.compareTo(BigDecimal.ZERO) > 0) {
                try {
                    TipoCombustible tipoCombustible = tipoCombustibleService
                            .getOrCreateTipoCombustible(header.codigo, header.nombre, header.categoria);

                    PrecioCombustible precioCombustible = new PrecioCombustible();
                    precioCombustible.setEstacion(estacion);
                    precioCombustible.setTipoCombustible(tipoCombustible);
                    precioCombustible.setPrecio(precio);
                    precioCombustible.setFechaPrecio(fechaFinal);

                    BigDecimal porcentaje = porcentajesPorCodigo.get(header.codigo);
                    if (porcentaje != null) {
                        precioCombustible.setPorcentaje(porcentaje);
                    }

                    precioCombustibleRepository.save(precioCombustible);
                } catch (Exception e) {
                }
            }
        }
    }
    private static class CombustibleHeader {
        String codigo;
        String nombre;
        String categoria;
        boolean esPorcentaje;
        int columnIndex;

        CombustibleHeader(String codigo, String nombre, String categoria, boolean esPorcentaje, int columnIndex) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.categoria = categoria;
            this.esPorcentaje = esPorcentaje;
            this.columnIndex = columnIndex;
        }
    }

    @Override
    public void uploadEmbarcacionesExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            deleteAllEmbarcacionesData();

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = 0;

            Map<Integer, CombustibleHeader> combustibleHeaders = readCombustibleHeadersEmbarcaciones(sheet);

            for (Row row : sheet) {
                if (row.getRowNum() < 4) {
                    continue;
                }

                try {
                    processEmbarcacionRow(row, combustibleHeaders);
                    rowCount++;
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo Excel: " + e.getMessage(), e);
        }
    }

    @Transactional
    private void processEmbarcacionRow(Row row, Map<Integer, CombustibleHeader> combustibleHeaders) {
        String provinciaNombre = ExcelUtils.getString(row.getCell(0));
        String municipioNombre = ExcelUtils.getString(row.getCell(1));
        String nombreEmpresa = ExcelUtils.getString(row.getCell(2));
        String localidad = ExcelUtils.getString(row.getCell(3));
        String codigoPostal = ExcelUtils.getString(row.getCell(4));
        String direccion = ExcelUtils.getString(row.getCell(5));
        String longitud = ExcelUtils.getString(row.getCell(6));
        String latitud = ExcelUtils.getString(row.getCell(7));
        String rotulo = ExcelUtils.getString(row.getCell(22));
        String tipoVenta = ExcelUtils.getString(row.getCell(23));
        String rem = ExcelUtils.getString(row.getCell(24));
        String horario = ExcelUtils.getString(row.getCell(25));

        Provincia provincia = null;
        if (provinciaNombre != null && !provinciaNombre.trim().isEmpty()) {
            provincia = provinciaService.getOrCreateProvincia(provinciaNombre);
        }

        Municipio municipio = null;
        if (municipioNombre != null && !municipioNombre.trim().isEmpty() && provincia != null) {
            municipio = municipioService.getOrCreateMunicipio(municipioNombre, provincia);
        }


        Empresa empresa = null;
        if (rotulo != null && !rotulo.trim().isEmpty()) {
            empresa = empresaService.getOrCreateEmpresa(rotulo).toEntity();
        }

        Embarcacion embarcacion = new Embarcacion();
        embarcacion.setMunicipio(municipio);
        embarcacion.setLocalidad(localidad);
        embarcacion.setCodigoPostal(codigoPostal);
        embarcacion.setDireccion(direccion);
        embarcacion.setLongitud(longitud);
        embarcacion.setLatitud(latitud);
        embarcacion.setTipoVenta(tipoVenta);
        embarcacion.setRem(rem);
        embarcacion.setHorario(horario);
        if (empresa != null) {
            embarcacion.setEmpresa(empresa);
        }

        embarcacion = embarcacionRepository.save(embarcacion);

        if (embarcacion == null || embarcacion.getId() == null) {
            throw new RuntimeException("No se pudo guardar la embarcación correctamente");
        }

        LocalDateTime fechaPrecio = LocalDateTime.now();
        procesarPreciosEmbarcaciones(row, embarcacion, combustibleHeaders, fechaPrecio);
    }

    private Map<Integer, CombustibleHeader> readCombustibleHeadersEmbarcaciones(Sheet sheet) {
        Map<Integer, CombustibleHeader> headers = new HashMap<>();

        String[] nombresCombustibles = {
            "Precio gasolina 95 E5", "Precio gasolina 95 E10", "Precio gasóleo A",
            "Precio gasóleo B", "Precio gasóleo de uso marítimo", "Precio gasolina 95 E25",
            "Precio gasolina 95 E85", "Precio AdBlue", "Precio diesel renovable",
            "Precio gasolina renovable", "Precio metanol", "Precio amoniaco",
            "Precio BGNC", "Precio BGNL"
        };

        for (int i = 0; i < nombresCombustibles.length; i++) {
            int colIndex = 8 + i;
            String nombreCompleto = nombresCombustibles[i];
            CombustibleHeader header = parseCombustibleHeader(nombreCompleto, colIndex);
            if (header != null) {
                headers.put(colIndex, header);
            }
        }

        return headers;
    }

    private void procesarPreciosEmbarcaciones(Row row, Embarcacion embarcacion,
                                             Map<Integer, CombustibleHeader> headers,
                                             LocalDateTime fechaPrecio) {
        if (embarcacion == null || embarcacion.getId() == null) {
            return;
        }

        for (Map.Entry<Integer, CombustibleHeader> entry : headers.entrySet()) {
            CombustibleHeader header = entry.getValue();
            BigDecimal precio = ExcelUtils.getBigDecimal(row.getCell(header.columnIndex));

            if (precio != null && precio.compareTo(BigDecimal.ZERO) > 0) {
                try {
                    TipoCombustible tipoCombustible = tipoCombustibleService
                            .getOrCreateTipoCombustible(header.codigo, header.nombre, header.categoria);

                    PrecioEmbarcacion precioEmbarcacion = new PrecioEmbarcacion();
                    precioEmbarcacion.setEmbarcacion(embarcacion);
                    precioEmbarcacion.setTipoCombustible(tipoCombustible);
                    precioEmbarcacion.setPrecio(precio);
                    precioEmbarcacion.setFechaPrecio(fechaPrecio);

                    precioEmbarcacionRepository.save(precioEmbarcacion);
                } catch (Exception e) {
                }
            }
        }
    }

    @Transactional
    public void deleteAllEstacionesData() {
        precioCombustibleRepository.deleteAll();
        estacionServicioRepository.deleteAll();
    }

    @Transactional
    public void deleteAllEmbarcacionesData() {
        precioEmbarcacionRepository.deleteAll();
        embarcacionRepository.deleteAll();
    }
}


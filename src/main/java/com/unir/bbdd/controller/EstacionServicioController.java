package com.unir.bbdd.controller;

import com.unir.bbdd.dto.EstacionPrecioMinimoDto;
import com.unir.bbdd.service.EstacionServicioService;
import com.unir.bbdd.service.ExcelUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EstacionServicioController {

    private final EstacionServicioService estacionServicioService;
    private final ExcelUploadService excelUploadService;

    @GetMapping("/query1")
    public ResponseEntity<String> query1() {
        String empresaNombre = estacionServicioService.getEmpresaConMasEstaciones();
        return ResponseEntity.ok(empresaNombre);
    }

    @GetMapping("/query3")
    public ResponseEntity<EstacionPrecioMinimoDto> query2() {
        EstacionPrecioMinimoDto resultado = estacionServicioService.getEstacionPrecioMinimoGasolina95E5Madrid();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/query4")
    public ResponseEntity<EstacionPrecioMinimoDto> query3() {
        EstacionPrecioMinimoDto resultado = estacionServicioService.getEstacionPrecioMinimoGasoleoAAlbacete();
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/upload/estaciones")
    public ResponseEntity<String> uploadEstacionesExcel() {
        try {
            String filePath = "src/main/resources/preciosEESS_es.xls";
            excelUploadService.uploadEstacionesExcel(filePath);
            return ResponseEntity.ok("Archivo cargado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al cargar el archivo: " + e.getMessage());
        }
    }


}


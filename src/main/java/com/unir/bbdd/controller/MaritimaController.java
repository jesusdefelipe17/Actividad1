package com.unir.bbdd.controller;

import com.unir.bbdd.dto.ProvinciaGasolina95MasCaraDto;
import com.unir.bbdd.service.EmbarcacionService;
import com.unir.bbdd.service.ExcelUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MaritimaController {

    private final EmbarcacionService embarcacionService;
    private final ExcelUploadService excelUploadService;

    @GetMapping("/query2")
    public ResponseEntity<String> query2() {
        String empresaNombre = embarcacionService.getEmpresaConMasEmbarcaciones();
        return ResponseEntity.ok(empresaNombre);
    }

    @GetMapping("/query5")
    public ResponseEntity<ProvinciaGasolina95MasCaraDto> query5() {
        ProvinciaGasolina95MasCaraDto resultado = embarcacionService.getProvinciaConGasolina95E5MasCara();
        return ResponseEntity.ok(resultado);
    }


    @PostMapping("/upload/maritimas")
    public ResponseEntity<String> uploadEmbarcacionesExcel() {
        try {
            String filePath = "src/main/resources/EmbarcacionesPrecios_es.xls";
            excelUploadService.uploadEmbarcacionesExcel(filePath);
            return ResponseEntity.ok("Archivo cargado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al cargar el archivo: " + e.getMessage());
        }
    }

}


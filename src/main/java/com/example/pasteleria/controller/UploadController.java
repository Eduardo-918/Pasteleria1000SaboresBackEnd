package com.example.pasteleria.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío");
        }

        // Carpeta uploads (se crea si no existe)
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Nombre único: timestamp + nombre original
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = uploadDir.resolve(fileName);

        // Guardar archivo
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // URL pública que usarás en los productos
        String url = "http://localhost:8080/uploads/" + fileName;

        return ResponseEntity.ok(url);
    }
}

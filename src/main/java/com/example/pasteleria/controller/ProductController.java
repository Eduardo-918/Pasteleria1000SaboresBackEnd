package com.example.pasteleria.controller;

import com.example.pasteleria.model.product;
import com.example.pasteleria.repository.ProductRepository;
import com.example.pasteleria.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:5173") // Permite peticiones desde tu frontend
public class ProductController {

    @Autowired
    private final ProductService service;

    @Autowired
    private final ProductRepository repository;

    public ProductController(ProductService service, ProductRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    // ========== ENDPOINT ORIGINAL (con archivo MultipartFile) ==========
    @PostMapping("/create")
    public ResponseEntity<product> createProduct(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("price") int price,
            @RequestParam("image") MultipartFile image
    ) {
        return ResponseEntity.ok(
                service.createProduct(title, description, category, price, image)
        );
    }

    // ========== NUEVOS ENDPOINTS PARA PANEL ADMIN (JSON) ==========

    // Crear producto simple (solo URL como texto) - Para panel admin
    @PostMapping
    public ResponseEntity<product> createProductSimple(@RequestBody product producto) {
        product newProduct = new product();
        newProduct.setTitle(producto.getTitle());
        newProduct.setDescription(producto.getDescription());
        newProduct.setCategory(producto.getCategory());
        newProduct.setPrice(producto.getPrice());
        newProduct.setImageUrl(producto.getImageUrl());

        product saved = repository.save(newProduct);
        return ResponseEntity.ok(saved);
    }

    // Actualizar producto - Para panel admin
    @PutMapping("/{id}")
    public ResponseEntity<product> updateProduct(@PathVariable long id, @RequestBody product producto) {
        product existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        existing.setTitle(producto.getTitle());
        existing.setDescription(producto.getDescription());
        existing.setCategory(producto.getCategory());
        existing.setPrice(producto.getPrice());
        existing.setImageUrl(producto.getImageUrl());

        product updated = repository.save(existing);
        return ResponseEntity.ok(updated);
    }

    // ========== ENDPOINTS COMUNES ==========

    @GetMapping
    public ResponseEntity<List<product>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<product> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
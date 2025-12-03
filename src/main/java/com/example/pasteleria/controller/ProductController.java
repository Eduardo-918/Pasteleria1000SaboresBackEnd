package com.example.pasteleria.controller;

import com.example.pasteleria.model.product;
import com.example.pasteleria.repository.ProductRepository;
import com.example.pasteleria.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Productos", description = "Operaciones CRUD para productos de la pastelería")
public class ProductController {

    @Autowired
    private  ProductService service;

    @Autowired
    private  ProductRepository repository;

    @PostMapping("/create")
    @Operation(
            summary = "Crear producto con imagen (MultipartFile)",
            description = "Crea un producto enviando sus datos por formulario y subiendo una imagen."
    )
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

    @Operation(
            summary = "Crear producto (JSON)",
            description = "Crea un producto enviando los datos en formato JSON, solo con URL de imagen."
    )
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

    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza un producto existente mediante su ID."
    )
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

    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve una lista con todos los productos disponibles."
    )
    @GetMapping
    public ResponseEntity<List<product>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }


    @Operation(
            summary = "Obtener un producto por ID",
            description = "Devuelve un producto específico mediante su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<product> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto del sistema mediante su ID."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
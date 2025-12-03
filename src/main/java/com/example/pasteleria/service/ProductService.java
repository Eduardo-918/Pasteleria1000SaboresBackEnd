package com.example.pasteleria.service;

import com.example.pasteleria.model.product;
import com.example.pasteleria.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository repository;
    @Autowired
    private final FileStorageService fileService;

    public ProductService(ProductRepository repository, FileStorageService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    public product createProduct(String title,
                                 String description,
                                 String category,
                                 int price,
                                 MultipartFile image) {

        try {
            String fileName = fileService.saveFile(image);

            String imageUrl = "http://localhost:8080/uploads/" + fileName;

            product product = new product();
            product.setTitle(title);
            product.setDescription(description);
            product.setCategory(category);
            product.setPrice(price);
            product.setImageUrl(imageUrl);

            return repository.save(product);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear producto: " + e.getMessage());
        }
    }

    public List<product> findAll() {
        return repository.findAll();
    }

    public product findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void deleteProduct(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }
}

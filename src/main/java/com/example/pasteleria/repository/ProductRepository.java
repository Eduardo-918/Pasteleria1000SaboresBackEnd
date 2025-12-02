package com.example.pasteleria.repository;

import com.example.pasteleria.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<product, Long> {
}

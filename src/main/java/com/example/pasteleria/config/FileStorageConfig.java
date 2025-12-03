package com.example.pasteleria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        String uploadDir = "file:" + uploadPath.toString() + "/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir);

        System.out.println(">>> Sirviendo archivos desde: " + uploadDir);
    }
}
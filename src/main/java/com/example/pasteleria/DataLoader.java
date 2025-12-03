package com.example.pasteleria;

import com.example.pasteleria.model.Usuario;
import com.example.pasteleria.model.product;
import com.example.pasteleria.repository.ProductRepository;
import com.example.pasteleria.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataLoader {


    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {

            if (!repository.existsByCorreo("F.sepulveda@gmail.com")) {

                Usuario admin = new Usuario();
                admin.setRut("21.111.111-1");
                admin.setNombre("Administrador");
                admin.setApellido("Mil Sabores");
                admin.setCorreo("admin@milsabores.com");

                admin.setPassword(encoder.encode("123456"));

                admin.setTelefono("+56951729053");
                admin.setFechaNacimiento(LocalDate.of(2006, 12, 25));
                admin.setDireccion("Av siempre viva 123");
                admin.setTipoUsuario("Admin");

                repository.save(admin);

                System.out.println("✔ Usuario ADMIN creado");
            }
        };
    }

    @Bean
    CommandLineRunner initDatabas(ProductRepository repository) {
        return args -> {

            // Limpiar la base de datos
            System.out.println(">>> Limpiando base de datos...");
            repository.deleteAll();

            System.out.println(">>> Cargando 10 productos...");
            Usuario admin = new Usuario();
            admin.setRut("21.111.111-1");
            admin.setNombre("Fernando");
            admin.setApellido("Sepulveda");
            admin.setCorreo("F.sepulveda@gmail.com");
            admin.setPassword("admin123");
            admin.setTelefono("+56951729053");
            admin.setFechaNacimiento(LocalDate.of(2006, 12, 25));
            admin.setDireccion("Av siempre viva 123");
            admin.setTipoUsuario("Admin");

            product p1 = new product();
            p1.setTitle("Cheesecake Sin Azúcar");
            p1.setDescription("Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.");
            p1.setCategory("Productos Sin Azúcar");
            p1.setPrice(47000);
            p1.setImageUrl("http://localhost:8080/uploads/descarga.jpg");

            product p2 = new product();
            p2.setTitle("Tarta de Santiago");
            p2.setDescription("Tradicional tarta española hecha con almendras, azúcar y huevos, una delicia para los amantes de los postres clásicos.");
            p2.setCategory("Pastelería Tradicional");
            p2.setPrice(6000);
            p2.setImageUrl("http://localhost:8080/uploads/Torta-de-santiago-1fp.jpg");

            product p3 = new product();
            p3.setTitle("Tiramisú Clásico");
            p3.setDescription("Un postre italiano individual con capas de café, mascarpone y cacao, perfecto para finalizar cualquier comida.");
            p3.setCategory("Postres Individuales");
            p3.setPrice(5500);
            p3.setImageUrl("http://localhost:8080/uploads/Classic_Tiramisu_L.jpg");

            product p4 = new product();
            p4.setTitle("Torta Circular de Manjar");
            p4.setDescription("Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces y clásicos.");
            p4.setCategory("Tortas Circulares");
            p4.setPrice(42000);
            p4.setImageUrl("http://localhost:8080/uploads/descarga(1).jpg");

            product p5 = new product();
            p5.setTitle("Galletas Veganas de Avena");
            p5.setDescription("Crujientes y sabrosas, estas galletas son una excelente opción para un snack saludable y vegano.");
            p5.setCategory("Productos Veganos");
            p5.setPrice(4500);
            p5.setImageUrl("http://localhost:8080/uploads/galletas-de-avena-veganas.jpg");

            product p6 = new product();
            p6.setTitle("Empanada de Manzana");
            p6.setDescription("Pastelería tradicional rellena de manzanas especiadas, perfecta para un dulce desayuno o merienda.");
            p6.setCategory("Pastelería Tradicional");
            p6.setPrice(3000);
            p6.setImageUrl("http://localhost:8080/uploads/empanada.jpg");

            product p7 = new product();
            p7.setTitle("Mousse de Chocolate");
            p7.setDescription("Postre individual cremoso y suave, hecho con chocolate de alta calidad, ideal para los amantes del chocolate.");
            p7.setCategory("Postres Individuales");
            p7.setPrice(5000);
            p7.setImageUrl("http://localhost:8080/uploads/mousse-de-chocolate-casera.jpg");

            product p8 = new product();
            p8.setTitle("Torta Especial de Boda");
            p8.setDescription("Elegante y deliciosa, esta torta está diseñada para ser el centro de atención en cualquier boda.");
            p8.setCategory("Tortas Especiales");
            p8.setPrice(60000);
            p8.setImageUrl("http://localhost:8080/uploads/images.jpg");

            product p9 = new product();
            p9.setTitle("Torta Sin Azúcar de Naranja");
            p9.setDescription("Torta ligera y deliciosa, endulzada naturalmente, ideal para quienes buscan opciones más saludables.");
            p9.setCategory("Productos Sin Azúcar");
            p9.setPrice(48000);
            p9.setImageUrl("http://localhost:8080/uploads/Torta-de-naranja-sin-azucar.jpg");

            product p10 = new product();
            p10.setTitle("Pan Sin Gluten");
            p10.setDescription("Suave y esponjoso, ideal para sándwiches o para acompañar cualquier comida.");
            p10.setCategory("Productos Sin Gluten");
            p10.setPrice(3500);
            p10.setImageUrl("http://localhost:8080/uploads/descarga(2).jpg");

            // Guardar todos
            repository.save(p1);
            repository.save(p2);
            repository.save(p3);
            repository.save(p4);
            repository.save(p5);
            repository.save(p6);
            repository.save(p7);
            repository.save(p8);
            repository.save(p9);
            repository.save(p10);

            long total = repository.count();
            System.out.println(">>> ✅ TOTAL productos guardados: " + total);

            List<product> todos = repository.findAll();
            System.out.println(">>> 📋 Lista de productos:");
            for (product p : todos) {
                System.out.println("    " + p.getId() + ". " + p.getTitle() + " - $" + p.getPrice());
            }
        };
    }
}
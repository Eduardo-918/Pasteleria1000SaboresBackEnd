package com.example.pasteleria.controller;

import com.example.pasteleria.DTO.LoginRequest;
import com.example.pasteleria.model.Usuario;
import com.example.pasteleria.security.JwtUtil;
import com.example.pasteleria.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios",description = "Obtiene una lista de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario",description = "Obtiene un usuario a traves del ID")
    @Parameter(description = "Id del usuario", required = true, name = "id")
    public ResponseEntity<Optional<Usuario>> getById(@PathVariable int id){
        return ResponseEntity.ok(usuarioService.findById(id));
    }
    @PostMapping
    @Operation(summary = "Crea un nuevo usuario", description = "Crea un usuario a traves de un objeto JSON")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
        //Inicializamos

        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        Usuario newUsuario = usuarioService.save(usuario);
        return new ResponseEntity<>(newUsuario, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Borra un usuario", description = "Borra un usuario a traves del ID")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.findByCorreo(request.getCorreo());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        boolean passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPassword());
        if (!passwordOk) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        // Ahora sí generamos el token
        String token = jwtUtil.generarToken(usuario);

        return ResponseEntity.ok(new HashMap<>() {{
            put("usuario", usuario);
            put("token", token);
        }});
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente")
    public ResponseEntity<Usuario> update(@PathVariable int id, @RequestBody Usuario usuario) {
        Optional<Usuario> existingOpt = usuarioService.findById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario existing = existingOpt.get();
        existing.setNombre(usuario.getNombre());
        existing.setApellido(usuario.getApellido());
        existing.setCorreo(usuario.getCorreo());
        existing.setRut(usuario.getRut());
        existing.setTelefono(usuario.getTelefono());
        existing.setDireccion(usuario.getDireccion());
        existing.setFechaNacimiento(usuario.getFechaNacimiento());
        existing.setTipoUsuario(usuario.getTipoUsuario());

        // Solo actualizar contraseña si se envió una nueva
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        Usuario updated = usuarioService.save(existing);
        return ResponseEntity.ok(updated);
    }


}

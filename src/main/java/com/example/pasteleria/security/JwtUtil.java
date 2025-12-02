package com.example.pasteleria.security;


import com.example.pasteleria.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
   private  int segundos=300;

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("tipoUsuario",usuario.getTipoUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *segundos)) // 1 hora
                .signWith(key)
                .compact();
    }
    public String validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
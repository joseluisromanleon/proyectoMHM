package org.iesvdm.mhm.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Rol;
import org.iesvdm.mhm.domain.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.function.Function;


@Slf4j
@Service
public class JwtService {

    // ¡IMPORTANTE! En producción, usa una clave secreta más segura y guárdala fuera del código fuente
    //private static final String SECRET_KEY = "miSuperClaveSegura";


    @Value("${jwt.secret}")
    private String secretKey;

    // Generar token JWT
    public String generateToken(Usuario usuario) {
        log.info("Generando token para usuario: {}", usuario.getUserName());
        log.info("Roles: {}", usuario.getRoles());
        log.info("Estado: {}", usuario.getEstadoUsuario());

        return Jwts.builder()
                .setSubject(usuario.getUserName())
                .claim("roles", usuario.getRoles().stream().map(Rol::getNombreRol).toList())
                .claim("estado", usuario.getEstadoUsuario().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    // Validar token
    public boolean validateToken(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return (username.equals(usuario.getUserName()) && !isTokenExpired(token));
    }

    // Extraer username del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer roles del token
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    // Extraer estado del token
    public String extractEstado(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("estado", String.class);
    }

    // Extraer fecha de expiración
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Métodos utilitarios
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }


}

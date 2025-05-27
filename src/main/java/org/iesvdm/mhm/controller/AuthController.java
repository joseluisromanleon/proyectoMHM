package org.iesvdm.mhm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Usuario;
import org.iesvdm.mhm.dto.LoginRequestDTO;
import org.iesvdm.mhm.service.JwtService;
import org.iesvdm.mhm.service.UsuarioService;
import org.iesvdm.mhm.domain.Rol;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200")  //esta Global SecurityConfig
@Slf4j
@RestController
@RequiredArgsConstructor
// Cambio el Constructor @Autowired  por  Lombok (** requiere final en los Atributos **)
@RequestMapping("/auth")
public class AuthController {


    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<Usuario> usuarioOpt = usuarioService.findByUsername(loginRequestDTO.username());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario o contraseña incorrectos"));
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(loginRequestDTO.password(), usuario.getUserPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario o contraseña incorrectos"));
        }

        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(Map.of(
                        "token", token,
                        "roles", usuario.getRoles().stream().map(Rol::getNombreRol).toList(),
                        "estado", usuario.getEstadoUsuario().name()
                ));


    }
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}













package org.iesvdm.mhm.controller;

import org.iesvdm.mhm.domain.Rol;
import org.iesvdm.mhm.dto.JwtDTO;
import org.iesvdm.mhm.dto.LoginRequestDTO;
import org.iesvdm.mhm.service.JwtService;
import org.iesvdm.mhm.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200") //esta Global  en SecurityConfig
public class JwtController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public JwtController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/jwt-login")
    public ResponseEntity<JwtDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // Busca el usuario por username
        return usuarioService.findByUsername(loginRequestDTO.username())
                .filter(usuario -> usuarioService.checkPassword(loginRequestDTO.password(), usuario.getUserPassword()))
                .map(usuario -> {
                    String token = jwtService.generateToken(usuario);
                    List<String> roles = usuario.getRoles().stream().map(Rol::getNombreRol).toList();
                    String estado = usuario.getEstadoUsuario().name();
                    JwtDTO response = new JwtDTO(token, roles, estado);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }

}

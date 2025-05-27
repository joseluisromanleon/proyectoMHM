package org.iesvdm.mhm.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Usuario;
import org.iesvdm.mhm.domain.Usuario.EstadoUsuario;
import org.iesvdm.mhm.dto.UsuarioDTO;
import org.iesvdm.mhm.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/usuarios")
public class UsuarioController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(BCryptPasswordEncoder passwordEncoder, UsuarioService usuarioService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }

    @PostMapping({"", "/"})
    public Usuario crearUsuario(@Valid @RequestBody UsuarioDTO dto) {
        log.info("Creando un Usuario nuevo");
        Usuario usuario = Usuario.builder()
                .userName(dto.userName())
                .userEmail(dto.userEmail())
                .userPassword(passwordEncoder.encode(dto.userPassword()))
                .aceptaCondiciones(dto.userAceptaCondiciones())
                .estadoUsuario(EstadoUsuario.PENDIENTE)
                .fecha(LocalDateTime.now())
                .build();

        usuarioService.save(usuario);

        //return ResponseEntity.ok().build();  // no se ve en Insomnia la devolucion
        return this.usuarioService.save(usuario);
    }


    @GetMapping("/{id}")
    public Usuario one(@PathVariable("id") Long id) {
        log.info("Buscando Usuario con id "+id);
        return this.usuarioService.one(id);
    }

    @PutMapping("/{id}")
    public Usuario replaceUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        log.info("Actualizando Usuario con id "+ id);
        return this.usuarioService.replace(id, usuario);
    }

    @DeleteMapping({"{id}","/{id}"})
    public void deleteUsuario(@PathVariable("id") Long id) {
        log.info("Eliminando Usuario con id "+id);
        this.usuarioService.delete(id);
    }

    @GetMapping(value = {"", "/"})
    public List<Usuario> all() {
        log.info("Accediendo a todos los  Usuarios");
        return this.usuarioService.all();
    }

}

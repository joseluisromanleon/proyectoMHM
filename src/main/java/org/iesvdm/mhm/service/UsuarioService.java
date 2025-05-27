package org.iesvdm.mhm.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.mhm.domain.Rol;
import org.iesvdm.mhm.domain.Usuario;
import org.iesvdm.mhm.exception.UsuarioNotFoundException;
import org.iesvdm.mhm.repository.RolRepository;
import org.iesvdm.mhm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UsuarioService {
    private final RolRepository rolRepository;

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // para las consultas dinamicas  en una  logica de control para multiconsultas
    @PersistenceContext
    EntityManager em;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, BCryptPasswordEncoder passwordEncoder1,
                          RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder1;
        this.rolRepository = rolRepository;
    }

    public Usuario one(Long id) {
        return this.usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    public Usuario replace(Long id, Usuario usuario) {
        return this.usuarioRepository.findById(id).map( c -> (id.equals(usuario.getIdUser()) ?
                        this.usuarioRepository.save(usuario) : null))
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    @Transactional
    public void delete(Long usuarioId) {
        log.info("Eliminando usuario con ID: " + usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Elimina las asociaciones en la tabla intermedia
        usuario.getRoles().clear();  // Rompe la relación con los roles
        usuarioRepository.save(usuario);  // Opcional (algunos casos requieren flush)

        // Ahora borra el usuario
        usuarioRepository.delete(usuario);
    }

    public List<Usuario> all(){
        log.info("listado de todos los usuarios");
        return  this.usuarioRepository.findAll();
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        // Si el usuario no tiene roles, le asignamos el rol por defecto
        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            Rol visitanteRol = rolRepository.findRolByNombreRol("ROLE_VISITANTE")
                    .orElse(new Rol("ROLE_VISITANTE")); // Asegúrate de tener un constructor adecuado
            usuario.setRoles(new HashSet<>(Set.of(visitanteRol)));
            log.info("Asignado rol por defecto: {}", visitanteRol.getNombreRol());
        }

        this.usuarioRepository.save(usuario);
        this.em.refresh(usuario);
        return usuario;
    }

    // Metodo que chequeara el password
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    public Optional<Usuario> findByUsername (String nombre) {
        return this.usuarioRepository.findByUserName(nombre);
    }


    public Page<Usuario> getAll(Pageable pageable) {
        return this.usuarioRepository.findAll(pageable);
    }



}



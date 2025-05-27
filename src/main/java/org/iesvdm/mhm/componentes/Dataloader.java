package org.iesvdm.mhm.componentes;

import org.iesvdm.mhm.domain.Rol;
import org.iesvdm.mhm.domain.Usuario;
import org.iesvdm.mhm.domain.Usuario.EstadoUsuario;
import org.iesvdm.mhm.repository.RolRepository;
import org.iesvdm.mhm.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Dataloader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Dataloader(RolRepository rolRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        // Carga en Lotes de Roles
        List<Rol> roles = List.of(              // Convención: los nombres de roles en mayúsculas y con "ROLE_"
                crearRol("ROLE_ADMIN"),
                crearRol("ROLE_COMERCIAL"),
                crearRol("ROLE_MECANICO"),
                crearRol("ROLE_VISITANTE"),
                crearRol("ROLE_CLIENTE"),
                crearRol("ROLE_CLIENTE_VIP_1"),
                crearRol("ROLE_CLIENTE_VIP_2")
        );

        // Guardar todos los usuarios (solo si no existe)
        roles.stream()
                .filter(u -> rolRepository.findRolByNombreRol(u.getNombreRol()).isEmpty())
                .forEach(rolRepository::save);



        // Carga en Lotes de Usuarios
        List<Usuario> usuarios = List.of(
                crearUsuario("admin", "password", "admin@empresa.com", "ROLE_ADMIN", roles),

                crearUsuario("comercial1", "1234", "comercial1@empresa.com", "ROLE_COMERCIAL", roles),
                crearUsuario("comercial2", "1234", "comercial1@empresa.com", "ROLE_COMERCIAL", roles),
                crearUsuario("comercial3", "1234", "comercial1@empresa.com", "ROLE_COMERCIAL", roles),

                crearUsuario("mecanico1", "1234", "mecanico1@empresa.com", "ROLE_MECANICO", roles),
                crearUsuario("mecanico2", "1234", "mecanico2@empresa.com", "ROLE_MECANICO", roles),
                crearUsuario("mecanico3", "1234", "mecanico3@empresa.com", "ROLE_MECANICO", roles),

                crearUsuario("cliente1", "1234", "cliente1@empresa.com", "ROLE_CLIENTE", roles),
                crearUsuario("cliente2", "1234", "cliente2@empresa.com", "ROLE_CLIENTE", roles),
                crearUsuario("cliente3", "1234", "cliente3@empresa.com", "ROLE_CLIENTE", roles),

                crearUsuario("clientevip1", "1234", "clientevip1@empresa.com", "ROLE_CLIENTE_VIP_1", roles),
                crearUsuario("clientevip2", "1234", "clientevip2@empresa.com", "ROLE_CLIENTE_VIP_1", roles),
                crearUsuario("clientevip3", "1234", "clientevip3@empresa.com", "ROLE_CLIENTE_VIP_1", roles)
        );

        // Guardar todos los usuarios (solo si no existe)
        usuarios.stream()
                .filter(u -> usuarioRepository.findByUserName(u.getUserName()).isEmpty())
                .forEach(usuarioRepository::save);
    }

    // ****  Método auxiliar para crear roles ****
    private Rol crearRol(String rolNombre) {

        Rol rol = new Rol();
        rol.setNombreRol(rolNombre);


        return rol;
    }

    // **** Método auxiliar para crear usuarios ****
    private Usuario crearUsuario(String username,
                                 String password,
                                 String email,
                                 String rolNombre,
                                 List<Rol> roles) {
        Rol rol = roles.stream()
                .filter(r -> r.getNombreRol().equals(rolNombre))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        Usuario usuario = new Usuario();
        usuario.setUserName(username);
        usuario.setUserPassword(passwordEncoder.encode(password));
        usuario.setUserEmail(email);
        usuario.setEnabled(true);
        usuario.setAceptaCondiciones(true);
        usuario.setEstadoUsuario(EstadoUsuario.ACEPTADO);
        usuario.setRoles(new HashSet<>(Set.of(rol)));
        // Asigna un solo rol (o puedes permitir múltiples)

        return usuario;
    }

}

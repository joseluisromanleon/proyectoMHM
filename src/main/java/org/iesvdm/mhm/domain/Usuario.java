package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "usuarios")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //solo los que tienen include
public class Usuario {

    public enum EstadoUsuario {
        PENDIENTE,
        ACEPTADO,
        RECHAZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "user_name", unique = true, nullable = false, length = 50)
    private String userName;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword; // Hasheada (BCrypt)

    @Column(name = "user_email", length = 100)
    private String userEmail;

    @Column(name = "fecha")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "acepta_condiciones")
    private Boolean aceptaCondiciones;

    @JoinColumn(name = "estado_user")
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estadoUsuario; // Ej: PENDIENTE, ACEPTADO, RECHAZADO

    // Relación con roles (muchos a muchos)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    //@OnDelete(action = OnDeleteAction.CASCADE) // Borra en cascada las filas de la tabla intermedia
    // realizado en el servicio (Mas control)
    private Set<Rol> roles;


}

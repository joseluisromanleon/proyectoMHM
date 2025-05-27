package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.iesvdm.mhm.notations.EmailValid;

import java.time.LocalDateTime;

@Data
@Table(name="mensajes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Mensaje {

    public enum EstadoMensaje {
        PENDIENTE,
        ACEPTADO,
        RECHAZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    @EqualsAndHashCode.Include
    private Long idMensaje;

    @Size(min = 3, max = 50)
    @Column(name = "nombre_empresa", length = 50)
    private String nombreEmpresa;

    @Size(min = 3, max = 100)
    @Column(name = "direccion_empresa", length = 100)
    private String direccionEmpresa;

    @Size(min = 9, max = 9)
    @Column(name = "tel_empresa", length = 9)
    private String telEmpresa;

    @EmailValid //Anotacion Personalizada Basica
    @Column(name = "email_empresa")
    private String emailEmpresa;

    @Size(min = 3, max = 50)
    @Column(name = "nombre_contacto",length = 50)
    private String nombreContacto;

    @Size(min = 9, max = 12)
    @Column(name = "tel_contacto", length = 12)
    private String telContacto;

    @EmailValid // Anotacion Personalizada Basica
    @Column(name = "email_contacto")
    private String emailContacto;

    @Size(min = 3, max = 256)
    @Column(name = "observaciones", length = 256)
    private String observaciones;

    @Column(name = "fecha")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private LocalDateTime fecha = LocalDateTime.now();

    @JoinColumn(name = "estado_msje")
    @Enumerated(EnumType.STRING)
    private EstadoMensaje estadoMsje; // Ej: PENDIENTE, ACEPTADO, RECHAZADO

    @Column(name = "acepta_condiciones")
    private Boolean aceptaCondiciones;


    // ******** CONSTRUCTOR PARA TESTS **************
    public Mensaje(long id, String nombre, String telefonoContacto) {
        this.idMensaje = id;
        this.nombreEmpresa = nombre;
        this.telContacto = telefonoContacto;
        this.observaciones = getObservaciones();
        this.fecha = LocalDateTime.now();
    }

}
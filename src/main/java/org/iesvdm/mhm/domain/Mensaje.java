package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.iesvdm.mhm.notations.EmailValid;

import java.util.Date;

/*
Si utilizo @OneToMany(FetchType.LAZY) además debo usar
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
 Para evitar que se envíe información de serialización
 JSON sobre los handler e hibernateLazyInitializer
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
*/
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

    @Size(min = 5, max = 5)
    @Column(name = "cp_empresa", length = 5)
    private String cpEmpresa;

    @Size(min = 24, max = 24)
    @Column(name = "iban_empresa", length = 24)
    private String IBANEmpresa;

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
    @Column(name = "tel_contacto", length = 9)
    private String telContacto;

    @EmailValid // Anotacion Personalizada Basica
    @Column(name = "email_contacto")
    private String emailContacto;

    @Size(min = 1, max = 1)
    @Column(name = "rol_id", length = 1)
    private Long rolId;

    @Size(min = 1, max = 1)
    @Column(name = "comercial_id", length = 1)
    private Long comercialId;

    @Size(min = 1, max = 1)
    @Column(name = "mecanico_id", length = 1)
    private Long mecanicoId;

    @Size(min = 0, max = 256)
    @Column(name = "observaciones", length = 256)
    private String observaciones;

    @Column(name = "fecha")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date Fecha;

    @JoinColumn(name = "estado_msje")
    @Enumerated(EnumType.STRING)
    private EstadoMensaje estadoMsje; // Ej: PENDIENTE, ACEPTADO, RECHAZADO

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id") // Nombre de la columna en la tabla 'mensajes'
    private Cliente cliente;


    // ******** CONSTRUCTOR PARA TESTS **************
    public Mensaje(long id, String nombre) {
        this.idMensaje = id;
        this.nombreEmpresa = nombre;
        this.observaciones = getObservaciones();
        this.comercialId = getComercialId();
    }

}
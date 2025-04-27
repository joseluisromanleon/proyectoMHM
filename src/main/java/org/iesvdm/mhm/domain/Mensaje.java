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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    @EqualsAndHashCode.Include
    private Long id_mensaje;

    @Size(min = 50, max = 50)
    @Column(name = "nombre_empresa", length = 50)
    private String nombre_empresa;

    @Size(min = 100, max = 100)
    @Column(name = "direccion_empresa", length = 100)
    private String direccion_empresa;

    @Size(min = 5, max = 5)
    @Column(name = "cp_empresa", length = 5)
    private String cp_empresa;

    @Size(min = 24, max = 24)
    @Column(name = "iban_empresa", length = 24)
    private String IBAN_empresa;

    @Size(min = 9, max = 9)
    @Column(name = "tel_empresa", length = 9)
    private String tel_empresa;

    @EmailValid //Anotacion Personalizada Basica
    @Column(name = "email_empresa")
    private String email_empresa;

    @Size(min = 3, max = 50)
    @Column(name = "nombre_contacto",length = 50)
    private String nombre_contacto;

    @Size(min = 9, max = 12)
    @Column(name = "tel_contacto", length = 9)
    private String tel_contacto;

    @EmailValid // Anotacion Personalizada Basica
    @Column(name = "email_contacto")
    private String email_contacto;

    @Size(min = 1, max = 1)
    @Column(name = "rol_id", length = 1)
    private Long rol_id;

    @Size(min = 1, max = 1)
    @Column(name = "comercial_id", length = 1)
    private Long comercial;

    @Size(min = 1, max = 1)
    @Column(name = "mecanico_id", length = 1)
    private Long mecanico;

    @Size(min = 0, max = 256)
    @Column(name = "observaciones", length = 256)
    private String observaciones;

    @Column(name = "ultima_actualizacion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date ultimaActualizacion;



    // ******** CONSTRUCTOR PARA TESTS **************
    public Mensaje(long id, String nombre) {
        this.id_mensaje = id;
        this.nombre_empresa = nombre;
        this.observaciones = getObservaciones();
        this.comercial = getComercial();
    }

}
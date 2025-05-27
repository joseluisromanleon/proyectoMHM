package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.iesvdm.mhm.notations.EmailValid;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/*
Si utilizo @OneToMany(FetchType.LAZY) además debo usar
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
 Para evitar que se envíe información de serialización
 JSON sobre los handler e hibernateLazyInitializer
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
*/
@Data
@Table(name="clientes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    @EqualsAndHashCode.Include
    private Long idCliente;

    // Composicion para Usuario (equivale a una Herencia)
    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

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
    private String IbanEmpresa;

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

    @Size(min = 1, max = 1)
    @Column(name = "rol_id", length = 1)
    private Long rolId;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    @ToStringExclude    //Rompe el lazo de Serializacion
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Pedido> pedidos;

    // un cliente puede ser atendido por varios empleados bidireccional
    @ManyToMany(mappedBy = "clientes",
            cascade = { CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"clientes","pedidos"})         //Rompe el lazo de Serializacion
    @ToStringExclude    //Rompe el lazo de Serializacion
    private Set<Empleado> empleados;

    @Column(name = "fecha_alta")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private LocalDateTime fecha_alta = LocalDateTime.now();

    @Size(min = 0, max = 256)
    @Column(name = "observaciones", length = 256)
    private String observaciones;



    // ******** CONSTRUCTOR PARA TESTS **************
    public Cliente(long id, String nombre) {
        this.idCliente = id;
        this.nombreEmpresa = nombre;
        this.pedidos = new HashSet<>();
        this.empleados = new HashSet<>();
        this.fecha_alta = LocalDateTime.now();
    }
}

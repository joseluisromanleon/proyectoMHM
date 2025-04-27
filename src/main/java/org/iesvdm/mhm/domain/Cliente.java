package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.iesvdm.mhm.notations.EmailValid;

import java.util.Date;
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
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    @EqualsAndHashCode.Include
    private Long id;

    private String nombre;
    private String direccion;

    @Size(min = 5, max = 5)
    @Column(name = "cp", length = 5)
    private String cp;

    @Size(min = 24, max = 24)
    @Column(name = "iban_empresa", length = 24)
    private String IBAN_empresa;

    @Size(min = 9, max = 9)
    @Column(name = "tel_empresa", length = 9)
    private String tel_empresa;

    @EmailValid //Anotacion Personalizada Basica
    @Column(name = "email_empresa")
    private String email_empresa;

    private String nombre_contacto;

    @Size(min = 9, max = 9)
    @Column(name = "tel_contacto", length = 9)
    private String tel_contacto;

    @EmailValid // Anotacion Personalizada Basica
    private String email_contacto;

    @Size(min = 256, max = 256)
    @Column(name = "observaciones", length = 256)
    private String observaciones;

    private long rol_id;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    @ToStringExclude    //Rompe el lazo de Serializacion
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Pedido> pedidos;

    @ManyToMany(mappedBy = "clientes", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    },fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"clientes", "empleados", "pedidos"})         //Rompe el lazo de Serializacion
    @ToStringExclude    //Rompe el lazo de Serializacion
    private Set<Empleado> empleados;

    @Column(name = "fecha_alta")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date fecha_alta;


    // ******** CONSTRUCTOR PARA TESTS **************
    public Cliente(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.pedidos = new HashSet<>();
        this.empleados = new HashSet<>();
    }

}

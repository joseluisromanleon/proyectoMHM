package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

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
@Table(name="cliente")
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
    private String cp;
    private String ccc_empresa;
    private String tel_empresa;
    private String email_empresa;
    private String nombre_contacto;
    private String tel_contacto;
    private String email_contacto;
    private String observaciones;

    @ManyToMany(mappedBy = "clientes", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    },fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"clientes", "empleados", "pedidos"})         //Rompe el lazo de Serializacion
    @ToStringExclude    //Rompe el lazo de Serializacion
    private Set<Empleado> empleados;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    @ToStringExclude    //Rompe el lazo de Serializacion
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Pedido> pedidos;

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

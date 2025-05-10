package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.iesvdm.mhm.notations.EmailValid;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "empleados")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //solo los que tienen include

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    @EqualsAndHashCode.Include
    private long id;
    private String nombre;
    private String apellidos;
    private String direccion;

    @Size(min = 9, max = 9)
    @Column(name = "telefono", length = 9)
    private String telefono;

    @Column(name = "email_empl")
    @EmailValid
    private String Email_Empl;

    @Size(min = 24, max = 24)
    @Column(name = "iban_empleado", length = 24)
    private String IBAN_empleado;

    @Column(name = "fecha_alta")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date fecha_alta;

    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  //Rompe el lazo de Serializacion
    @ToStringExclude                                                //Rompe el lazo de Serializacion
    @JsonBackReference
    Set<Pedido> pedidos = new HashSet<>();

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "empleado_clientes",
            joinColumns = @JoinColumn (name = "id_empleado"),
            inverseJoinColumns = @JoinColumn(name = "id_cliente"))
    @JsonIgnoreProperties({"clientes","empleados"}) //Rompe el lazo de Serializacion
    @ToStringExclude                                //Rompe el lazo de Serializacion
    Set<Cliente> clientes = new HashSet<>();

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "empleado_roles",
        joinColumns = @JoinColumn (name = "empleado_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id"))
    @JsonIgnoreProperties({"empleados", "roles"})   //Rompe el lazo de Serializacion
    @ToStringExclude                                //Rompe el lazo de Serializacion
    Set<Rol> roles; // Un empleado tiene mas de un solo rol


    // ******* CONSTRUCTORES PARA TESTS *********
    public Empleado(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.pedidos = new HashSet<Pedido>();
    }

    public Empleado(String nombre, String apellidos,  Date fecha_alta) {
        this.id = 0;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.fecha_alta = fecha_alta;
        this.pedidos = new HashSet<Pedido>();
    }
}

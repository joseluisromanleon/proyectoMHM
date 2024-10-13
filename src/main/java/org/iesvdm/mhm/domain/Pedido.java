package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import com.fasterxml.jackson.annotation.JsonBackReference;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="pedido")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
    /*@Transient
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");*/
    @Id
    @Column(name="id_pedido")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = true) //nulable para tests
    @ToString.Exclude
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = true) //nulable para tests
    @ToString.Exclude
    private Empleado empleado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType
            .ALL, fetch = FetchType.EAGER)
    @ToStringExclude  //Para Romper el lazo y bucle
    @JsonBackReference //solo en los one   *********
    private Set<DetallePedProd> productos = new HashSet<>();

    @Transient
    private double importeTotal = 0;
    @Column(name = "fecha_pedido")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date fecha_pedido;
    @Column(name = "facturado")
    private boolean facturado;
    @Column(name = "fecha_factura")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss",  shape = JsonFormat.Shape.STRING)
    private Date fecha_factura;
    @Transient
    private int unidadesVendidas;
    @Column(name = "enviado")
    private boolean enviado;
    private String observaciones;

    //Constructores `para tests
    public <E> Pedido(long id, Cliente cliente, HashSet<DetallePedProd>  productos) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
    }
    public Pedido(long id, Cliente cliente, Empleado empleado, HashSet<DetallePedProd>  productos) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.productos = productos;
    }
    public Pedido(int id, Cliente cliente, Empleado empleado) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    //Constructor Pedido Productos



}

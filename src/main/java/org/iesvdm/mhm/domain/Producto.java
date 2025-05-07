package org.iesvdm.mhm.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto {

    @Id
    @Column(name = "id_producto")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @EqualsAndHashCode.Include
    private String nombre;


//    @ManyToOne
//    @JoinColumn(name = "id_categoria", nullable = false) //FK para la relaccion
//    private Contacto categoria;


    @Column(name = "voltaje_v")
    @EqualsAndHashCode.Include
    private int voltaje;

    @Column(name = "potencia_w")
    @EqualsAndHashCode.Include
    private int potencia;

    @Column(name = "capacidad_l")
    @EqualsAndHashCode.Include
    private String capacidad;

    @Column(name = "alto")
    @EqualsAndHashCode.Include
    private Float alto;

    @Column(name = "ancho")
    @EqualsAndHashCode.Include
    private Float ancho;

    @Column(name = "fondo")
    @EqualsAndHashCode.Include
    private Float fondo;

    @ManyToOne
    @JoinColumn(name = "categoria_id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "proveedor_id_proveedor")
    private Proveedor proveedor;

    private long stock;
    private long stockMinimo;
    private boolean descatalogado;
    private long unidadesServidas;
    private double precioCompra;
    private double precioVenta;

    private double iva;
    private double rec_equivalencia;
    private double descuento_max;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    @ToStringExclude
    private Set<DetallePedProd> detalles = new HashSet<>();

    @Column(name = "ultima_compra")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date ultimaCompra;

    // Constructores
    // Otros métodos necesite para calculos

}

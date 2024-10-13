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
@Table(name = "producto")
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
    private double precioCompra;
    private double precioVenta;

//    @ManyToOne
//    @JoinColumn(name = "id_categoria", nullable = false) //FK para la relaccion
//    private Categoria categoria;

    private String categoria;
    private String proveedor;
    private long stock;
    private long stockMinimo;
    private boolean descatalogado;
    private long unidadesServidas;
    private double descuento_max;
    private double iva;
    private double rec_equivalencia;

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

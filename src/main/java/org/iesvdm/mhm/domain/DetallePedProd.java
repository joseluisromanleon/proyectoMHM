package org.iesvdm.mhm.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "detalle_pedido_producto")
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedProd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;
    // Cantidad de productos en este detalle
    private int numeroProductos;
    // Precio unitario del producto
    private double precioU;
    // Descuentos aplicados
    private double Dtos;
    // Subtotal para este detalle
    private double subtotal;
    // Base imponible (sin impuestos) para este detalle
    private double Base;
    // IVA aplicado para este detalle
    private double iva;
    // Recargo de equivalencia aplicado para este detalle
    private double recEqui;
    // Total para este detalle
    private double total;
    // Indica si este detalle está facturado o no
    private boolean Facturado;

}

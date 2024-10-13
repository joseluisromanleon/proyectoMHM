package org.iesvdm.mhm.dto;

import lombok.*;
import org.iesvdm.mhm.domain.Producto;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


    public class PedidoDTO {

        private Long id;
        private String titulo;
        private Set<Producto>  productos;
        private int numProductos;

       /*
       @Transient
        private int numCatPorPedido;
        */

    public PedidoDTO(Long id, String titulo, HashSet<Producto> productos) {
        this.id = id;
        this.titulo = titulo;
        this.productos = productos;

        //this.numCatPorPedido = getProductos().size();
    }
}



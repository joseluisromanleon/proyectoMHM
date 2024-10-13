package org.iesvdm.mhm.exception;

public class DetallePedidoProductoNotFoundException extends RuntimeException{
    public DetallePedidoProductoNotFoundException(Long id) {
        super("Not found Detalle with id: " + id);
    }
}

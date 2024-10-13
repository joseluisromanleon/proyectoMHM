package org.iesvdm.mhm.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("Not found Producto with id: " + id);
    }
}
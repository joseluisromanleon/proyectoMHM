package org.iesvdm.mhm.exception;

public class ProductoDTONotFoundException extends RuntimeException {
    public ProductoDTONotFoundException(Long id) {
        super("Not found Producto with id: " + id);
    }
}
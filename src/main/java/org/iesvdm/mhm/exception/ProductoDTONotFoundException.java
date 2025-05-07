package org.iesvdm.mhm.exception;

public class ProductoDTONotFoundException extends RuntimeException {
    public ProductoDTONotFoundException(Long id) {
        super("Not found Contacto with id: " + id);
    }
}
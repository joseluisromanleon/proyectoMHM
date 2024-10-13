package org.iesvdm.mhm.exception;

public class ProveedorNotFoundException extends RuntimeException {
    public ProveedorNotFoundException(Long id) {
        super("Not found Proveedor with id: " + id);
    }

}

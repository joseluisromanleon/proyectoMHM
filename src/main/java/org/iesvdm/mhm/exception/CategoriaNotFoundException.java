package org.iesvdm.mhm.exception;

public class CategoriaNotFoundException extends RuntimeException{
    public CategoriaNotFoundException(Long id) {
        super("Not found Categoría with id: " + id);
    }

}

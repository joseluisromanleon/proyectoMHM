package org.iesvdm.mhm.exception;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(Long id) {
        super("Not found Usuario with id: " + id);
    }

}

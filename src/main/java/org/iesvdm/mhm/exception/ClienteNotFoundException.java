package org.iesvdm.mhm.exception;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(Long id) {
        super("Not found Cliente with id: " + id);
    }
}

package org.iesvdm.mhm.exception;

public class MensajeNotFoundException extends RuntimeException{
    public MensajeNotFoundException(Long id) {
        super("Not found Mensaje with id: " + id);
    }
}

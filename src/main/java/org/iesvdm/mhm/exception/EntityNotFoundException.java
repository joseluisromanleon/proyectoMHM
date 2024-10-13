package org.iesvdm.mhm.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Long id, Class entity) {
        super("Not found Entity " + entity.getSimpleName() + " with id: " + id);
    }
}

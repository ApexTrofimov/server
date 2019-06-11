package ru.trofimov.server.service;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityType, String entityId) {
        super(String.format("%s with id: %s not found", entityType, entityId));
    }
}

package com.OnlineLibrary.System.Exception;

public class EntityNotFoundException extends RuntimeException{
	
	private final String entity;
    private final String entityId;
 
    public EntityNotFoundException(String entity, String entityId) {
        super(entity + " with ID " + entityId + " not present in the database");
        this.entity = entity;
        this.entityId = entityId;
    }
 
    public String getEntity() {
        return entity;
    }
 
    public String getEntityId() {
        return entityId;
    }

}

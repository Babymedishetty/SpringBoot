package com.OnlineLibrary.System.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.OnlineLibrary.System.Exception.EntityNotFoundException;

 class EntityNotFoundExceptionTest {
	
	 @Test
	     void testExceptionMessage() {
	        String entity = "Book";
	        String entityId = "123";
	        EntityNotFoundException exception = new EntityNotFoundException(entity, entityId);

	        String expectedMessage = "Book with ID 123 not present in the database";
	        assertEquals(expectedMessage, exception.getMessage());
	    }

	    @Test
	     void testGetEntity() {
	        String entity = "Book";
	        String entityId = "123";
	        EntityNotFoundException exception = new EntityNotFoundException(entity, entityId);

	        assertEquals(entity, exception.getEntity());
	    }

	    @Test
	     void testGetEntityId() {
	        String entity = "Book";
	        String entityId = "123";
	        EntityNotFoundException exception = new EntityNotFoundException(entity, entityId);

	        assertEquals(entityId, exception.getEntityId());
	    }

	    @Test
	     void testExceptionThrown() {
	        String entity = "Book";
	        String entityId = "123";

	        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
	            throw new EntityNotFoundException(entity, entityId);
	        });

	        String expectedMessage = "Book with ID 123 not present in the database";
	        assertEquals(expectedMessage, exception.getMessage());
	    }

}

package com.OnlineLibrary.System.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.OnlineLibrary.System.Dto.ResponseDto;

class ResponseDtoTest {

    private ResponseDto responseDto;

    @BeforeEach
    public void setUp() {
        responseDto = new ResponseDto();
    }

    @Test
     void testGetMessage() {
        String expectedMessage = "Test message";
        responseDto.setMessage(expectedMessage);
        assertEquals(expectedMessage, responseDto.getMessage(), "The message should be the same as set");
    }

    @Test
     void testSetMessage() {
        String expectedMessage = "Another test message";
        responseDto.setMessage(expectedMessage);
        assertEquals(expectedMessage, responseDto.getMessage(), "The message should be correctly set");
    }

    @Test
     void testToString() {
        String message = "Sample message";
        responseDto.setMessage(message);
        String expectedToString = "ResponseDto [message=" + message + "]";
        assertEquals(expectedToString, responseDto.toString(), "The toString method should return the correct string representation");
    }
}


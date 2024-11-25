package com.OnlineLibrary.System.Exception;

public class BookNotFoundException extends RuntimeException {
	
	public BookNotFoundException(String message) {
		super(message);
	}

}

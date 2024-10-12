package com.OnlineLibrary.System.Dto;

 
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ResponseDto {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	 
	@Override
	public String toString() {
		return "ResponseDto [message=" + message + "]";
	}
	

}

package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.responses;

import java.time.LocalDateTime;

public record ErrorResponse(
		LocalDateTime timestamp,
	    int status,
	    String error,
	    String message,
	    String path
		) 
{
	
}

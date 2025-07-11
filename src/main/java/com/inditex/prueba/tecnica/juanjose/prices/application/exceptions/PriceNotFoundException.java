package com.inditex.prueba.tecnica.juanjose.prices.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends PriceException {

	private static final long serialVersionUID = 1L;

	public PriceNotFoundException(String brandId, String productId, String askDate) {
		super(String.format("No se encontró ningún precio para BrandId: %s, ProductId: %s y Fecha: %s.", brandId,
				productId, askDate), HttpStatus.NOT_FOUND);
	}

}

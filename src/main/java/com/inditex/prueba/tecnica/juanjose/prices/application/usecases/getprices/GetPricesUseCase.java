package com.inditex.prueba.tecnica.juanjose.prices.application.usecases.getprices;

import java.time.LocalDateTime;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;

public interface GetPricesUseCase {
	
	Price getPricesForMarkAndProductByDate(final Integer brandId, final Integer productId, final LocalDateTime askDate);
}

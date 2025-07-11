package com.inditex.prueba.tecnica.juanjose.prices.domain.repository;

import java.time.LocalDateTime;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;

public interface PriceRepository {
	
	Price getPricesForMarkAndProductByDate(final Integer brandId, final Integer productId, final LocalDateTime askDate);

}

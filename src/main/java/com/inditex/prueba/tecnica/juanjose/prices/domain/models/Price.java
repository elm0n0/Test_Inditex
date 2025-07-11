package com.inditex.prueba.tecnica.juanjose.prices.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(Integer brandId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList,
		Integer productId, Integer priority, BigDecimal price, String curr) {

}
package com.inditex.prueba.tecnica.juanjose.prices.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
		Integer brandId,
		Integer productId,
		Integer tarifa,
		LocalDateTime startDate,
		LocalDateTime endDate,
		Integer priority,
		BigDecimal price,
		String curr
		)
{}
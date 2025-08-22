package com.inditex.prueba.tecnica.juanjose.prices.domain.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Price(
        Integer brandId,
        Integer productId,
        Integer tarifa,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        Integer priority,
        BigDecimal price,
        String currency) {}

package com.inditex.prueba.tecnica.juanjose.prices.domain.usecases;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import java.time.OffsetDateTime;

public interface GetPricesUseCase {

    Price getPricesForMarkAndProductByDate(
            final Integer brandId, final Integer productId, final OffsetDateTime askDate);
}

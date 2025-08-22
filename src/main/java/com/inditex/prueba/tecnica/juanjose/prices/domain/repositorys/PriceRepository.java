package com.inditex.prueba.tecnica.juanjose.prices.domain.repositorys;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import java.time.OffsetDateTime;

public interface PriceRepository {

    Price getPricesForMarkAndProductByDate(
            final Integer brandId, final Integer productId, final OffsetDateTime askDate);
}

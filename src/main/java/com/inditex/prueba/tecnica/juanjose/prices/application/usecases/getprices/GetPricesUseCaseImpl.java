package com.inditex.prueba.tecnica.juanjose.prices.application.usecases.getprices;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.domain.repositorys.PriceRepository;
import com.inditex.prueba.tecnica.juanjose.prices.domain.usecases.GetPricesUseCase;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPricesUseCaseImpl implements GetPricesUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price getPricesForMarkAndProductByDate(
            final Integer brandId, final Integer productId, final OffsetDateTime askDate) {
        return priceRepository.getPricesForMarkAndProductByDate(brandId, productId, askDate);
    }
}

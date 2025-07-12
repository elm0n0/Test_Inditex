package com.inditex.prueba.tecnica.juanjose.prices.application.usecases.getprices;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.domain.repository.PriceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPricesUseCaseImpl implements GetPricesUseCase {

	private final PriceRepository priceRepository;

	@Override
	public Price getPricesForMarkAndProductByDate(final Integer brandId, final Integer productId,
			final LocalDateTime askDate) {
		return priceRepository.getPricesForMarkAndProductByDate(brandId, productId, askDate);
	}

}

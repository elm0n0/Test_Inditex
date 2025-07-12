package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.domain.repository.PriceRepository;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.mappers.PriceJpaMapper;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.repository.jpa.PriceJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

	private final PriceJpaRepository priceJpaRepository;

	@Override
	public Price getPricesForMarkAndProductByDate(Integer brandId, Integer productId, LocalDateTime askDate) {
		Optional<PriceJpaEntity> priceJpaEntity = priceJpaRepository
				.findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
						brandId, productId, askDate, askDate);
		
		return PriceJpaMapper.INSTANCE.priceJpaToPrice(priceJpaEntity.orElseThrow());
	}
}

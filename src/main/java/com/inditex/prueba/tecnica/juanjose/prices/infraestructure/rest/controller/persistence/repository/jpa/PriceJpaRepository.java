package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.repository.jpa;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;

public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Integer>, JpaSpecificationExecutor<PriceJpaEntity> {
	
	Optional<PriceJpaEntity> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
	        Integer brandId, Integer productId, LocalDateTime askDate, LocalDateTime askDate2);
}

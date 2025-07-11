package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;

@Mapper
public interface PriceJpaMapper {

	PriceJpaMapper INSTANCE = Mappers.getMapper(PriceJpaMapper.class);

	Price priceJpaToPrice(PriceJpaEntity priceJpaEntity);
}

package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.responses.PriceResponse;

@Mapper
public interface PriceRestMapper {

	PriceRestMapper INSTANCE = Mappers.getMapper(PriceRestMapper.class);
	
	PriceResponse PriceToPriceResponse(Price priceResponse);
	
}

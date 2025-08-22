package com.inditex.prueba.tecnica.juanjose.prices.api.rest.controller.mappers;

import com.inditex.prueba.tecnica.juanjose.prices.api.rest.generated.model.PriceResponse;
import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceRestMapper {

    PriceRestMapper INSTANCE = Mappers.getMapper(PriceRestMapper.class);

    PriceResponse priceToPriceResponse(Price priceResponse);
}

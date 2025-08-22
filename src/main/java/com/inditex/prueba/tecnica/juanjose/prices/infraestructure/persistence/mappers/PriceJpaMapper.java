package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.mappers;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceJpaMapper {

    PriceJpaMapper INSTANCE = Mappers.getMapper(PriceJpaMapper.class);

    @Mapping(source = "priceList", target = "tarifa")
    @Mapping(source = "curr", target = "currency")
    Price priceJpaToPrice(PriceJpaEntity priceJpaEntity);
}

package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.mappers;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T17:37:13+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
public class PriceJpaMapperImpl implements PriceJpaMapper {

    @Override
    public Price priceJpaToPrice(PriceJpaEntity priceJpaEntity) {
        if ( priceJpaEntity == null ) {
            return null;
        }

        Integer brandId = null;
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        Integer priceList = null;
        Integer productId = null;
        Integer priority = null;
        BigDecimal price = null;
        String curr = null;

        brandId = priceJpaEntity.getBrandId();
        startDate = priceJpaEntity.getStartDate();
        endDate = priceJpaEntity.getEndDate();
        priceList = priceJpaEntity.getPriceList();
        productId = priceJpaEntity.getProductId();
        priority = priceJpaEntity.getPriority();
        price = priceJpaEntity.getPrice();
        curr = priceJpaEntity.getCurr();

        Price price1 = new Price( brandId, startDate, endDate, priceList, productId, priority, price, curr );

        return price1;
    }
}

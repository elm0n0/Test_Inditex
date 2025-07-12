package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.mappers;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T13:53:34+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
public class PriceJpaMapperImpl implements PriceJpaMapper {

    @Override
    public Price priceJpaToPrice(PriceJpaEntity priceJpaEntity) {
        if ( priceJpaEntity == null ) {
            return null;
        }

        Integer tarifa = null;
        Integer brandId = null;
        Integer productId = null;
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        Integer priority = null;
        BigDecimal price = null;
        String curr = null;

        tarifa = priceJpaEntity.getPriceList();
        brandId = priceJpaEntity.getBrandId();
        productId = priceJpaEntity.getProductId();
        startDate = priceJpaEntity.getStartDate();
        endDate = priceJpaEntity.getEndDate();
        priority = priceJpaEntity.getPriority();
        price = priceJpaEntity.getPrice();
        curr = priceJpaEntity.getCurr();

        Price price1 = new Price( brandId, productId, tarifa, startDate, endDate, priority, price, curr );

        return price1;
    }
}

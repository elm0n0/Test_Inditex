package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.mapper;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.responses.PriceResponse;
import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T17:37:13+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
public class PriceRestMapperImpl implements PriceRestMapper {

    @Override
    public PriceResponse PriceToPriceResponse(Price priceResponse) {
        if ( priceResponse == null ) {
            return null;
        }

        Long brandId = null;
        Date startDate = null;
        Date endDate = null;
        Integer priceList = null;
        Long productId = null;
        Double price = null;
        String curr = null;

        if ( priceResponse.brandId() != null ) {
            brandId = priceResponse.brandId().longValue();
        }
        if ( priceResponse.startDate() != null ) {
            startDate = Date.from( priceResponse.startDate().toInstant( ZoneOffset.UTC ) );
        }
        if ( priceResponse.endDate() != null ) {
            endDate = Date.from( priceResponse.endDate().toInstant( ZoneOffset.UTC ) );
        }
        priceList = priceResponse.priceList();
        if ( priceResponse.productId() != null ) {
            productId = priceResponse.productId().longValue();
        }
        if ( priceResponse.price() != null ) {
            price = priceResponse.price().doubleValue();
        }
        curr = priceResponse.curr();

        PriceResponse priceResponse1 = new PriceResponse( brandId, startDate, endDate, priceList, productId, price, curr );

        return priceResponse1;
    }
}

package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.mapper;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.responses.PriceResponse;
import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T13:53:35+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
public class PriceRestMapperImpl implements PriceRestMapper {

    @Override
    public PriceResponse priceToPriceResponse(Price priceResponse) {
        if ( priceResponse == null ) {
            return null;
        }

        Long brandId = null;
        Long productId = null;
        Integer tarifa = null;
        Date startDate = null;
        Date endDate = null;
        Double price = null;
        String curr = null;

        if ( priceResponse.brandId() != null ) {
            brandId = priceResponse.brandId().longValue();
        }
        if ( priceResponse.productId() != null ) {
            productId = priceResponse.productId().longValue();
        }
        tarifa = priceResponse.tarifa();
        if ( priceResponse.startDate() != null ) {
            startDate = Date.from( priceResponse.startDate().toInstant( ZoneOffset.UTC ) );
        }
        if ( priceResponse.endDate() != null ) {
            endDate = Date.from( priceResponse.endDate().toInstant( ZoneOffset.UTC ) );
        }
        if ( priceResponse.price() != null ) {
            price = priceResponse.price().doubleValue();
        }
        curr = priceResponse.curr();

        PriceResponse priceResponse1 = new PriceResponse( brandId, productId, tarifa, startDate, endDate, price, curr );

        return priceResponse1;
    }
}

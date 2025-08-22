package com.inditex.prueba.tecnica.juanjose.prices.api.rest.controller;

import com.inditex.prueba.tecnica.juanjose.prices.api.rest.controller.exceptions.PriceException;
import com.inditex.prueba.tecnica.juanjose.prices.api.rest.controller.exceptions.PriceNotFoundException;
import com.inditex.prueba.tecnica.juanjose.prices.api.rest.controller.mappers.PriceRestMapper;
import com.inditex.prueba.tecnica.juanjose.prices.api.rest.generated.api.PricesApi;
import com.inditex.prueba.tecnica.juanjose.prices.api.rest.generated.model.PriceResponse;
import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.domain.usecases.GetPricesUseCase;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PricesRestController implements PricesApi {

    private final GetPricesUseCase getPricesUseCase;

    @Override
    public ResponseEntity<PriceResponse> getPrices(
            final Integer brandId, final Integer productId, final String askDate) {

        Price response = null;

        try {
            LocalDateTime localDateTime = LocalDateTime.parse(askDate);
            OffsetDateTime askDateZoned =
                    localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
            response =
                    getPricesUseCase.getPricesForMarkAndProductByDate(
                            brandId, productId, askDateZoned);

        } catch (DateTimeException e) {

            throw new PriceException(
                    String.format(
                            "La 'askDate' proporcionada es inválida o tiene un formato incorrecto: '%s'. El formato esperado es ISO 8601 (yyyy-MM-dd'T'HH:mm:ss).",
                            askDate),
                    HttpStatus.BAD_REQUEST);

        } catch (NoSuchElementException ex) {
            throw new PriceNotFoundException(
                    String.valueOf(brandId), String.valueOf(productId), askDate);
        }

        return new ResponseEntity<>(
                PriceRestMapper.INSTANCE.priceToPriceResponse(response), HttpStatus.OK);
    }
}

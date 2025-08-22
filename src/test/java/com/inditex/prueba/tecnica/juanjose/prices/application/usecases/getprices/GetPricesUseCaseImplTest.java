package com.inditex.prueba.tecnica.juanjose.prices.application.usecases.getprices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.domain.repositorys.PriceRepository;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPricesUseCaseImplTest {

    @Mock private PriceRepository priceRepository;

    @InjectMocks private GetPricesUseCaseImpl getPricesUseCase;

    @Test
    @DisplayName("Debe devolver un precio cuando se encuentra en el repositorio")
    void testShouldReturnPriceWhenFoundInRepository() {
        Integer brandId = 1;
        Integer productId = 35455;
        OffsetDateTime askDate = OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);

        Price expectedPrice =
                new Price(
                        brandId,
                        productId,
                        1,
                        OffsetDateTime.of(2020, 6, 14, 0, 0, 0, 0, ZoneOffset.UTC),
                        OffsetDateTime.of(2020, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC),
                        0,
                        new BigDecimal("35.50"),
                        "EUR");

        when(priceRepository.getPricesForMarkAndProductByDate(brandId, productId, askDate))
                .thenReturn(expectedPrice);

        Price actualPrice =
                getPricesUseCase.getPricesForMarkAndProductByDate(brandId, productId, askDate);

        assertNotNull(actualPrice, "El precio devuelto no debería ser nulo");
        assertEquals(expectedPrice, actualPrice, "El precio devuelto debe ser el esperado");

        verify(priceRepository).getPricesForMarkAndProductByDate(brandId, productId, askDate);
    }
}

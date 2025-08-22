package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.repository.PriceRepositoryImpl;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.repository.jpa.PriceJpaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryImplTest {

    @Mock private PriceJpaRepository priceJpaRepository;

    @InjectMocks private PriceRepositoryImpl priceRepositoryImpl;

    @Test
    @DisplayName("Debe devolver un objeto Price cuando JpaRepository encuentra una entidad")
    void shouldReturnPriceWhenJpaRepositoryFindsEntity() {

        Integer brandId = 1;
        Integer productId = 35455;
        LocalDateTime askDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

        PriceJpaEntity jpaEntity =
                new PriceJpaEntity(
                        1L,
                        brandId,
                        LocalDateTime.of(2020, 6, 14, 0, 0, 0)
                                .atZone(ZoneId.systemDefault())
                                .toOffsetDateTime(),
                        LocalDateTime.of(2020, 12, 31, 23, 59, 59)
                                .atZone(ZoneId.systemDefault())
                                .toOffsetDateTime(),
                        1,
                        productId,
                        0,
                        new BigDecimal("35.50"),
                        "EUR");

        when(priceJpaRepository.getPricesForMarkAndProductByDate(
                        brandId,
                        productId,
                        askDate.atZone(ZoneId.systemDefault()).toOffsetDateTime()))
                .thenReturn(Optional.of(jpaEntity));

        Price resultPrice =
                priceRepositoryImpl.getPricesForMarkAndProductByDate(
                        brandId,
                        productId,
                        askDate.atZone(ZoneId.systemDefault()).toOffsetDateTime());

        assertNotNull(resultPrice, "El objeto Price devuelto no debe ser nulo");
        assertEquals(jpaEntity.getBrandId(), resultPrice.brandId());
        assertEquals(jpaEntity.getProductId(), resultPrice.productId());
        assertEquals(jpaEntity.getPriceList(), resultPrice.tarifa());
        assertEquals(jpaEntity.getPriority(), resultPrice.priority());
        assertEquals(jpaEntity.getStartDate(), resultPrice.startDate());
        assertEquals(jpaEntity.getEndDate(), resultPrice.endDate());
        assertEquals(new BigDecimal("35.50"), resultPrice.price());
        assertEquals(jpaEntity.getCurr(), resultPrice.currency());

        verify(priceJpaRepository)
                .getPricesForMarkAndProductByDate(
                        brandId,
                        productId,
                        askDate.atZone(ZoneId.systemDefault()).toOffsetDateTime());
    }
}

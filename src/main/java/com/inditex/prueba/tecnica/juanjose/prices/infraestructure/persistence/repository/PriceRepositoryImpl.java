package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.repository;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.domain.repositorys.PriceRepository;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.mappers.PriceJpaMapper;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.repository.jpa.PriceJpaRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;

    @Override
    public Price getPricesForMarkAndProductByDate(
            Integer brandId, Integer productId, OffsetDateTime askDate) {
        Optional<PriceJpaEntity> priceJpaEntity =
                priceJpaRepository.getPricesForMarkAndProductByDate(brandId, productId, askDate);

        return PriceJpaMapper.INSTANCE.priceJpaToPrice(priceJpaEntity.orElseThrow());
    }
}

package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.repository.jpa;

import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.persistence.entities.PriceJpaEntity;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceJpaRepository
        extends JpaRepository<PriceJpaEntity, Integer>, JpaSpecificationExecutor<PriceJpaEntity> {

    @Query(
            "SELECT "
                    + "p "
                    + "FROM "
                    + "PriceJpaEntity p "
                    + "WHERE "
                    + "p.brandId = :brandId "
                    + "AND p.productId = :productId "
                    + "AND :askDate BETWEEN p.startDate AND p.endDate "
                    + "ORDER BY p.priority DESC "
                    + "LIMIT 1")
    Optional<PriceJpaEntity> getPricesForMarkAndProductByDate(
            @Param("brandId") Integer brandId,
            @Param("productId") Integer productId,
            @Param("askDate") OffsetDateTime askDate);
}

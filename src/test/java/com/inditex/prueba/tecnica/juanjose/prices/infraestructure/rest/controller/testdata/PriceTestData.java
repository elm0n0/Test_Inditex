package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.testdata;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceTestData {
	String testName;
	Integer brandId;
	Integer productId;
	LocalDateTime askDate;
	Price expectedPrice;
	BigDecimal expectedFinalPrice;
	Integer expectedPriceList;
}

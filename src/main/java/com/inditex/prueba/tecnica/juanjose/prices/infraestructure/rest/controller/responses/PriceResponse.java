package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.responses;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representación del precio de un producto para la fecha y marca solicitadas.")
public record PriceResponse(@Schema(description = "Identificador de la marca.", example = "1") Long brandId,
		@Schema(description = "Fecha de inicio de validez del precio.", example = "2020-06-14T00:00:00") Date startDate,
		@Schema(description = "Fecha de fin de validez del precio.", example = "2020-12-31T23:59:59") Date endDate,
		@Schema(description = "Identificador de la tarifa de precios aplicable.", example = "1") Integer priceList,
		@Schema(description = "Identificador del producto.", example = "35455") Long productId,
		@Schema(description = "Precio final aplicado.", example = "35.50") Double price,
		@Schema(description = "Código de moneda (ej. EUR).", example = "EUR") String curr) {
}
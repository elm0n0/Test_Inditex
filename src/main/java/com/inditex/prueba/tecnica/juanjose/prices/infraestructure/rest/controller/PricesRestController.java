package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.prueba.tecnica.juanjose.prices.application.exceptions.PriceNotFoundException;
import com.inditex.prueba.tecnica.juanjose.prices.application.usecases.getprices.GetPricesUseCase;
import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.responses.PriceResponse;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.mapper.PriceRestMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PricesRestController.CONTROLLER_PATH)
@Tag(name = "Prices API", description = "API para consultar los precios de los productos de Inditex.")
public class PricesRestController {

	public static final String CONTROLLER_PATH = "/v1/prices";

	private final GetPricesUseCase getPricesUseCase;

	@GetMapping
	@Operation(summary = "Obtiene el precio más relevante para un producto de una marca en una fecha y hora dadas.", description = "Busca en la base de datos el precio aplicable para el producto y la marca indicados, en la fecha y hora proporcionadas. "
			+ "Si hay múltiples precios aplicables, se selecciona el de mayor prioridad. ", responses = {
					@ApiResponse(responseCode = "200", description = "Precio encontrado exitosamente.", content = @Content(schema = @Schema(implementation = PriceResponse.class))),
					@ApiResponse(responseCode = "400", description = "Parámetros de entrada inválidos. Asegúrese de que brandId y productId son numéricos y askDate tiene el formato correcto (ISO 8601).", content = @Content(schema = @Schema(example = "{\"status\": 400, \"error\": \"Bad Request\", \"message\": \"Formato de fecha inválido.\"}"))),
					@ApiResponse(responseCode = "404", description = "No se encontró ningún precio para los criterios especificados.", content = @Content(schema = @Schema(example = "{\"status\": 404, \"error\": \"Not Found\", \"message\": \"No price found for brandId: 1, productId: 35455, and date: 2020-06-14T10:00:00\"}"))),
					@ApiResponse(responseCode = "500", description = "Error interno del servidor.") })
	public ResponseEntity<PriceResponse> getPrices(
			@Parameter(description = "ID de la marca (ej. 1)", required = true, example = "1") @RequestParam(required = true) final Integer brandId,
			@Parameter(description = "ID del producto (ej. 35455)", required = true, example = "1") @RequestParam(required = true) final Integer productId,
			@Parameter(description = "Fecha y hora de consulta en Formato ISO 8601: yyyy-MM-ddTHH:mm:ss (ej. 2020-06-14T10:00:00).", required = true, example = "2020-06-14T10:00:00") @RequestParam(required = true) final LocalDateTime askDate) {
		Price response = null;
		try {
			response = getPricesUseCase.getPricesForMarkAndProductByDate(brandId, productId, askDate);
		} catch (NoSuchElementException ex) {
			throw new PriceNotFoundException(String.valueOf(brandId), String.valueOf(productId), askDate.toString());
		}
		return new ResponseEntity<PriceResponse>(PriceRestMapper.INSTANCE.PriceToPriceResponse(response),
				HttpStatus.OK);
	}

}

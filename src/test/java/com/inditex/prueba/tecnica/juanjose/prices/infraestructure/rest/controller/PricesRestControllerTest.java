
package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.inditex.prueba.tecnica.juanjose.prices.application.usecases.getprices.GetPricesUseCase;
import com.inditex.prueba.tecnica.juanjose.prices.domain.models.Price;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.testdata.PriceTestData;

@WebMvcTest(PricesRestController.class)
class PricesRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private GetPricesUseCase getPricesUseCase;

	static Stream<PriceTestData> getSuccessfulPriceData() {
		return Stream.of(
				// Prueba 1:
				new PriceTestData("Prueba 1: 10:00 del día 14", 1, 35455, LocalDateTime.of(2020, 6, 14, 10, 0, 0),
						new Price(1, LocalDateTime.of(2020, 6, 14, 0, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
								1, 35455, 0, new BigDecimal("35.5"), "EUR"),
						new BigDecimal("35.5"), 1),
				// Prueba 2:
				new PriceTestData("Prueba 2: 16:00 del día 14", 1, 35455, LocalDateTime.of(2020, 6, 14, 16, 0, 0),
						new Price(1, LocalDateTime.of(2020, 6, 14, 15, 0, 0), LocalDateTime.of(2020, 6, 14, 18, 30, 0),
								2, 35455, 1, new BigDecimal("25.45"), "EUR"),
						new BigDecimal("25.45"), 2),
				// Prueba 3:
				new PriceTestData("Prueba 3: 21:00 del día 14", 1, 35455, LocalDateTime.of(2020, 6, 14, 21, 0, 0),
						new Price(1, LocalDateTime.of(2020, 6, 14, 0, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
								1, 35455, 0, new BigDecimal("35.5"), "EUR"),
						new BigDecimal("35.5"), 1),
				// Prueba 4:
				new PriceTestData("Prueba 4: 10:00 del día 15", 1, 35455, LocalDateTime.of(2020, 6, 15, 10, 0, 0),
						new Price(1, LocalDateTime.of(2020, 6, 15, 0, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0, 0), 3,
								35455, 1, new BigDecimal("30.5"), "EUR"),
						new BigDecimal("30.5"), 3),
				// Prueba 5:
				new PriceTestData("Prueba 5: 21:00 del día 16", 1, 35455, LocalDateTime.of(2020, 6, 16, 21, 0, 0),
						new Price(1, LocalDateTime.of(2020, 6, 15, 16, 0, 0),
								LocalDateTime.of(2020, 12, 31, 23, 59, 59), 4, 35455, 1, new BigDecimal("38.95"),
								"EUR"),
						new BigDecimal("38.95"), 4));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("getSuccessfulPriceData")
	@DisplayName("Test getPrices - Casos exitosos con diferentes fechas y prioridades")
	void testGetPrices_Success(PriceTestData testData) throws Exception {

		// Mock del UseCase
		when(getPricesUseCase.getPricesForMarkAndProductByDate(testData.getBrandId(), testData.getProductId(),
				testData.getAskDate())).thenReturn(testData.getExpectedPrice());

		// petición HTTP simulada
		mockMvc.perform(MockMvcRequestBuilders.get(PricesRestController.CONTROLLER_PATH)
				.param("brandId", String.valueOf(testData.getBrandId()))
				.param("productId", String.valueOf(testData.getProductId()))
				.param("askDate", testData.getAskDate().toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.brandId").value(testData.getBrandId()))
				.andExpect(jsonPath("$.productId").value(testData.getProductId()))
				.andExpect(jsonPath("$.price").value(testData.getExpectedFinalPrice()))
				.andExpect(jsonPath("$.priceList").value(testData.getExpectedPriceList()))
				.andExpect(jsonPath("$.curr").value("EUR"));
	}
	
	@Test
	@DisplayName("Test getPrices - Caso de no encontrado (PriceNotFoundException): Devolver 404 Not Found")
	void testGetPrices_PriceNotFoundException() throws Exception {
		// Datos de entrada
		Integer brandId = 999;
		Integer productId = 99999;
		LocalDateTime askDate = LocalDateTime.of(2025, 7, 11, 17, 0, 0);

		// Mock del UseCase
		when(getPricesUseCase.getPricesForMarkAndProductByDate(brandId, productId, askDate))
				.thenThrow(new NoSuchElementException("No price found in the database."));

		// petición HTTP simulada
		mockMvc.perform(MockMvcRequestBuilders.get(PricesRestController.CONTROLLER_PATH)
				.param("brandId", String.valueOf(brandId))
				.param("productId", String.valueOf(productId))
				.param("askDate", askDate.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.error").value("Not Found"))
				.andExpect(jsonPath("$.message").value(String.format("No se encontró ningún precio para BrandId: %s, ProductId: %s y Fecha: %s.", brandId,
						productId, askDate)))
				.andExpect(jsonPath("$.path").value("/v1/prices"));
		
		
	}
	
	@Test
	@DisplayName("Test getPrices - Caso de parámetro brandId mal formado (InvalidBrandIdException): Devolver 400 Bad Request")
	void testGetPrices_InvalidBrandIdException() throws Exception {
		String invalidBrandId = "abc";
		Integer productId = 35455;
		LocalDateTime askDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

		mockMvc.perform(MockMvcRequestBuilders.get(PricesRestController.CONTROLLER_PATH)
				.param("brandId", invalidBrandId)
				.param("productId", String.valueOf(productId))
				.param("askDate", askDate.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.message").value(String.format(
						"El 'brandId' proporcionado es inválido o está mal formado: '%s'. Debe ser un número entero.",
						invalidBrandId)))
				.andExpect(jsonPath("$.path").value("/v1/prices"));
	}
	
	@Test
	@DisplayName("Test getPrices - Caso de parámetro brandId mal formado (InvalidProductIdException): Devolver 400 Bad Request")
	void testGetPrices_InvalidProductIdException() throws Exception {
		Integer brandId = 1;
		String invalidProductId = "abc";
		LocalDateTime askDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

		mockMvc.perform(MockMvcRequestBuilders.get(PricesRestController.CONTROLLER_PATH)
				.param("brandId", String.valueOf(brandId))
				.param("productId", String.valueOf(invalidProductId))
				.param("askDate", askDate.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.message").value(String.format(
						"El 'productId' proporcionado es inválido o está mal formado: '%s'. Debe ser un número entero.",
						invalidProductId)))
				.andExpect(jsonPath("$.path").value("/v1/prices"));
	}
	
	@Test
	@DisplayName("Test getPrices - Caso de parámetro askDate mal formado: Devolver 400 Bad Request")
	void testGetPrices_InvalidAskDateFormat() throws Exception {
		Integer brandId = 1;
		Integer productId = 35455;
		String invalidAskDate = "2020-06-14 10:00:00";

		mockMvc.perform(MockMvcRequestBuilders.get(PricesRestController.CONTROLLER_PATH)
				.param("brandId", String.valueOf(brandId))
				.param("productId", String.valueOf(productId))
				.param("askDate", invalidAskDate)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.message").value(String.format(
						"La 'askDate' proporcionada es inválida o tiene un formato incorrecto: '%s'. El formato esperado es ISO 8601 (yyyy-MM-dd'T'HH:mm:ss).",
						invalidAskDate)))
				.andExpect(jsonPath("$.path").value("/v1/prices"));
	}
	
	@Test
	@DisplayName("Test getPrices - Caso de parámetro obligatorio productId faltante: Devolver 400 Bad Request")
	void testGetPrices_MissingProductId() throws Exception {
		Integer brandId = 1;
		LocalDateTime askDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);

		mockMvc.perform(MockMvcRequestBuilders.get(PricesRestController.CONTROLLER_PATH)
				.param("brandId", String.valueOf(brandId))
				.param("askDate", askDate.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.error").value("Bad Request"))
				.andExpect(jsonPath("$.message", Matchers.containsString("El parámetro 'productId' es obligatorio.")))
				.andExpect(jsonPath("$.path").value("/v1/prices"));
	}
	
	@Test
	@DisplayName("Test getPrices - Caso de no encontrado (NoSuchElementException): Devolver 404 Not Found")
	void testGetPrices_NotFoundException() throws Exception {
	    Integer brandId = 999;
	    Integer productId = 99999;
	    LocalDateTime askDate = LocalDateTime.of(2025, 7, 11, 17, 0, 0);

	    String expectedExceptionMessage = "No price found in the database.";
	    when(getPricesUseCase.getPricesForMarkAndProductByDate(brandId, productId, askDate))
	            .thenThrow(new NoSuchElementException(expectedExceptionMessage));

	    mockMvc.perform(MockMvcRequestBuilders.get(PricesRestController.CONTROLLER_PATH)
	            .param("brandId", String.valueOf(brandId))
	            .param("productId", String.valueOf(productId))
	            .param("askDate", askDate.toString())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.timestamp").exists())
	            .andExpect(jsonPath("$.status").value(404))
	            .andExpect(jsonPath("$.error").value("Not Found"))
	            .andExpect(jsonPath("$.message").value("No se encontró ningún precio para BrandId: " + brandId
	                    + ", ProductId: " + productId + " y Fecha: " + askDate.toString() + "."))
	            .andExpect(jsonPath("$.path").value("/v1/prices"));
	}

}

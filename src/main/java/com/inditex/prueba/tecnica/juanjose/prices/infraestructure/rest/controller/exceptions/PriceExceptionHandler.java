package com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.inditex.prueba.tecnica.juanjose.prices.application.exceptions.PriceException;
import com.inditex.prueba.tecnica.juanjose.prices.infraestructure.rest.controller.responses.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class PriceExceptionHandler {

	// Maneja tus excepciones personalizadas de PriceException
	@ExceptionHandler(PriceException.class)
	public ResponseEntity<ErrorResponse> handlePriceException(PriceException ex, HttpServletRequest request) {
		HttpStatus status = ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.BAD_REQUEST;

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());
		return new ResponseEntity<>(errorResponse, status);
	}

	// Maneja errores de tipo de parámetro
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpServletRequest request) {
		String parameterName = ex.getName();
		Object invalidValue = ex.getValue();
		String message;

		switch (parameterName) {
		case "brandId": {
			message = String.format(
					"El 'brandId' proporcionado es inválido o está mal formado: '%s'. Debe ser un número entero.",
					invalidValue);
			break;
		}
		case "productId": {
			message = String.format(
					"El 'productId' proporcionado es inválido o está mal formado: '%s'. Debe ser un número entero.",
					invalidValue);
			break;
		}
		case "askDate": {
			message = String.format(
					"La 'askDate' proporcionada es inválida o tiene un formato incorrecto: '%s'. El formato esperado es ISO 8601 (yyyy-MM-dd'T'HH:mm:ss).",
					invalidValue);
			break;
		}
		default:
			message = "Un error de tipo de parámetro inesperado ha ocurrido.";
            break;
		}

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// Maneja errores cuando falta un parámetro es quequerido @RequestParam
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex,
			HttpServletRequest request) {
		String message = String.format("El parámetro '%s' es obligatorio.", ex.getParameterName());
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}

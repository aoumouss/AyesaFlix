package com.example.demo.exceptions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Intercepta excepciones de todos los @RestController y devuelve JSON de error
@RestControllerAdvice
public class GlobalExceptionHandler {

	// Se dispara cuando un @RequestBody no pasa las validaciones
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, Object> respuesta = new LinkedHashMap<>();
		respuesta.put("status", 400);
		respuesta.put("error", "Datos no válidos");

		Map<String, String> campos = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			campos.put(error.getField(), error.getDefaultMessage());
		}
		respuesta.put("campos", campos);
		return respuesta;
	}

	// Se dispara cuando lanzamos IllegalArgumentException (ID no encontrado)
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, Object> handleNotFound(IllegalArgumentException ex) {
		return Map.of("status", 404, "error", "Recurso no encontrado", "mensaje", ex.getMessage());
	}

}

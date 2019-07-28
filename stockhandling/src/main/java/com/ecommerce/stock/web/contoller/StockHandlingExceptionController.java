package com.ecommerce.stock.web.contoller;

import java.time.Instant;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.stock.exception.OutdatedStockException;
import com.ecommerce.stock.exception.StockNotFoundException;
import com.ecommerce.stock.web.model.RestApiResponse;

/**
 * Stock handling exception controller advice
 * 
 * @author YunusEmre
 *
 */
@ControllerAdvice
public class StockHandlingExceptionController extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(StockHandlingExceptionController.class);

    @ExceptionHandler(value = StockNotFoundException.class)
    public ResponseEntity<RestApiResponse> handleStockNotFound(StockNotFoundException exception) {
	logger.error(exception.getMessage());
	return new ResponseEntity<RestApiResponse>(new RestApiResponse(HttpStatus.NOT_FOUND,
		Instant.now().atOffset(ZoneOffset.UTC), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = OutdatedStockException.class)
    public ResponseEntity handleProductNotFound(OutdatedStockException exception) {
	logger.error(exception.getMessage());
	return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
	    HttpHeaders headers, HttpStatus status, WebRequest request) {
	logger.error(exception.getMessage());
	return new ResponseEntity<Object>(new RestApiResponse(HttpStatus.BAD_REQUEST,
		Instant.now().atOffset(ZoneOffset.UTC), "Erroneous JSON request"), HttpStatus.BAD_REQUEST);
    }

}

package com.ecommerce.stock.web.model;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;

public class RestApiResponse {
    private HttpStatus status;
    private OffsetDateTime timestamp;
    private String message;

    public HttpStatus getStatus() {
	return status;
    }

    public OffsetDateTime getTimestamp() {
	return timestamp;
    }

    public String getMessage() {
	return message;
    }

    public RestApiResponse(HttpStatus status, OffsetDateTime timestamp, String message) {

	this.status = status;
	this.timestamp = timestamp;
	this.message = message;
    }

}

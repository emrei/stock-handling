package com.ecommerce.stock.exception;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String message) {
	super(message);
    }

}

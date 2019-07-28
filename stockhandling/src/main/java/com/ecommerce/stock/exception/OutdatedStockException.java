package com.ecommerce.stock.exception;

public class OutdatedStockException extends RuntimeException {

    public OutdatedStockException(String message) {
	super(message);
    }

}

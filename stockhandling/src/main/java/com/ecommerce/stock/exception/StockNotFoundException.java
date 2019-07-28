package com.ecommerce.stock.exception;

/**
 * StockNotFoundException can be thrown when a stock is not found in the store
 * 
 * @author YunusEmre
 *
 */
public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String message) {
	super(message);
    }

}

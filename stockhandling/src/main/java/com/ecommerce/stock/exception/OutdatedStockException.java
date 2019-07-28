package com.ecommerce.stock.exception;

/**
 * OutdatedStockException can be thrown when a newer stock is already stored
 * @author YunusEmre
 *
 */
public class OutdatedStockException extends RuntimeException {

    public OutdatedStockException(String message) {
	super(message);
    }

}

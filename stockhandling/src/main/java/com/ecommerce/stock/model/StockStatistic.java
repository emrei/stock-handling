package com.ecommerce.stock.model;

import java.time.OffsetDateTime;

/**
 * Class holds stock information with sold number
 * 
 * @author YunusEmre
 *
 */
public class StockStatistic {
    private String id;
    private OffsetDateTime timestamp;
    private String productId;
    private int quantity;
    private int soldNumber;

    public StockStatistic(String id, OffsetDateTime timestamp, String productId, int quantity, int soldNumber) {
	this.id = id;
	this.timestamp = timestamp;
	this.productId = productId;
	this.quantity = quantity;
	this.soldNumber = soldNumber;
    }

    public String getId() {
	return id;
    }

    public OffsetDateTime getTimestamp() {
	return timestamp;
    }

    public String getProductId() {
	return productId;
    }

    public int getQuantity() {
	return quantity;
    }

    public int getSoldNumber() {
	return soldNumber;
    }
}

package com.ecommerce.stock.model;

import java.time.OffsetDateTime;

/**
 * Class holds stock information without sales records
 * 
 * @author YunusEmre
 *
 */
public class StockWithoutRecords {
    private String id;
    private OffsetDateTime timestamp;
    private String productId;
    private int quantity;

    public StockWithoutRecords(String id, OffsetDateTime timestamp, String productId, int quantity) {
	this.id = id;
	this.timestamp = timestamp;
	this.productId = productId;
	this.quantity = quantity;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public OffsetDateTime getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
	this.timestamp = timestamp;
    }

    public String getProductId() {
	return productId;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

}

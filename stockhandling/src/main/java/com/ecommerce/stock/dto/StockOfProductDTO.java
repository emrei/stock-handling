package com.ecommerce.stock.dto;

import java.time.OffsetDateTime;

/**
 * StockProductDTO keeps stock information and is used for client communiation
 * @author YunusEmre
 *
 */
public class StockOfProductDTO {
    private String id;
    private OffsetDateTime timestamp;
    private int quantity;

    public StockOfProductDTO() {

    }

    public StockOfProductDTO(String id, OffsetDateTime timestamp, int quantity) {
	this.id = id;
	this.timestamp = timestamp;
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

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }
}

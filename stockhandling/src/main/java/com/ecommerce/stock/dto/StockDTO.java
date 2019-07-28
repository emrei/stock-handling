package com.ecommerce.stock.dto;

import java.time.OffsetDateTime;

/**
 * StockDTO model keeps stock information and used for client communication
 * 
 * @author YunusEmre
 *
 */
public class StockDTO {
    private String id;
    private OffsetDateTime timestamp;
    private String productId;
    private int quantity;

    public StockDTO() {

    }

    public StockDTO(String id, OffsetDateTime timestamp, String productId, int quantity) {
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

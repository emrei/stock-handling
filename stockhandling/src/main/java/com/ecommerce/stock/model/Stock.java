package com.ecommerce.stock.model;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Stock model holds stock information
 * 
 * @author YunusEmre
 *
 */
public class Stock {
    private String id;
    private String productId;
    private OffsetDateTime timestamp;
    private int quantity;

    public Stock(String id, String productId, OffsetDateTime timestamp, int quantity) {
	this.id = id;
	this.productId = productId;
	this.timestamp = timestamp;
	this.quantity = quantity;
    }

    public String getId() {
	return id;
    }

    public String getProductId() {
	return productId;
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

    @Override
    public int hashCode() {
	return Objects.hash(getId(), getProductId(), getQuantity(), getTimestamp());
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Stock))
	    return false;
	Stock other = (Stock) obj;
	return Objects.equals(getId(), other.getId()) && Objects.equals(getProductId(), other.getProductId())
		&& getQuantity() == other.getQuantity() && Objects.equals(getTimestamp(), other.getTimestamp());
    }

}

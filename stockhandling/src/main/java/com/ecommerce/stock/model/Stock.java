package com.ecommerce.stock.model;

import java.time.OffsetDateTime;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Stock class keeps stock information and sales records
 * 
 * @author YunusEmre
 *
 */
public class Stock {
    private String id;
    private OffsetDateTime timestamp;
    private String productId;
    private int quantity;
    private ConcurrentSkipListMap<OffsetDateTime, Integer> salesRecords = new ConcurrentSkipListMap<OffsetDateTime, Integer>();

    public Stock(String id, OffsetDateTime timestamp, String productId, int quantity) {
	this.id = id;
	this.timestamp = timestamp;
	this.productId = productId;
	this.quantity = quantity;
    }

    public Stock(String id, String productId) {
	this.id = id;
	this.productId = productId;
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

    public String getProductId() {
	return productId;
    }

    public void setProductId(String productId) {
	this.productId = productId;
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

    public void addSalesRecord(OffsetDateTime time, int quantity) {
	salesRecords.merge(timestamp, quantity, (prev, latest) -> prev + latest);
    }

    public ConcurrentSkipListMap<OffsetDateTime, Integer> getSalesRecords() {
	return salesRecords;
    }
}

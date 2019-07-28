package com.ecommerce.stock.model;

import java.time.OffsetDateTime;

import com.ecommerce.stock.enums.RangeEnum;

/**
 * StockStatistic model holds stock, product and number of sales information for
 * the range
 * 
 * @author YunusEmre
 *
 */
public class StockStatistic {
    private String stockId;
    private OffsetDateTime timestamp;
    private String productId;
    private int quantity;
    private RangeEnum range;
    private int soldNumber;

    public StockStatistic(String stockId, OffsetDateTime timestamp, String productId, int quantity, RangeEnum range,
	    int soldNumber) {
	this.stockId = stockId;
	this.timestamp = timestamp;
	this.productId = productId;
	this.quantity = quantity;
	this.range = range;
	this.soldNumber = soldNumber;
    }

    public String getId() {
	return stockId;
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

    public RangeEnum getRange() {
	return range;
    }

    public int getSoldNumber() {
	return soldNumber;
    }
}

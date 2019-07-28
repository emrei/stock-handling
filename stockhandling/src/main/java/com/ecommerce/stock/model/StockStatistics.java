package com.ecommerce.stock.model;

import java.time.OffsetDateTime;
import java.util.List;

import com.ecommerce.stock.enums.RangeEnum;

/**
 * Class keeps product and stock statistics
 * 
 * @author YunusEmre
 *
 */
public class StockStatistics {
    private OffsetDateTime requestTimestamp;
    private RangeEnum range;
    private List<StockWithoutRecords> topAvailableProducts;
    private List<ProductSold> topSellingProducts;

    public StockStatistics(OffsetDateTime requestTimestamp, RangeEnum range,
	    List<StockWithoutRecords> topAvailableProducts, List<ProductSold> topSellingProducts) {
	this.requestTimestamp = requestTimestamp;
	this.range = range;
	this.topAvailableProducts = topAvailableProducts;
	this.topSellingProducts = topSellingProducts;
    }

    public OffsetDateTime getRequestTimestamp() {
	return requestTimestamp;
    }

    public void setRequestTimestamp(OffsetDateTime requestTimestamp) {
	this.requestTimestamp = requestTimestamp;
    }

    public RangeEnum getRange() {
	return range;
    }

    public void setRange(RangeEnum range) {
	this.range = range;
    }

    public List<StockWithoutRecords> getTopAvailableProducts() {
	return topAvailableProducts;
    }

    public void setTopAvailableProducts(List<StockWithoutRecords> topAvailableProducts) {
	this.topAvailableProducts = topAvailableProducts;
    }

    public List<ProductSold> getTopSellingProducts() {
	return topSellingProducts;
    }

    public void setTopSellingProducts(List<ProductSold> topSellingProducts) {
	this.topSellingProducts = topSellingProducts;
    }
}

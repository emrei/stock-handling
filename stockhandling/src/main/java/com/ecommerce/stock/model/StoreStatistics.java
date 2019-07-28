package com.ecommerce.stock.model;

import java.time.OffsetDateTime;
import java.util.List;

import com.ecommerce.stock.enums.RangeEnum;

/**
 * StoreStatistics model keeps top available products with highest availability
 * and top selling products of all store for the range
 * 
 * @author YunusEmre
 *
 */
public class StoreStatistics {
    private OffsetDateTime requestTimestamp;
    private RangeEnum range;
    private List<Stock> topAvailableProducts;
    private List<ProductStatistic> topSellingProducts;

    public StoreStatistics(OffsetDateTime requestTimestamp, RangeEnum range, List<Stock> topAvailableProducts,
	    List<ProductStatistic> topSellingProducts) {
	this.requestTimestamp = requestTimestamp;
	this.range = range;
	this.topAvailableProducts = topAvailableProducts;
	this.topSellingProducts = topSellingProducts;
    }

    public OffsetDateTime getRequestTimestamp() {
	return requestTimestamp;
    }

    public RangeEnum getRange() {
	return range;
    }

    public List<Stock> getTopAvailableProducts() {
	return topAvailableProducts;
    }

    public List<ProductStatistic> getTopSellingProducts() {
	return topSellingProducts;
    }

}

package com.ecommerce.stock.dto;

import java.time.OffsetDateTime;
import java.util.List;

public class StatisticsResponseDTO {
    private OffsetDateTime requestTimestamp;
    private String range;
    List<StockRequestDTO> topAvailableProducts;
    List<ProductSoldDTO> topSellingProducts;

    public StatisticsResponseDTO(OffsetDateTime requestTimestamp, String range,
	    List<StockRequestDTO> topAvailableProducts, List<ProductSoldDTO> topSellingProducts) {
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

    public String getRange() {
	return range;
    }

    public void setRange(String range) {
	this.range = range;
    }

    public List<StockRequestDTO> getTopAvailableProducts() {
	return topAvailableProducts;
    }

    public void setTopAvailableProducts(List<StockRequestDTO> topAvailableProducts) {
	this.topAvailableProducts = topAvailableProducts;
    }

    public List<ProductSoldDTO> getTopSellingProducts() {
	return topSellingProducts;
    }

    public void setTopSellingProducts(List<ProductSoldDTO> topSellingProducts) {
	this.topSellingProducts = topSellingProducts;
    }
}

package com.ecommerce.stock.dto;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * StatisticsResponseDTO model keeps requested timestamp and statistics of the
 * given range. This is used for communication with client
 * 
 * @author YunusEmre
 *
 */
public class StatisticsResponseDTO {
    private OffsetDateTime requestTimestamp;
    private String range;
    List<StockDTO> topAvailableProducts;
    List<ProductSoldDTO> topSellingProducts;

    public StatisticsResponseDTO() {

    }

    public StatisticsResponseDTO(OffsetDateTime requestTimestamp, String range, List<StockDTO> topAvailableProducts,
	    List<ProductSoldDTO> topSellingProducts) {
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

    public List<StockDTO> getTopAvailableProducts() {
	return topAvailableProducts;
    }

    public void setTopAvailableProducts(List<StockDTO> topAvailableProducts) {
	this.topAvailableProducts = topAvailableProducts;
    }

    public List<ProductSoldDTO> getTopSellingProducts() {
	return topSellingProducts;
    }

    public void setTopSellingProducts(List<ProductSoldDTO> topSellingProducts) {
	this.topSellingProducts = topSellingProducts;
    }
}

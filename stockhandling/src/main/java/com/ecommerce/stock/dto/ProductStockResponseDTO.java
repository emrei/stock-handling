package com.ecommerce.stock.dto;

import java.time.OffsetDateTime;

public class ProductStockResponseDTO {
    private String productId;
    private OffsetDateTime requestTimestamp;
    private StockOfProductDTO stock;

    public ProductStockResponseDTO(String productId, OffsetDateTime requestTimestamp, StockOfProductDTO stock) {
	this.productId = productId;
	this.requestTimestamp = requestTimestamp;
	this.stock = stock;
    }

    public String getProductId() {
	return productId;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }

    public OffsetDateTime getRequestTimestamp() {
	return requestTimestamp;
    }

    public void setRequestTimestamp(OffsetDateTime requestTimestamp) {
	this.requestTimestamp = requestTimestamp;
    }

    public StockOfProductDTO getStock() {
	return stock;
    }

    public void setStock(StockOfProductDTO stock) {
	this.stock = stock;
    }
}

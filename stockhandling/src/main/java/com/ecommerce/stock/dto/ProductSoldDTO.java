package com.ecommerce.stock.dto;

public class ProductSoldDTO {
    private String productId;
    private int itemsSold;

    public ProductSoldDTO(String productId, int itemsSold) {
	this.productId = productId;
	this.itemsSold = itemsSold;
    }

    public String getProductId() {
	return productId;
    }

    public void setProductId(String productId) {
	this.productId = productId;
    }

    public int getItemsSold() {
	return itemsSold;
    }

    public void setItemsSold(int itemsSold) {
	this.itemsSold = itemsSold;
    }
}

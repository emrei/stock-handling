package com.ecommerce.stock.dto;

/**
 * ProductSoldDTO model keeps product id and number of items sold.
 * This model is used for communication with client
 * @author YunusEmre
 *
 */
public class ProductSoldDTO {
    private String productId;
    private int itemsSold;

    public ProductSoldDTO() {
	
    }

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

package com.ecommerce.stock.model;

/**
 * Class keeps number of product items sold
 * 
 * @author YunusEmre
 *
 */
public class ProductSold {
    private String productId;
    private int itemsSold;

    public ProductSold(String productId, int itemsSold) {
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

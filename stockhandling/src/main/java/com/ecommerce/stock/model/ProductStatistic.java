package com.ecommerce.stock.model;

import java.util.Objects;

import com.ecommerce.stock.enums.RangeEnum;

/**
 * ProductStatistic model holds product information and product sales statistics
 * of range
 * 
 * @author YunusEmre
 *
 */
public class ProductStatistic {
    private String productId;
    private RangeEnum range;
    private int itemsSold;

    public String getProductId() {
	return productId;
    }

    public RangeEnum getRange() {
	return range;
    }

    public int getItemsSold() {
	return itemsSold;
    }

    public ProductStatistic(String productId, RangeEnum range, int itemsSold) {
	this.productId = productId;
	this.range = range;
	this.itemsSold = itemsSold;
    }

    @Override
    public int hashCode() {
	return Objects.hash(getItemsSold(), getProductId(), getRange());
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof ProductStatistic))
	    return false;
	ProductStatistic other = (ProductStatistic) obj;
	return getItemsSold() == other.getItemsSold() && Objects.equals(getProductId(), other.getProductId())
		&& getRange() == other.getRange();
    }

}

package com.ecommerce.stock.model;

import java.time.OffsetDateTime;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Store model holds stock information and sales records of the stock
 * 
 * @author YunusEmre
 *
 */
public class Store {
    private final Stock stock;
    private final ConcurrentSkipListMap<OffsetDateTime, Integer> salesRecords = new ConcurrentSkipListMap<OffsetDateTime, Integer>();

    public Store(Stock stock) {
	this.stock = stock;
    }

    public Stock getStock() {
	return stock;
    }

    public boolean isStockOutdated(Stock newStock) {
	return stock.getTimestamp().isAfter(newStock.getTimestamp());
    }

    /**
     * upadtes store with new stock information and returns updated stock
     * 
     * @param newStock
     * @return
     */
    public Stock updateStockInfo(Stock newStock) {
	if (stock.getQuantity() > newStock.getQuantity()) {
	    updateSalesRecords(newStock.getTimestamp(), stock.getQuantity() - newStock.getQuantity());
	}
	stock.setTimestamp(newStock.getTimestamp());
	stock.setQuantity(newStock.getQuantity());
	return stock;
    }

    private void updateSalesRecords(OffsetDateTime timestamp, int salesNumber) {
	salesRecords.put(timestamp, salesNumber);

    }

    public ConcurrentSkipListMap<OffsetDateTime, Integer> getSalesRecords() {
	return salesRecords;
    }
}

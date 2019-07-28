package com.ecommerce.stock.service;

import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StoreStatistics;

/**
 * Stock handling service managing stocks
 * 
 * @author YunusEmre
 *
 */
public interface StockHandlingService {

    /**
     * Updates stock. If there is no stock and product with given id, then it
     * creates a new one. If given stock is an outdated stock then it throws
     * exception
     * 
     * @param stock
     * @return
     */
    Stock updateStock(Stock stock);

    /**
     * Returns stock for given product id. If there is no product for given id then
     * it throws an exception
     * 
     * @param productId
     * @return
     */
    Stock getStock(String productId);

    /**
     * Returns stock statistics of given time range
     * 
     * @param valueOf
     * @return
     */
    StoreStatistics getStatistics(RangeEnum range);

}

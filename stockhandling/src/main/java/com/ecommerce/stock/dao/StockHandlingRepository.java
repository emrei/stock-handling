package com.ecommerce.stock.dao;

import java.util.List;
import java.util.Optional;

import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StockStatistic;

/**
 * Stock handling repository interface
 * 
 * @author YunusEmre
 *
 */
public interface StockHandlingRepository {

    /**
     * Updates stock. If there is no stock and product with given id, then it
     * creates a new one. If there is an incompatible case such as there is a
     * product with given product id but no stock id, then it throws an exception
     * 
     * @param stock
     * @return
     */
    Stock updateStock(Stock stock);

    /**
     * Returns stock for given product id. If there is no stock for given id then it
     * throws an exception
     * 
     * @param productId
     * @return
     */
    Optional<Stock> getStock(String productId);

    /**
     * Returns list of stockStatistic which contains stock information and sold
     * number info for given range
     * 
     * @param range
     * @return
     */
    List<StockStatistic> getStockStatisticList(RangeEnum range);

}

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
     * Updates stock. If there is no stock for given id, then it creates a new one.
     * 
     * @param stock
     * @return
     */
    Stock updateStock(Stock stock);

    /**
     * Returns stock for given product id.
     * 
     * @param productId
     * @return
     */
    Optional<Stock> getStock(String productId);

    /**
     * Returns list of stockStatistic which contains stock information and sold
     * number info for given range. StockStatistic doesn't contain stock object,
     * instead it contains value of the stock at requested moment. By this way stock
     * statistic data can not be changed by other threads while being processed
     * after.
     * 
     * @param range
     * @return
     */
    List<StockStatistic> getStockStatisticList(RangeEnum range);

}

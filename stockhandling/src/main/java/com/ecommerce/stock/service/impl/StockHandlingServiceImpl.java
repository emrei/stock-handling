package com.ecommerce.stock.service.impl;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.stock.dao.StockHandlingRepository;
import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.exception.StockNotFoundException;
import com.ecommerce.stock.model.ProductSold;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StockStatistic;
import com.ecommerce.stock.model.StockStatistics;
import com.ecommerce.stock.model.StockWithoutRecords;
import com.ecommerce.stock.service.StockHandlingService;

@Service
public class StockHandlingServiceImpl implements StockHandlingService {

    @Autowired
    private StockHandlingRepository stockHandlingRepository;

    @Override
    public Stock updateStock(Stock stock) {
	return stockHandlingRepository.updateStock(stock);
    }

    @Override
    public Stock getStock(String productId) {
	return stockHandlingRepository.getStock(productId)
		.orElseThrow(() -> new StockNotFoundException("Stock not found with id: " + productId));
    }

    @Override
    public StockStatistics getStatistics(RangeEnum range) {
	OffsetDateTime requestTimestamp = Instant.now().atOffset(ZoneOffset.UTC);
	List<StockStatistic> stockStatisticList = stockHandlingRepository.getStockStatisticList(range);
	return new StockStatistics(requestTimestamp, range, getTopAvailableProducts(stockStatisticList),
		getTopSellingProducts(stockStatisticList));

    }

    private List<StockWithoutRecords> getTopAvailableProducts(List<StockStatistic> stockStatisticList) {
	return stockStatisticList.stream().sorted(Comparator.comparing(StockStatistic::getQuantity).reversed()).limit(3)
		.map(s -> {
		    return new StockWithoutRecords(s.getId(), s.getTimestamp(), s.getProductId(), s.getQuantity());
		}).collect(Collectors.toList());

    }

    private List<ProductSold> getTopSellingProducts(List<StockStatistic> stockStatisticList) {

	return stockStatisticList.stream().sorted(Comparator.comparing(StockStatistic::getSoldNumber).reversed())
		.limit(3).map(s -> new ProductSold(s.getProductId(), s.getSoldNumber())).collect(Collectors.toList());
    }

}

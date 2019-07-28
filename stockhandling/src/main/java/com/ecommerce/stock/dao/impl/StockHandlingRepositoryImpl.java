package com.ecommerce.stock.dao.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ecommerce.stock.dao.StockHandlingRepository;
import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.exception.OutdatedStockException;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StockStatistic;
import com.ecommerce.stock.model.Store;

@Repository
public class StockHandlingRepositoryImpl implements StockHandlingRepository {

    private final ConcurrentHashMap<String, Store> productStockMap = new ConcurrentHashMap<String, Store>();

    @Override
    public Stock updateStock(Stock stock) {
	Store updatedStore = productStockMap.merge(stock.getProductId(), new Store(stock), (prev, latest) -> {
	    if (prev.isStockOutdated(stock)) {
		throw new OutdatedStockException(
			"There is a newer stock information for given productId: " + stock.getProductId());
	    }
	    prev.updateStockInfo(stock);
	    return prev;
	});
	return updatedStore.getStock();
    }

    @Override
    public Optional<Stock> getStock(String productId) {
	Optional<Store> store = Optional.ofNullable(productStockMap.get(productId));
	return store.map(Store::getStock);
    }

    @Override
    public List<StockStatistic> getStockStatisticList(RangeEnum range) {
	OffsetDateTime from = getTimeForGivenRange(range);
	return productStockMap.values().stream().map(s -> {
	    return new StockStatistic(s.getStock().getId(), s.getStock().getTimestamp(), s.getStock().getProductId(),
		    s.getStock().getQuantity(), range,
		    s.getSalesRecords().tailMap(from, true).values().stream().reduce(0, Integer::sum));

	}).collect(Collectors.toList());
    }

    private OffsetDateTime getTimeForGivenRange(RangeEnum range) {
	if (range == RangeEnum.TODAY) {
	    return OffsetDateTime.of(LocalDateTime.now().toLocalDate().atStartOfDay(), ZoneOffset.UTC);
	} else {
	    return OffsetDateTime.now().minusMonths(1);
	}
    }

}

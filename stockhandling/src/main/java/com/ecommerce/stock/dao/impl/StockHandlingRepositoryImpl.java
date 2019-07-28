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

@Repository
public class StockHandlingRepositoryImpl implements StockHandlingRepository {

    private final ConcurrentHashMap<String, Stock> productStockMap = new ConcurrentHashMap<String, Stock>();

    @Override
    public Stock updateStock(Stock stock) {

	return productStockMap.merge(stock.getProductId(), stock, (prev, latest) -> {
	    if (prev.getTimestamp().isAfter(latest.getTimestamp())) {
		throw new OutdatedStockException(
			"There is a newer stock information for given productId: " + stock.getProductId());
	    }
	    prev.setTimestamp(latest.getTimestamp());
	    if (prev.getQuantity() > latest.getQuantity()) {
		prev.addSalesRecord(latest.getTimestamp(), prev.getQuantity() - latest.getQuantity());
	    }
	    prev.setQuantity(latest.getQuantity());
	    return prev;
	});

    }

    @Override
    public Optional<Stock> getStock(String productId) {
	return Optional.ofNullable(productStockMap.get(productId));
    }

    @Override
    public List<StockStatistic> getStockStatisticList(RangeEnum range) {
	OffsetDateTime from = getTimeForGivenRange(range);
	return productStockMap.values().stream().map(s -> {
	    return new StockStatistic(s.getId(), s.getTimestamp(), s.getProductId(), s.getQuantity(),
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

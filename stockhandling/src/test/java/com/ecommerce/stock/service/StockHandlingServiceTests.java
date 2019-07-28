package com.ecommerce.stock.service;

import static org.junit.Assert.assertEquals;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.stock.dao.StockHandlingRepository;
import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.exception.OutdatedStockException;
import com.ecommerce.stock.exception.StockNotFoundException;
import com.ecommerce.stock.model.ProductStatistic;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StockStatistic;
import com.ecommerce.stock.model.StoreStatistics;
import com.ecommerce.stock.service.impl.StockHandlingServiceImpl;

/**
 * Stock Handling Service mock tests
 * 
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
public class StockHandlingServiceTests {

    @InjectMocks
    StockHandlingServiceImpl stockHandlingService;

    @Mock
    StockHandlingRepository stockHandlingRepository;

    Stock stockVegetable, stockFruit, stockMilk, stockBeverage;

    StoreStatistics todayExpectedStatistics, lastMonthExpectedStatistics;
    List<StockStatistic> todayStockStatisticList, lastMonthStockStatisticList;

    OffsetDateTime requestTimestamp;

    @Before
    public void setup() {
	requestTimestamp = OffsetDateTime.parse("2019-07-28T18:54:01.754Z");
	todayStockStatisticList = new ArrayList<StockStatistic>();
	lastMonthStockStatisticList = new ArrayList<StockStatistic>();
	todayStockStatisticList.add(new StockStatistic("000001", OffsetDateTime.parse("2019-07-28T15:58:02.954Z"),
		"vegetable-123", 99, RangeEnum.TODAY, 2));
	todayStockStatisticList.add(new StockStatistic("000002", OffsetDateTime.parse("2019-07-27T21:59:01.954Z"),
		"fruit", 270, RangeEnum.TODAY, 0));
	todayStockStatisticList.add(new StockStatistic("000003", OffsetDateTime.parse("2019-07-28T11:59:01.954Z"),
		"milk", 58, RangeEnum.TODAY, 4));
	todayStockStatisticList.add(new StockStatistic("000004", OffsetDateTime.parse("2019-07-28T15:59:01.954Z"),
		"beverage", 160, RangeEnum.TODAY, 71));

	lastMonthStockStatisticList.add(new StockStatistic("000001", OffsetDateTime.parse("2019-07-28T15:58:02.954Z"),
		"vegetable-123", 99, RangeEnum.LAST_MONTH, 2));
	lastMonthStockStatisticList.add(new StockStatistic("000002", OffsetDateTime.parse("2019-07-27T21:59:01.954Z"),
		"fruit", 270, RangeEnum.LAST_MONTH, 20));
	lastMonthStockStatisticList.add(new StockStatistic("000003", OffsetDateTime.parse("2019-07-28T11:59:01.954Z"),
		"milk", 58, RangeEnum.LAST_MONTH, 5));
	lastMonthStockStatisticList.add(new StockStatistic("000004", OffsetDateTime.parse("2019-07-28T15:59:01.954Z"),
		"beverage", 160, RangeEnum.LAST_MONTH, 71));

	stockVegetable = new Stock("000001", "vegetable-123", OffsetDateTime.parse("2019-07-28T15:58:02.954Z"), 99);

	stockFruit = new Stock("000002", "fruit", OffsetDateTime.parse("2019-07-27T21:59:01.954Z"), 270);

	stockMilk = new Stock("000003", "milk", OffsetDateTime.parse("2019-07-28T11:59:01.954Z"), 58);

	stockBeverage = new Stock("000004", "beverage", OffsetDateTime.parse("2019-07-28T15:59:01.954Z"), 160);
    }

    @Test
    public void testUpdateStock() {
	Mockito.when(stockHandlingRepository.updateStock(stockVegetable)).thenReturn(stockVegetable);
	Stock updatedStock = stockHandlingService.updateStock(stockVegetable);
	assertEquals(stockVegetable.getId(), updatedStock.getId());
	assertEquals(stockVegetable.getProductId(), updatedStock.getProductId());
	assertEquals(stockVegetable.getTimestamp(), updatedStock.getTimestamp());
	assertEquals(stockVegetable.getQuantity(), updatedStock.getQuantity());
    }

    @Test(expected = OutdatedStockException.class)
    public void testUpdateStock_OutdatedStock() {
	Mockito.when(stockHandlingRepository.updateStock(stockVegetable)).thenThrow(OutdatedStockException.class);
	stockHandlingService.updateStock(stockVegetable);
    }

    @Test
    public void testGetStock_Successful() {
	Mockito.when(stockHandlingRepository.getStock("vegetable-123"))
		.thenReturn(Optional.ofNullable(stockVegetable));
	Stock actualStock = stockHandlingService.getStock("vegetable-123");
	assertEquals(stockVegetable.getId(), actualStock.getId());
	assertEquals(stockVegetable.getTimestamp(), actualStock.getTimestamp());
	assertEquals(stockVegetable.getProductId(), actualStock.getProductId());
	assertEquals(stockVegetable.getQuantity(), actualStock.getQuantity());
    }

    @Test(expected = StockNotFoundException.class)
    public void testGetStock_StockNotFound() {
	Mockito.when(stockHandlingRepository.getStock("vegetable-123")).thenThrow(StockNotFoundException.class);
	stockHandlingService.getStock("vegetable-123");
    }

    @Test
    public void testGetStatistics_Today() {
	Mockito.when(stockHandlingRepository.getStockStatisticList(RangeEnum.TODAY))
		.thenReturn(todayStockStatisticList);
	StoreStatistics actualStatistics = stockHandlingService.getStatistics(RangeEnum.TODAY);
	assertEquals(RangeEnum.TODAY, actualStatistics.getRange());
	assertEquals(3, actualStatistics.getTopAvailableProducts().size());
	assertEquals(stockFruit, actualStatistics.getTopAvailableProducts().get(0));
	assertEquals(stockBeverage, actualStatistics.getTopAvailableProducts().get(1));
	assertEquals(stockVegetable, actualStatistics.getTopAvailableProducts().get(2));

	assertEquals(new ProductStatistic("beverage", RangeEnum.TODAY, 71),
		actualStatistics.getTopSellingProducts().get(0));
	assertEquals(new ProductStatistic("milk", RangeEnum.TODAY, 4), actualStatistics.getTopSellingProducts().get(1));
	assertEquals(new ProductStatistic("vegetable-123", RangeEnum.TODAY, 2),
		actualStatistics.getTopSellingProducts().get(2));

    }

    @Test
    public void testGetStatistics_LastMonth() {
	Mockito.when(stockHandlingRepository.getStockStatisticList(RangeEnum.LAST_MONTH))
		.thenReturn(lastMonthStockStatisticList);
	StoreStatistics actualStatistics = stockHandlingService.getStatistics(RangeEnum.LAST_MONTH);
	assertEquals(RangeEnum.LAST_MONTH, actualStatistics.getRange());
	assertEquals(3, actualStatistics.getTopAvailableProducts().size());
	assertEquals(stockFruit, actualStatistics.getTopAvailableProducts().get(0));
	assertEquals(stockBeverage, actualStatistics.getTopAvailableProducts().get(1));
	assertEquals(stockVegetable, actualStatistics.getTopAvailableProducts().get(2));

	assertEquals(new ProductStatistic("beverage", RangeEnum.LAST_MONTH, 71),
		actualStatistics.getTopSellingProducts().get(0));
	assertEquals(new ProductStatistic("fruit", RangeEnum.LAST_MONTH, 20),
		actualStatistics.getTopSellingProducts().get(1));
	assertEquals(new ProductStatistic("milk", RangeEnum.LAST_MONTH, 5),
		actualStatistics.getTopSellingProducts().get(2));

    }

}

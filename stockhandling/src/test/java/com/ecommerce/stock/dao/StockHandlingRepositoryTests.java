package com.ecommerce.stock.dao;

import static org.junit.Assert.assertEquals;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.exception.OutdatedStockException;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StockStatistic;

/**
 * Stock Handling Repository integration tests.
 * 
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class StockHandlingRepositoryTests {

    @Autowired
    StockHandlingRepository stockHandlingRepository;

    Stock stockVegetable1, stockVegetable2, stockVegetable3, stockFruit1, stockFruit2, stockFruit3, stockMilk1,
	    stockMilk2, stockMilk3, stockBeverage1, stockBeverage2, stockBeverage3;

    @Before
    public void setup() {
	stockVegetable1 = new Stock("000001", "vegetable-123", OffsetDateTime.parse("2019-07-16T15:54:01.754Z"), 100);
	stockVegetable2 = new Stock("000001", "vegetable-123", OffsetDateTime.parse("2019-07-28T15:58:01.954Z"), 98);
	stockVegetable3 = new Stock("000001", "vegetable-123", OffsetDateTime.parse("2019-07-28T15:58:02.954Z"), 99);

	stockFruit1 = new Stock("000002", "fruit", OffsetDateTime.parse("2019-06-16T11:54:01.754Z"), 300);
	stockFruit2 = new Stock("000002", "fruit", OffsetDateTime.parse("2019-06-17T11:54:01.754Z"), 290);
	stockFruit3 = new Stock("000002", "fruit", OffsetDateTime.parse("2019-07-27T21:59:01.954Z"), 270);

	stockMilk1 = new Stock("000003", "milk", OffsetDateTime.parse("2019-07-26T21:54:01.754Z"), 63);
	stockMilk2 = new Stock("000003", "milk", OffsetDateTime.parse("2019-07-26T21:57:01.754Z"), 62);
	stockMilk3 = new Stock("000003", "milk", OffsetDateTime.parse("2019-07-28T11:59:01.954Z"), 58);

	stockBeverage1 = new Stock("000004", "beverage", OffsetDateTime.parse("2019-05-16T21:54:01.754Z"), 250);
	stockBeverage2 = new Stock("000004", "beverage", OffsetDateTime.parse("2019-05-19T20:54:01.754Z"), 231);
	stockBeverage3 = new Stock("000004", "beverage", OffsetDateTime.parse("2019-07-28T15:59:01.954Z"), 160);

    }

    @Test
    public void testUpdateStock_SuccessfulCreate() {
	Stock updatedStock = stockHandlingRepository.updateStock(stockVegetable1);
	assertEquals(stockVegetable1.getId(), updatedStock.getId());
	assertEquals(stockVegetable1.getProductId(), updatedStock.getProductId());
	assertEquals(stockVegetable1.getTimestamp(), updatedStock.getTimestamp());
	assertEquals(stockVegetable1.getQuantity(), updatedStock.getQuantity());
    }

    @Test(expected = OutdatedStockException.class)
    public void testUpdateStock_OutdatedStock() {
	stockHandlingRepository.updateStock(stockVegetable2);
	stockHandlingRepository.updateStock(stockVegetable1);
    }

    @Test
    public void testUpdateStock_SuccessfulUpdate() {
	stockHandlingRepository.updateStock(stockVegetable1);
	Stock updatedStock = stockHandlingRepository.updateStock(stockVegetable2);
	assertEquals(stockVegetable2.getTimestamp(), updatedStock.getTimestamp());
	assertEquals(stockVegetable2.getQuantity(), updatedStock.getQuantity());
    }

    @Test
    public void testGetStock_Successful() {
	stockHandlingRepository.updateStock(stockFruit1);
	Optional<Stock> actualStock = stockHandlingRepository.getStock("fruit");
	assertEquals(stockFruit1.getId(), actualStock.get().getId());
	assertEquals(stockFruit1.getTimestamp(), actualStock.get().getTimestamp());
	assertEquals(stockFruit1.getProductId(), actualStock.get().getProductId());
	assertEquals(stockFruit1.getQuantity(), actualStock.get().getQuantity());
    }

    @Test
    public void testGetStockStatisticList_Today() {
	updateAllStocks();
	List<StockStatistic> stockStatisticList = stockHandlingRepository.getStockStatisticList(RangeEnum.TODAY);
	Map<String, StockStatistic> productStatisticMap = stockStatisticList.stream()
		.collect(Collectors.toMap(StockStatistic::getProductId, Function.identity()));
	assertEquals(4, stockStatisticList.size());
	assertProductStatistic(productStatisticMap.get("vegetable-123"), 2, 99);
	assertProductStatistic(productStatisticMap.get("fruit"), 0, 270);
	assertProductStatistic(productStatisticMap.get("milk"), 4, 58);
	assertProductStatistic(productStatisticMap.get("beverage"), 71, 160);

    }

    @Test
    public void testGetStockStatisticList_LastMonth() {
	updateAllStocks();
	List<StockStatistic> stockStatisticList = stockHandlingRepository.getStockStatisticList(RangeEnum.LAST_MONTH);
	Map<String, StockStatistic> productStatisticMap = stockStatisticList.stream()
		.collect(Collectors.toMap(StockStatistic::getProductId, Function.identity()));
	assertEquals(4, stockStatisticList.size());
	assertProductStatistic(productStatisticMap.get("vegetable-123"), 2, 99);
	assertProductStatistic(productStatisticMap.get("fruit"), 20, 270);
	assertProductStatistic(productStatisticMap.get("milk"), 5, 58);
	assertProductStatistic(productStatisticMap.get("beverage"), 71, 160);
    }

    private void assertProductStatistic(StockStatistic stockStatistic, int expectedsoldNumber, int expectedQuantity) {
	assertEquals(expectedsoldNumber, stockStatistic.getSoldNumber());
	assertEquals(expectedQuantity, stockStatistic.getQuantity());

    }

    private void updateAllStocks() {
	stockHandlingRepository.updateStock(stockVegetable1);
	stockHandlingRepository.updateStock(stockVegetable2);
	stockHandlingRepository.updateStock(stockVegetable3);

	stockHandlingRepository.updateStock(stockFruit1);
	stockHandlingRepository.updateStock(stockFruit2);
	stockHandlingRepository.updateStock(stockFruit3);

	stockHandlingRepository.updateStock(stockMilk1);
	stockHandlingRepository.updateStock(stockMilk2);
	stockHandlingRepository.updateStock(stockMilk3);

	stockHandlingRepository.updateStock(stockBeverage1);
	stockHandlingRepository.updateStock(stockBeverage2);
	stockHandlingRepository.updateStock(stockBeverage3);
    }

}

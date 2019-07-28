package com.ecommerce.stock.dao;

import static org.junit.Assert.assertEquals;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    Stock stockVegetable1, stockVegetable2, stockFruit1, stockFruit2, stockMilk1, stockMilk2, stockBeverage1,
	    stockBeverage2;

    @Before
    public void setup() {
	stockVegetable1 = new Stock("000001", OffsetDateTime.parse("2017-07-16T22:54:01.754Z"), "vegetable-123", 100);
	stockVegetable2 = new Stock("000001", OffsetDateTime.parse("2017-07-16T22:54:01.954Z"), "vegetable-123", 98);
	stockFruit1 = new Stock("000002", OffsetDateTime.parse("2017-07-16T21:54:01.754Z"), "fruit", 300);
	stockFruit2 = new Stock("000002", OffsetDateTime.parse("2017-07-16T21:59:01.954Z"), "fruit", 270);

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
	assertEquals(2, updatedStock.getSalesRecords().firstEntry().getValue().intValue());
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
	stockHandlingRepository.getStockStatisticList(RangeEnum.TODAY);
    }
    
    @Test
    public void testGetStockStatisticList_LastMonth() {
	stockHandlingRepository.getStockStatisticList(RangeEnum.LAST_MONTH);
    }

}

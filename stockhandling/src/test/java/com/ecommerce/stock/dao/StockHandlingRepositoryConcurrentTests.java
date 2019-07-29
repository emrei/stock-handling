package com.ecommerce.stock.dao;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.model.Stock;

/**
 * StockHandlingRepository concurrency tests
 * 
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class StockHandlingRepositoryConcurrentTests {

    @Autowired
    StockHandlingRepository stockHandlingRepository;

    @Test
    public void testUpdateStockConcurrently() throws InterruptedException {

	runMultithreaded(new Runnable() {
	    public void run() {
		stockHandlingRepository.updateStock(new Stock("stock" + getRandomInt(), "product" + getRandomInt(),
			Instant.now().atOffset(ZoneOffset.UTC), getRandomInt()));
	    }
	}, 10000);

	assertEquals(stockHandlingRepository.getStockStatisticList(RangeEnum.TODAY).size(), 10000);
    }

    private void runMultithreaded(Runnable runnable, int threadCount) throws InterruptedException {
	List<Thread> threadList = new LinkedList<Thread>();
	for (int i = 0; i < threadCount; i++) {
	    threadList.add(new Thread(runnable));
	}
	for (Thread t : threadList) {
	    t.start();
	}
	for (Thread t : threadList) {
	    t.join();
	}
    }

    private int getRandomInt() {
	Random r = new Random();
	return r.ints(Integer.MIN_VALUE, (10000000)).findAny().getAsInt();
    }

}

package com.ecommerce.stock.controller;

import java.time.OffsetDateTime;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.stock.dto.StatisticsResponseDTO;
import com.ecommerce.stock.dto.StockDTO;
import com.ecommerce.stock.dto.StockOfProductResponseDTO;
import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.web.model.RestApiResponse;

/**
 * Stock Handling Rest Controller integration tests
 * 
 * @author YunusEmre
 *
 */
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class StockHandlingControllerTests {

    private TestRestTemplate restTemplate;
    StockDTO stockVegetableDTO1, stockVegetableDTO2, stockVegetableDTO3, stockFruitDTO1, stockFruitDTO2, stockFruitDTO3,
	    stockMilkDTO1, stockMilkDTO2, stockMilkDTO3, stockBeverageDTO1, stockBeverageDTO2, stockBeverageDTO3;

    @Before
    public void setup() {
	restTemplate = new TestRestTemplate();

	stockVegetableDTO1 = new StockDTO("000001", OffsetDateTime.parse("2019-07-16T15:54:01.754Z"), "vegetable-123",
		100);
	stockVegetableDTO2 = new StockDTO("000001", OffsetDateTime.parse("2019-07-28T15:58:01.954Z"), "vegetable-123",
		98);
	stockVegetableDTO3 = new StockDTO("000001", OffsetDateTime.parse("2019-07-28T15:58:02.954Z"), "vegetable-123",
		99);

	stockFruitDTO1 = new StockDTO("000002", OffsetDateTime.parse("2019-06-16T11:54:01.754Z"), "fruit", 300);
	stockFruitDTO2 = new StockDTO("000002", OffsetDateTime.parse("2019-06-17T11:54:01.754Z"), "fruit", 290);
	stockFruitDTO3 = new StockDTO("000002", OffsetDateTime.parse("2019-07-27T21:59:01.954Z"), "fruit", 270);

	stockMilkDTO1 = new StockDTO("000003", OffsetDateTime.parse("2019-07-26T21:54:01.754Z"), "milk", 63);
	stockMilkDTO2 = new StockDTO("000003", OffsetDateTime.parse("2019-07-26T21:57:01.754Z"), "milk", 62);
	stockMilkDTO3 = new StockDTO("000003", OffsetDateTime.parse("2019-07-28T11:59:01.954Z"), "milk", 58);

	stockBeverageDTO1 = new StockDTO("000004", OffsetDateTime.parse("2019-05-16T21:54:01.754Z"), "beverage", 250);
	stockBeverageDTO2 = new StockDTO("000004", OffsetDateTime.parse("2019-05-19T20:54:01.754Z"), "beverage", 231);
	stockBeverageDTO3 = new StockDTO("000004", OffsetDateTime.parse("2019-07-28T15:59:01.954Z"), "beverage", 160);
    }

    @Test
    public void testUpdateStock() {
	ResponseEntity<RestApiResponse> response = restTemplate.postForEntity("http://localhost:8080/stock/update",
		stockVegetableDTO1, RestApiResponse.class);

	MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.CREATED));
	MatcherAssert.assertThat(response.getBody().getStatus(), Matchers.equalTo(HttpStatus.CREATED));
	MatcherAssert.assertThat(response.getBody().getMessage(),
		Matchers.equalTo("Stock with id 000001 has been updated"));
    }

    @Test
    public void testUpdateStock_NoContent() {
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockFruitDTO2, RestApiResponse.class);
	ResponseEntity<RestApiResponse> response = restTemplate.postForEntity("http://localhost:8080/stock/update",
		stockFruitDTO1, RestApiResponse.class);

	MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.NO_CONTENT));

    }

    @Test
    public void testGetStock() {
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockVegetableDTO1, RestApiResponse.class);

	ResponseEntity<StockOfProductResponseDTO> response = restTemplate
		.getForEntity("http://localhost:8080/stock?productId=vegetable-123", StockOfProductResponseDTO.class);

	MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
	MatcherAssert.assertThat(response.getBody().getProductId(), Matchers.equalTo("vegetable-123"));
	MatcherAssert.assertThat(response.getBody().getStock().getId(), Matchers.equalTo("000001"));
	MatcherAssert.assertThat(response.getBody().getStock().getTimestamp(),
		Matchers.equalTo(OffsetDateTime.parse("2019-07-16T15:54:01.754Z")));
	MatcherAssert.assertThat(response.getBody().getStock().getQuantity(), Matchers.equalTo(100));
    }

    @Test
    public void testGetStock_StockNotFound() {
	ResponseEntity<RestApiResponse> response = restTemplate
		.getForEntity("http://localhost:8080/stock?productId=vegetable-123", RestApiResponse.class);

	MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.NOT_FOUND));
	MatcherAssert.assertThat(response.getBody().getStatus(), Matchers.equalTo(HttpStatus.NOT_FOUND));
	MatcherAssert.assertThat(response.getBody().getMessage(),
		Matchers.equalTo("Stock not found with id: vegetable-123"));
    }

    @Test
    public void testGetStatistics_Today() {
	updateStocks();

	ResponseEntity<StatisticsResponseDTO> response = restTemplate
		.getForEntity("http://localhost:8080/statistics?time=today", StatisticsResponseDTO.class);

	MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
	MatcherAssert.assertThat(response.getBody().getRange(), Matchers.equalTo(RangeEnum.TODAY.getValue()));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(0).getId(),
		Matchers.equalTo("000002"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(0).getProductId(),
		Matchers.equalTo("fruit"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(0).getQuantity(),
		Matchers.equalTo(270));

	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(1).getId(),
		Matchers.equalTo("000004"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(1).getProductId(),
		Matchers.equalTo("beverage"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(1).getQuantity(),
		Matchers.equalTo(160));

	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(2).getId(),
		Matchers.equalTo("000001"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(2).getProductId(),
		Matchers.equalTo("vegetable-123"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(2).getQuantity(),
		Matchers.equalTo(99));

	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(0).getProductId(),
		Matchers.equalTo("beverage"));
	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(0).getItemsSold(),
		Matchers.equalTo(71));

	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(1).getProductId(),
		Matchers.equalTo("milk"));
	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(1).getItemsSold(), Matchers.equalTo(4));

	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(2).getProductId(),
		Matchers.equalTo("vegetable-123"));
	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(2).getItemsSold(), Matchers.equalTo(2));

    }

    @Test
    public void testGetStatistics_LastMonth() {
	updateStocks();
	ResponseEntity<StatisticsResponseDTO> response = restTemplate
		.getForEntity("http://localhost:8080/statistics?time=lastMonth", StatisticsResponseDTO.class);

	MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
	MatcherAssert.assertThat(response.getBody().getRange(), Matchers.equalTo(RangeEnum.LAST_MONTH.getValue()));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(0).getId(),
		Matchers.equalTo("000002"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(0).getProductId(),
		Matchers.equalTo("fruit"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(0).getQuantity(),
		Matchers.equalTo(270));

	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(1).getId(),
		Matchers.equalTo("000004"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(1).getProductId(),
		Matchers.equalTo("beverage"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(1).getQuantity(),
		Matchers.equalTo(160));

	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(2).getId(),
		Matchers.equalTo("000001"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(2).getProductId(),
		Matchers.equalTo("vegetable-123"));
	MatcherAssert.assertThat(response.getBody().getTopAvailableProducts().get(2).getQuantity(),
		Matchers.equalTo(99));

	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(0).getProductId(),
		Matchers.equalTo("beverage"));
	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(0).getItemsSold(),
		Matchers.equalTo(71));

	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(1).getProductId(),
		Matchers.equalTo("fruit"));
	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(1).getItemsSold(),
		Matchers.equalTo(20));

	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(2).getProductId(),
		Matchers.equalTo("milk"));
	MatcherAssert.assertThat(response.getBody().getTopSellingProducts().get(2).getItemsSold(), Matchers.equalTo(5));

    }

    private void updateStocks() {
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockVegetableDTO1, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockVegetableDTO2, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockVegetableDTO3, RestApiResponse.class);

	restTemplate.postForEntity("http://localhost:8080/stock/update", stockFruitDTO1, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockFruitDTO2, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockFruitDTO3, RestApiResponse.class);

	restTemplate.postForEntity("http://localhost:8080/stock/update", stockMilkDTO1, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockMilkDTO2, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockMilkDTO3, RestApiResponse.class);

	restTemplate.postForEntity("http://localhost:8080/stock/update", stockBeverageDTO1, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockBeverageDTO2, RestApiResponse.class);
	restTemplate.postForEntity("http://localhost:8080/stock/update", stockBeverageDTO3, RestApiResponse.class);
    }
}

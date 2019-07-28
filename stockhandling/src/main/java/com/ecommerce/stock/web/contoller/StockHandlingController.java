package com.ecommerce.stock.web.contoller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.stock.dto.ProductSoldDTO;
import com.ecommerce.stock.dto.StatisticsResponseDTO;
import com.ecommerce.stock.dto.StockDTO;
import com.ecommerce.stock.dto.StockOfProductDTO;
import com.ecommerce.stock.dto.StockOfProductResponseDTO;
import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.model.ProductStatistic;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StoreStatistics;
import com.ecommerce.stock.service.StockHandlingService;
import com.ecommerce.stock.web.model.RestApiResponse;

/**
 * Rest Controller for stock handling
 * 
 * @author YunusEmre
 *
 */
@RestController
public class StockHandlingController {

    @Autowired
    StockHandlingService stockHandlingService;

    Logger logger = LoggerFactory.getLogger(StockHandlingController.class);

    @PostMapping("/stock/update")
    public ResponseEntity<RestApiResponse> updateStock(@RequestBody StockDTO stockDTO) {
	logger.info("Update Stock request for product: " + stockDTO.getProductId());
	stockHandlingService.updateStock(convertToStock(stockDTO));
	return new ResponseEntity<RestApiResponse>(new RestApiResponse(HttpStatus.CREATED,
		Instant.now().atOffset(ZoneOffset.UTC), "Stock with id " + stockDTO.getId() + " has been updated"),
		HttpStatus.CREATED);
    }

    @GetMapping("/stock")
    public ResponseEntity<StockOfProductResponseDTO> getStock(@RequestParam("productId") String productId) {
	logger.info("Get Stock request for product: " + productId);
	Stock stock = stockHandlingService.getStock(productId);
	return ResponseEntity.ok(new StockOfProductResponseDTO(productId, Instant.now().atOffset(ZoneOffset.UTC),
		new StockOfProductDTO(stock.getId(), stock.getTimestamp(), stock.getQuantity())));
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponseDTO> getStatistics(@RequestParam("time") String time) {
	logger.info("Statistics request for time: " + time);
	StoreStatistics stockStatistics = stockHandlingService.getStatistics(RangeEnum.fromValue(time));
	return ResponseEntity.ok(convertToStatisticsResponseDTO(stockStatistics));
    }

    private Stock convertToStock(StockDTO stockDTO) {
	return new Stock(stockDTO.getId(), stockDTO.getProductId(), stockDTO.getTimestamp(), stockDTO.getQuantity());
    }

    private StatisticsResponseDTO convertToStatisticsResponseDTO(StoreStatistics statistics) {
	List<StockDTO> topAvailableProducts = statistics.getTopAvailableProducts().stream()
		.map(p -> convertToStockDTO(p)).collect(Collectors.toList());
	List<ProductSoldDTO> topSellingProducts = statistics.getTopSellingProducts().stream()
		.map(p -> convertToProductSoldDTO(p)).collect(Collectors.toList());
	return new StatisticsResponseDTO(statistics.getRequestTimestamp(), statistics.getRange().getValue(),
		topAvailableProducts, topSellingProducts);
    }

    private StockDTO convertToStockDTO(Stock stock) {
	return new StockDTO(stock.getId(), stock.getTimestamp(), stock.getProductId(), stock.getQuantity());
    }

    private ProductSoldDTO convertToProductSoldDTO(ProductStatistic productSold) {
	return new ProductSoldDTO(productSold.getProductId(), productSold.getItemsSold());
    }

}

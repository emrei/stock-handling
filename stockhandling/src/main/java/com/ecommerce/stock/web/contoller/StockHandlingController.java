package com.ecommerce.stock.web.contoller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.stock.dto.ProductSoldDTO;
import com.ecommerce.stock.dto.ProductStockResponseDTO;
import com.ecommerce.stock.dto.StatisticsResponseDTO;
import com.ecommerce.stock.dto.StockOfProductDTO;
import com.ecommerce.stock.dto.StockRequestDTO;
import com.ecommerce.stock.enums.RangeEnum;
import com.ecommerce.stock.model.ProductSold;
import com.ecommerce.stock.model.Stock;
import com.ecommerce.stock.model.StockStatistics;
import com.ecommerce.stock.model.StockWithoutRecords;
import com.ecommerce.stock.service.StockHandlingService;
import com.ecommerce.stock.web.model.RestApiResponse;

@RestController
public class StockHandlingController {

    @Autowired
    StockHandlingService stockHandlingService;

    @PostMapping("/stock/update")
    public ResponseEntity<RestApiResponse> updateStock(@RequestBody StockRequestDTO stockDTO) {
	stockHandlingService.updateStock(convertToStock(stockDTO));
	return new ResponseEntity<RestApiResponse>(new RestApiResponse(HttpStatus.CREATED,
		Instant.now().atOffset(ZoneOffset.UTC), "Stock with id " + stockDTO.getId() + " has been updated"),
		HttpStatus.CREATED);
    }

    @GetMapping("/stock")
    public ResponseEntity<ProductStockResponseDTO> getStock(@RequestParam("productId") String productId) {
	Stock stock = stockHandlingService.getStock(productId);
	return ResponseEntity.ok(new ProductStockResponseDTO(productId, Instant.now().atOffset(ZoneOffset.UTC),
		new StockOfProductDTO(stock.getId(), stock.getTimestamp(), stock.getQuantity())));
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponseDTO> getStatistics(@RequestParam("time") String time) {
	StockStatistics stockStatistics = stockHandlingService.getStatistics(RangeEnum.valueOf(time));
	return ResponseEntity.ok(convertToStatisticsResponseDTO(stockStatistics));
    }

    private Stock convertToStock(StockRequestDTO stockDTO) {
	return new Stock(stockDTO.getId(), stockDTO.getTimestamp(), stockDTO.getProductId(), stockDTO.getQuantity());
    }

    private StatisticsResponseDTO convertToStatisticsResponseDTO(StockStatistics statistics) {

	List<StockRequestDTO> topAvailableProducts = statistics.getTopAvailableProducts().stream()
		.map(p -> convertToStockRequestDTO(p)).collect(Collectors.toList());
	List<ProductSoldDTO> topSellingProducts = statistics.getTopSellingProducts().stream()
		.map(p -> convertToProductSoldDTO(p)).collect(Collectors.toList());
	return new StatisticsResponseDTO(statistics.getRequestTimestamp(), statistics.getRange().range(),
		topAvailableProducts, topSellingProducts);
    }

    private StockRequestDTO convertToStockRequestDTO(StockWithoutRecords stock) {
	return new StockRequestDTO(stock.getId(), stock.getTimestamp(), stock.getProductId(), stock.getQuantity());
    }

    private ProductSoldDTO convertToProductSoldDTO(ProductSold productSold) {
	return new ProductSoldDTO(productSold.getProductId(), productSold.getItemsSold());
    }

}

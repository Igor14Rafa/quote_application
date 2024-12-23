package com.example.stockmanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.stockmanager.model.Stock;
import com.example.stockmanager.service.StockManagerService;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    @Mock
    private StockManagerService stockService;

    @InjectMocks
    private StockController stockController;

    @Test
    public void testInit() {
        stockController.init();

        verify(stockService).initializeStocks();
    }

    @Test
    public void testAddStock() {
        Stock stock = new Stock();

        stock.setId("TEST");
        stock.setDescription("Test Stock");
        ResponseEntity<Stock> expectedResponse = new ResponseEntity<>(stock, HttpStatus.CREATED);

        when(stockService.addStock(any(Stock.class))).thenReturn(expectedResponse);

        ResponseEntity<Stock> response = stockController.addStock(stock);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(stock, response.getBody());
        verify(stockService).addStock(stock);
    }

    @Test
    public void testGetAllStocks() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        stock1.setId("PETR3");
        stock1.setDescription("petr3 test");
        stock2.setId("VALE5");
        stock2.setDescription("vale5 test");

        when(stockService.getAllStocks()).thenReturn(Arrays.asList(stock1, stock2));

        Iterable<Stock> stocks = stockController.getAllStocks();

        assertNotNull(stocks);
        List<Stock> stockList = (List<Stock>) stocks;
        assertEquals(2, stockList.size());
        assertEquals(stock1, stockList.get(0));
        assertEquals(stock2, stockList.get(1));
    }
}

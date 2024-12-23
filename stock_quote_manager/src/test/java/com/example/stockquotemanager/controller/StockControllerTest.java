package com.example.stockquotemanager.controller;

import com.example.stockquotemanager.model.StockQuote;
import com.example.stockquotemanager.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StockControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStockQuote() {
        StockQuote stock = new StockQuote();
        stock.setId("1");

        when(stockService.addStockQuote(any(StockQuote.class))).thenReturn(new ResponseEntity<>(stock, HttpStatus.CREATED));

        ResponseEntity<StockQuote> response = stockController.addStockQuote(stock);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(stock, response.getBody());
    }

    @Test
    void testGetAllStockQuotes() {
        Iterable<StockQuote> stocks = List.of(new StockQuote(), new StockQuote());
        when(stockService.getAllStockQuotes()).thenReturn(stocks);

        Iterable<StockQuote> response = stockController.getAllStockQuotes();

        assertEquals(stocks, response);
    }

    @Test
    void testGetQuote() {
        StockQuote stock = new StockQuote();
        stock.setId("1");
        when(stockService.getQuote("1")).thenReturn(new ResponseEntity<>(stock, HttpStatus.OK));

        ResponseEntity<StockQuote> response = stockController.getQuote("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stock, response.getBody());
    }

    @Test
    void testClearStockCache() {
        ResponseEntity<String> response = stockController.clearStockCache();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

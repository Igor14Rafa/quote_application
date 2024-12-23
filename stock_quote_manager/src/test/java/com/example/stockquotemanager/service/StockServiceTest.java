package com.example.stockquotemanager.service;

import com.example.stockquotemanager.model.Host;
import com.example.stockquotemanager.model.Stock;
import com.example.stockquotemanager.model.StockQuote;
import com.example.stockquotemanager.repository.StockQuoteRepository;
import com.example.stockquotemanager.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockQuoteRepository stockQuoteRepository;

    @Mock
    private Environment env;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddStockQuote() throws Exception {
        StockQuote stock = new StockQuote();
        stock.setId("1");

        Stock stockExistent = new Stock();
        stockExistent.setId("1");

        stockService.setIsRegistered(true);

        when(env.getProperty("stocks.api.host")).thenReturn("externalstocks");
        when(env.getProperty("stocks.api.port")).thenReturn("9090");

        List<Stock> stockList = Arrays.asList(stockExistent);
        Optional<List<Stock>> returnedStocks = Optional.of(stockList);

        try (MockedStatic<Util> mockedStatic = mockStatic(Util.class)) { 
            mockedStatic.when(() -> Util.getIdsStocks(anyString(), anyString())).thenReturn(returnedStocks);
            when(stockQuoteRepository.save(any(StockQuote.class))).thenReturn(stock);

            ResponseEntity<StockQuote> response = stockService.addStockQuote(stock); 

            StockQuote respBody = response.getBody();
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(respBody);
            assertEquals(stock.getId(), respBody.getId());
            assertEquals(stock.getQuote(), respBody.getQuote());
        }

    }

    @Test
    void testAddStockQuote_WhenNotRegistered() {
        StockQuote stockQuote = new StockQuote();
        stockService.setIsRegistered(false);

        ResponseEntity<StockQuote> response = stockService.addStockQuote(stockQuote);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }    

    @Test
    void testGetAllStockQuotes() {
        Iterable<StockQuote> stocks = List.of(new StockQuote(), new StockQuote());
        when(stockQuoteRepository.findAll()).thenReturn(stocks);

        Iterable<StockQuote> response = stockService.getAllStockQuotes();

        assertEquals(stocks.spliterator().getExactSizeIfKnown(), response.spliterator().getExactSizeIfKnown());
    }

    @Test
    void testGetQuote() {
        StockQuote stock = new StockQuote();
        stock.setId("1");
        when(stockQuoteRepository.findById("1")).thenReturn(Optional.of(stock));

        ResponseEntity<StockQuote> response = stockService.getQuote("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stock, response.getBody());
    }

    @Test
    void testClearItemsCache() {
        stockService.clearItemsCache();
    }

    @Test
    void testTryAddNotification() throws Exception {
        ResponseEntity<Host> resp = new ResponseEntity<>(HttpStatus.CREATED);
        
        when(env.getProperty("quote.host.name")).thenReturn("internalstocks");
        when(env.getProperty("stocks.api.host")).thenReturn("externalstocks");
        when(env.getProperty("stocks.api.port")).thenReturn("9090");
        when(env.getProperty("server.port")).thenReturn("8081");

        try (MockedStatic<Util> mockedStatic = mockStatic(Util.class)) {
            stockService.setIsRegistered(false);
            mockedStatic.when(() -> Util.connectToStockManager(anyString(), anyString(), anyString(), anyString())).thenReturn(resp);

            stockService.tryAddNotification();

            mockedStatic.verify(() -> Util.connectToStockManager(anyString(), anyString(), anyString(), anyString()), times(1));
        }

    }
}


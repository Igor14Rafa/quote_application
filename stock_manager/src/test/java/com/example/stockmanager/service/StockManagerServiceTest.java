package com.example.stockmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.stockmanager.Util.ManagerUtil;
import com.example.stockmanager.model.Host;
import com.example.stockmanager.model.Stock;
import com.example.stockmanager.repository.HostRepository;
import com.example.stockmanager.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
public class StockManagerServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private HostRepository hostRepository;

    @InjectMocks
    private StockManagerService stockManagerService;

    @Test
    public void testInitializeStocks() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        stock1.setId("PETR3");
        stock1.setDescription("petr3 test");
        stock2.setId("VALE5");
        stock2.setDescription("vale5 test");

        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        stockManagerService.initializeStocks();

        verify(stockRepository, times(2)).save(any(Stock.class));

    }

    @Test
    public void testAddStock() {
        Stock stock = new Stock();
        stock.setId("TEST");
        stock.setDescription("Test Stock");

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        ResponseEntity<Stock> response = stockManagerService.addStock(stock);
    
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(stock, response.getBody());
        verify(stockRepository).save(stock);

    }

    @Test
    public void testGetAllStocks() {
        Stock stock1 = new Stock();
        Stock stock2 = new Stock();

        stock1.setId("PETR3");
        stock1.setDescription("petr3 test");
        stock2.setId("VALE5");
        stock2.setDescription("vale5 test");

        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        Iterable<Stock> stocks = stockManagerService.getAllStocks();

        assertNotNull(stocks);
        List<Stock> stockList = (List<Stock>) stocks;
        assertEquals(2, stockList.size());
        assertEquals(stock1, stockList.get(0));
        assertEquals(stock2, stockList.get(1));
    }

    @Test
    public void testGetAllHosts() {
        Host host1 = new Host();
        Host host2 = new Host();

        host1.setEndpoint("test");
        host2.setEndpoint("test2");

        host1.setHost("hosttest");
        host2.setHost("hosttest2");

        host1.setPort(161);
        host2.setPort(162);

        when(hostRepository.findAll()).thenReturn(Arrays.asList(host1, host2));

        Iterable<Host> hosts = stockManagerService.getAllHosts();

        assertNotNull(hosts);
        List<Host> hostList = (List<Host>) hosts;
        assertEquals(2, hostList.size());
        assertEquals(host1, hostList.get(0));
        assertEquals(host2, hostList.get(1));
    }

    @Test
    public void testClearCacheNotification() {
        Host host1 = new Host();
        Host host2 = new Host();

        host1.setEndpoint("test");
        host2.setEndpoint("test2");

        host1.setHost("hosttest");
        host2.setHost("hosttest2");

        host1.setPort(161);
        host2.setPort(162);

        Iterable<Host> hosts = Arrays.asList(host1, host2);

        when(hostRepository.findAll()).thenReturn(Arrays.asList(host1, host2));

        try (MockedStatic<ManagerUtil> mockedStatic = mockStatic(ManagerUtil.class)) {
            mockedStatic.when(() -> ManagerUtil.sendClearCacheNotification(hosts)).thenReturn(new ResponseEntity<String>(HttpStatus.OK));

            ResponseEntity<String> response = stockManagerService.clearCacheNotification();

            assertEquals(HttpStatus.OK, response.getStatusCode());

            mockedStatic.verify(() -> ManagerUtil.sendClearCacheNotification(hosts), times(1));
        }

    }

    @Test
    public void testAddNotification() {
        Host host = new Host();

        host.setEndpoint("test");
        host.setHost("hosttest");
        host.setPort(161);

        when(hostRepository.save(any(Host.class))).thenReturn(host);

        ResponseEntity<Host> response = stockManagerService.addNotification(host);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(host, response.getBody());
        verify(hostRepository).save(host);
    }
}


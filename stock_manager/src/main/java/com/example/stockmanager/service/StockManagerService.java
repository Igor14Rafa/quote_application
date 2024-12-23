package com.example.stockmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.stockmanager.Util.ManagerUtil;
import com.example.stockmanager.model.Host;
import com.example.stockmanager.model.Stock;
import com.example.stockmanager.repository.HostRepository;
import com.example.stockmanager.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockManagerService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private HostRepository hostRepository;

    @Transactional
    public void initializeStocks() {
        Stock aux = new Stock();
        Stock aux2 = new Stock();
        aux.setId("PETR3");
        aux.setDescription("petr3 test");
        aux2.setId("VALE5");
        aux2.setDescription("vale5 test");
        stockRepository.save(aux);
        stockRepository.save(aux2);
    }

    @Transactional
    public ResponseEntity<Stock> addStock(Stock stock) {
        ResponseEntity<String> respClearCache = clearCacheNotification();
        
        if (respClearCache.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            return new ResponseEntity<Stock>(stock, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        Stock savedStock = stockRepository.save(stock);
        return new ResponseEntity<Stock>(savedStock, HttpStatus.CREATED);
    }

    public Iterable<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Iterable<Host> getAllHosts() {
        return hostRepository.findAll();
    }    


    public ResponseEntity<String> clearCacheNotification() {
        return ManagerUtil.sendClearCacheNotification(getAllHosts());
    }

    @Transactional
    public ResponseEntity<Host> addNotification(Host host) {
        hostRepository.save(host);
        return new ResponseEntity<Host>(host, HttpStatus.CREATED);
    }
}

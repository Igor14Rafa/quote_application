package com.example.stockquotemanager.service;

import com.example.stockquotemanager.model.Host;
import com.example.stockquotemanager.model.Stock;
import com.example.stockquotemanager.model.StockQuote;
import com.example.stockquotemanager.repository.StockQuoteRepository;
import com.example.stockquotemanager.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockQuoteRepository stockQuoteRepository;

    @Autowired
    private Environment env;

    private Boolean isRegistered = false;
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    public void setIsRegistered(Boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean getIsRegistered() {
        return this.isRegistered;
    }

    public String getHostStock() {
        return this.env.getProperty("stocks.api.host");
    }

    public String getPortStock() {
        return this.env.getProperty("stocks.api.port");
    }

    public String getServerHost() {
        return this.env.getProperty("quote.host.name");
    }

    public String getServerPort() {
        return this.env.getProperty("server.port");
    }

    @CacheEvict(value = "stocks", allEntries = true)
    public void clearItemsCache() {
        logger.info("Cache cleared.");
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        clearItemsCache();
        logger.info("Cache cleared on application startup.");
    }

    public Optional<List<Stock>> getStocks() {
        return Util.getIdsStocks(getHostStock(), getPortStock());
    }

    @Cacheable("stocks")
    public Boolean checkIdInCache(String id) {
        Optional<List<Stock>> stocksAlreadyExistent = getStocks();
        if (stocksAlreadyExistent.isPresent()) {
            return stocksAlreadyExistent.get().stream().anyMatch(s -> s.getId().equals(id));
        }
        return false;
    }

    @Scheduled(fixedRate = 60000)
    public void tryAddNotification() {
        if (!getIsRegistered()) {
            checkConnection(Util.connectToStockManager(getServerHost(), getServerPort() ,getHostStock(), getPortStock()));
        }
    }

    public void checkConnection(ResponseEntity<Host> response) {
        if (response.getStatusCode() == HttpStatus.CREATED) {
            logger.info("Host added to the notification system!");
            setIsRegistered(true);
        } else {
            logger.error("Request failed with status code: " + response.getStatusCode());
        }
    }

    @PostConstruct
    public void init() {
        checkConnection(Util.connectToStockManager(getServerHost(), getServerPort(), getHostStock(), getPortStock()));
    }

    @Transactional
    public ResponseEntity<StockQuote> addStockQuote(StockQuote stock) {
        if (!getIsRegistered()) {
            logger.info("Host isn't registered. Creation of quotes isn't ensured by the Stock Manager.");
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        if (!checkIdInCache(stock.getId())) {
            logger.info("Stock does not exist in the Stock Manager. Register it first in the Stock Manager service.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        StockQuote savedStock = stockQuoteRepository.save(stock);
        return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
    }

    public Iterable<StockQuote> getAllStockQuotes() {
        return stockQuoteRepository.findAll();
    }

    public ResponseEntity<StockQuote> getQuote(String id) {
        Optional<StockQuote> quote = stockQuoteRepository.findById(id);
        return quote.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}


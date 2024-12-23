package com.example.stockmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.stockmanager.model.Stock;
import com.example.stockmanager.service.StockManagerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping(path="/stocks")
public class StockController {

    @Autowired
    private StockManagerService stockService;

    @PostConstruct
    public void init() {
        stockService.initializeStocks();
    }

    @Operation(summary = "Add a new stock", description = "Adds a new stock to the repository and clears the cache notification")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully added stock",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Stock.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content)
    })
    @PostMapping
    public @ResponseBody ResponseEntity<Stock> addStock(@Parameter(description = "Stock object to be added", required = true) @RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

    @Operation(summary = "Get all stocks", description = "Retrieves a list of all stocks")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Stock.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content)
    })
    @GetMapping
    public @ResponseBody Iterable<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }
}

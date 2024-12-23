package com.example.stockquotemanager.controller;

import com.example.stockquotemanager.model.StockQuote;
import com.example.stockquotemanager.service.StockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stockquote")
public class StockController {

    @Autowired
    private StockService stockService;

    @Operation(summary = "Add a new stock quote", description = "Adds a new stock quote to the repository")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully added stock quote",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StockQuote.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public @ResponseBody ResponseEntity<StockQuote> addStockQuote(@Parameter(description = "StockQuote object to be added", required = true) @RequestBody StockQuote stock) {
        return stockService.addStockQuote(stock);
    }

    @Operation(summary = "Get all stock quotes", description = "Retrieves a list of all stock quotes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StockQuote.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public @ResponseBody Iterable<StockQuote> getAllStockQuotes() {
        return stockService.getAllStockQuotes();
    }

    @Operation(summary = "Get stock quote by ID", description = "Retrieves a stock quote by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved stock quote",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = StockQuote.class)) }),
        @ApiResponse(responseCode = "404", description = "Stock quote not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(path = "/quote_by_id")
    public @ResponseBody ResponseEntity<StockQuote> getQuote(@RequestParam String id) {
        return stockService.getQuote(id);
    }

    @Operation(summary = "Clear stock cache", description = "Clears the stock items cache")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cache cleared successfully", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping(path = "/clear_cache")
    public @ResponseBody ResponseEntity<String> clearStockCache() {
        stockService.clearItemsCache();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


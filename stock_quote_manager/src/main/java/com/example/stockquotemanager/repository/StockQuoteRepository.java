package com.example.stockquotemanager.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.stockquotemanager.model.StockQuote;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface StockQuoteRepository extends CrudRepository<StockQuote, String> {

}

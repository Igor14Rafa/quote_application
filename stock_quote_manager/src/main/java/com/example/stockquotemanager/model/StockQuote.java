package com.example.stockquotemanager.model;

import com.example.stockquotemanager.util.RawJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StockQuote {

    @Id
    private String id;
    
    @JsonDeserialize(using = RawJsonDeserializer.class)
    private String quote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }    

}

package com.example.stockquotemanager.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.stockquotemanager.model.Host;
import com.example.stockquotemanager.model.Stock;

public final class Util {
    
    public static RestTemplate restTemplate = new RestTemplate();

    public static String getHostEndpoint (String host_stocks, String port_stocks, String endpoint) {
        String url = "http://" + host_stocks + ":" + port_stocks + "/" + endpoint;
        return url;
    }
    
    public static ResponseEntity<Host> connectToStockManager (String hostname_quote, String port_quote, String hostname, String port) {
        try {
            String url = getHostEndpoint(hostname, port, "hosts/notification");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Host host = new Host(hostname_quote, Integer.parseInt(port_quote), "stockquote");
            HttpEntity<Host> request = new HttpEntity<Host>(host, headers);
            ResponseEntity<Host> response = restTemplate.postForEntity(url, request, Host.class);

            return response;

        } catch (Exception e) {
            return new ResponseEntity<Host>(HttpStatus.BAD_REQUEST);
        }
    }

    public static Optional<List<Stock>> getIdsStocks (String host, String port) {
        try {
            String url = getHostEndpoint(host, port, "stocks");
            Stock[] resp = restTemplate.getForObject(url, Stock[].class);
            return Optional.of(Arrays.asList(resp));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

}

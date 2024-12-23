package com.example.stockmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.stockmanager.model.Host;
import com.example.stockmanager.service.StockManagerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path="/hosts")
public class HostController {

    @Autowired
    private StockManagerService hostService;

    @Operation(summary = "View a list of available hosts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public @ResponseBody Iterable<Host> getAllHosts() {
        return hostService.getAllHosts();
    }

    @Operation(summary = "Add a host notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added host notification"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("notification")
    public @ResponseBody ResponseEntity<Host> addNotification(@Param(value = "Host object to be added") @RequestBody Host host) {
        return hostService.addNotification(host);
    }
}

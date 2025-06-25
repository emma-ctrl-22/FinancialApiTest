package com.example.financialapiapplication.controller;

import com.example.financialapiapplication.dto.DataListPaymentResponse;
import com.example.financialapiapplication.dto.TransactionFilterRequest;
import com.example.financialapiapplication.service.FinancialTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class FinancialTransactionController {
    
    private final FinancialTransactionService service;
    
    @Autowired
    public FinancialTransactionController(FinancialTransactionService service) {
        this.service = service;
    }
    
    @GetMapping
    public Mono<ResponseEntity<DataListPaymentResponse>> getTransactionsWithFilters(
            @Valid TransactionFilterRequest request) {
        
        return service.getTransactionsWithFilters(request)
                .map(response -> ResponseEntity.ok(response))
                .onErrorResume(WebClientResponseException.class, error -> {
                    // Handle WebClientResponseException errors by returning a ResponseEntity with the error status code
                    return Mono.just(ResponseEntity.status(error.getStatusCode()).build());
                })
                .onErrorResume(Exception.class, error -> {
                    // Handle other exceptions
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }
} 
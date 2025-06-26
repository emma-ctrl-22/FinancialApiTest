package com.example.financialapiapplication.service;

import com.example.financialapiapplication.dto.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * Service responsible for payment-related operations.
 * Extends BaseService to inherit common functionality.
 * Follows Single Responsibility Principle - only handles payment operations.
 */
@Service
public class  PaymentService extends BaseService {
    
    private final WebClient webClient;
    
    @Autowired
    public PaymentService(WebClient webClient) {
        this.webClient = webClient;
    }
    
    /**
     * Retrieves payment information from external service.
     * Single Responsibility: Only handles payment retrieval.
     */
    public Mono<Payment> retrievePayment(String paymentId) {
        if (!isValidParameter(paymentId)) {
            return createErrorResponse("Payment ID cannot be null or empty");
        }
        
        return webClient.get()
                .uri("/payments/{paymentId}", paymentId)
                .retrieve()
                .bodyToMono(Payment.class)
                .onErrorResume(WebClientResponseException.class, error -> 
                        handleWebClientError(error, createDefaultPayment(paymentId)));
    }
    
    /**
     * Creates a default payment when external service fails.
     */
    private Payment createDefaultPayment(String paymentId) {
        Payment defaultPayment = new Payment();
        defaultPayment.setId(paymentId);
        defaultPayment.setStatus("UNKNOWN");
        return defaultPayment;
    }
} 
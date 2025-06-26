package com.example.financialapiapplication.service;

import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * Abstract base service class providing common functionality.
 * Demonstrates inheritance and code reuse principles.
 */
public abstract class BaseService {

    protected <T> Mono<T> handleWebClientError(WebClientResponseException error, T defaultValue) {
        // Log the error for debugging
        System.err.println("WebClient error: " + error.getMessage());
        return Mono.just(defaultValue);
    }
    
    /**
     * Common validation method for service parameters.
     */
    protected boolean isValidParameter(String parameter) {
        return parameter != null && !parameter.trim().isEmpty();
    }
    
    /**
     * Common method to create error response.
     */
    protected <T> Mono<T> createErrorResponse(String errorMessage) {
        System.err.println("Service error: " + errorMessage);
        return Mono.error(new RuntimeException(errorMessage));
    }
} 
package com.example.financialapiapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }
    
    @GetMapping("/sample-request")
    public String sampleRequest() {
        return """
                Sample request URL:
                GET /api/transactions?dateFrom=2024-01-01T00:00:00&dateTo=2024-12-31T23:59:59&userId=USER001&service=PAYMENT_SERVICE&status=COMPLETED&reference=REF001&offset=0&limit=10
                
                Available filter parameters:
                - dateFrom: Start date (ISO format)
                - dateTo: End date (ISO format)
                - userId: User ID
                - service: Service type
                - status: Transaction status
                - reference: Reference number
                - offset: Pagination offset (default: 0)
                - limit: Page size (default: 10)
                """;
    }
} 
package com.example.financialapiapplication.service;

import com.example.financialapiapplication.dto.DataListPaymentResponse;
import com.example.financialapiapplication.dto.Payment;
import com.example.financialapiapplication.dto.TransactionFilterRequest;
import com.example.financialapiapplication.model.FinancialTransaction;
import com.example.financialapiapplication.repository.IFinancialTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test class for FinancialTransactionService.
 * Demonstrates testing principles and OOP concepts.
 */
@ExtendWith(MockitoExtension.class)
class FinancialTransactionServiceTest {
    
    @Mock
    private IFinancialTransactionRepository repository;
    
    @Mock
    private PaymentService paymentService;
    
    @Mock
    private HateoasLinkService hateoasLinkService;
    
    @InjectMocks
    private FinancialTransactionService service;
    
    private FinancialTransaction testTransaction;
    private Payment testPayment;
    private TransactionFilterRequest testRequest;
    
    @BeforeEach
    void setUp() {
        // Setup test data
        testTransaction = new FinancialTransaction(
                "PAY001", "USER001", "PAYMENT_SERVICE", "COMPLETED",
                "REF001", new BigDecimal("100.50"), LocalDateTime.now()
        );
        
        testPayment = new Payment();
        testPayment.setId("PAY001");
        testPayment.setUserId("USER001");
        testPayment.setService("PAYMENT_SERVICE");
        testPayment.setStatus("COMPLETED");
        testPayment.setReference("REF001");
        testPayment.setAmount(new BigDecimal("100.50"));
        
        testRequest = new TransactionFilterRequest();
        testRequest.setOffset(0);
        testRequest.setLimit(10);
        testRequest.setUserId("USER001");
    }
    
    @Test
    void testGetTransactionsWithFilters_Success() {
        // Arrange
        List<FinancialTransaction> transactions = Arrays.asList(testTransaction);
        Page<FinancialTransaction> page = new PageImpl<>(transactions, PageRequest.of(0, 10), 1);
        
        when(repository.findTransactionsWithFilters(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString(),
                anyString(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(page);
        
        when(paymentService.retrievePayment(anyString()))
                .thenReturn(reactor.core.publisher.Mono.just(testPayment));
        
        when(hateoasLinkService.createLinks(any(), any()))
                .thenReturn(Arrays.asList());
        
        // Act & Assert
        StepVerifier.create(service.getTransactionsWithFilters(testRequest))
                .expectNextMatches(response -> {
                    return response.getData() != null && 
                           response.getData().size() == 1 &&
                           response.getData().get(0).getId().equals("PAY001");
                })
                .verifyComplete();
    }
    
    @Test
    void testGetTransactionsWithFilters_EmptyResult() {
        // Arrange
        Page<FinancialTransaction> emptyPage = new PageImpl<>(Arrays.asList(), PageRequest.of(0, 10), 0);
        
        when(repository.findTransactionsWithFilters(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString(),
                anyString(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(emptyPage);
        
        when(hateoasLinkService.createLinks(any(), any()))
                .thenReturn(Arrays.asList());
        
        // Act & Assert
        StepVerifier.create(service.getTransactionsWithFilters(testRequest))
                .expectNextMatches(response -> 
                        response.getData() != null && response.getData().isEmpty())
                .verifyComplete();
    }
    
    @Test
    void testGetTransactionsWithFilters_WithAllFilters() {
        // Arrange
        TransactionFilterRequest fullRequest = new TransactionFilterRequest();
        fullRequest.setDateFrom(LocalDateTime.now().minusDays(1));
        fullRequest.setDateTo(LocalDateTime.now());
        fullRequest.setUserId("USER001");
        fullRequest.setService("PAYMENT_SERVICE");
        fullRequest.setStatus("COMPLETED");
        fullRequest.setReference("REF001");
        fullRequest.setOffset(0);
        fullRequest.setLimit(5);
        
        List<FinancialTransaction> transactions = Arrays.asList(testTransaction);
        Page<FinancialTransaction> page = new PageImpl<>(transactions, PageRequest.of(0, 5), 1);
        
        when(repository.findTransactionsWithFilters(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString(),
                anyString(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(page);
        
        when(paymentService.retrievePayment(anyString()))
                .thenReturn(reactor.core.publisher.Mono.just(testPayment));
        
        when(hateoasLinkService.createLinks(any(), any()))
                .thenReturn(Arrays.asList());
        
        // Act & Assert
        StepVerifier.create(service.getTransactionsWithFilters(fullRequest))
                .expectNextMatches(response -> 
                        response.getData() != null && response.getData().size() == 1)
                .verifyComplete();
    }
} 
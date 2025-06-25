package com.example.financialapiapplication.service;

import com.example.financialapiapplication.dto.DataListPaymentResponse;
import com.example.financialapiapplication.dto.Payment;
import com.example.financialapiapplication.dto.TransactionFilterRequest;
import com.example.financialapiapplication.model.FinancialTransaction;
import com.example.financialapiapplication.repository.FinancialTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

/**
 * Service class responsible for handling financial transaction business logic.
 * Follows Single Responsibility Principle - only handles transaction operations.
 */
@Service
public class FinancialTransactionService {
    
    private final FinancialTransactionRepository repository;
    private final PaymentService paymentService;
    private final HateoasLinkService hateoasLinkService;
    
    @Autowired
    public FinancialTransactionService(FinancialTransactionRepository repository, 
                                     PaymentService paymentService,
                                     HateoasLinkService hateoasLinkService) {
        this.repository = repository;
        this.paymentService = paymentService;
        this.hateoasLinkService = hateoasLinkService;
    }
    
    /**
     * Retrieves financial transactions with filters and processes them.
     * Implements the complete workflow as specified in requirements.
     */
    public Mono<DataListPaymentResponse> getTransactionsWithFilters(TransactionFilterRequest request) {
        // Step 1: Create Pageable Object
        Pageable pageable = createPageable(request);
        
        // Step 2: Retrieve Financial Transactions
        Page<FinancialTransaction> transactionPage = retrieveTransactions(request, pageable);
        
        // Step 3: Extract and Transform Data
        Flux<FinancialTransaction> transactionFlux = Flux.fromIterable(transactionPage.getContent());
        
        // Step 4: Process Each Financial Transaction
        return processTransactions(transactionFlux)
                .collectList()
                .map(payments -> {
                    // Step 5: Sort Payments
                    sortPayments(payments);
                    
                    // Step 6: Create Response Object
                    return createResponse(payments, request, transactionPage);
                });
    }
    
    /**
     * Creates a Pageable object from the request parameters.
     * Single Responsibility: Only handles pagination logic.
     */
    private Pageable createPageable(TransactionFilterRequest request) {
        int page = request.getOffset() / request.getLimit();
        return PageRequest.of(page, request.getLimit());
    }
    
    /**
     * Retrieves transactions from the repository with filters.
     * Single Responsibility: Only handles data retrieval.
     */
    private Page<FinancialTransaction> retrieveTransactions(TransactionFilterRequest request, Pageable pageable) {
        return repository.findTransactionsWithFilters(
                request.getDateFrom(),
                request.getDateTo(),
                request.getUserId(),
                request.getService(),
                request.getStatus(),
                request.getReference(),
                pageable
        );
    }
    
    /**
     * Processes each transaction by calling external service.
     * Single Responsibility: Only handles transaction processing.
     */
    private Flux<Payment> processTransactions(Flux<FinancialTransaction> transactionFlux) {
        return transactionFlux
                .flatMap(transaction -> paymentService.retrievePayment(transaction.getPaymentId())
                        .map(payment -> mapTransactionToPayment(transaction, payment))
                        .onErrorResume(WebClientResponseException.class, error -> 
                                handlePaymentError(transaction, error)));
    }
    
    /**
     * Maps a financial transaction to a payment object.
     * Single Responsibility: Only handles object mapping.
     */
    private Payment mapTransactionToPayment(FinancialTransaction transaction, Payment payment) {
        payment.setId(transaction.getPaymentId());
        payment.setUserId(transaction.getUserId());
        payment.setService(transaction.getService());
        payment.setStatus(transaction.getStatus());
        payment.setReference(transaction.getReference());
        payment.setAmount(transaction.getAmount());
        payment.setCreatedAt(transaction.getCreatedAt());
        payment.setUpdatedAt(transaction.getUpdatedAt());
        return payment;
    }
    
    /**
     * Handles payment retrieval errors by creating a default payment.
     * Single Responsibility: Only handles error recovery.
     */
    private Mono<Payment> handlePaymentError(FinancialTransaction transaction, WebClientResponseException error) {
        Payment defaultPayment = createDefaultPayment(transaction);
        return Mono.just(defaultPayment);
    }
    
    /**
     * Creates a default payment from transaction data.
     * Single Responsibility: Only handles default object creation.
     */
    private Payment createDefaultPayment(FinancialTransaction transaction) {
        Payment defaultPayment = new Payment();
        defaultPayment.setId(transaction.getPaymentId());
        defaultPayment.setUserId(transaction.getUserId());
        defaultPayment.setService(transaction.getService());
        defaultPayment.setStatus(transaction.getStatus());
        defaultPayment.setReference(transaction.getReference());
        defaultPayment.setAmount(transaction.getAmount());
        defaultPayment.setCreatedAt(transaction.getCreatedAt());
        defaultPayment.setUpdatedAt(transaction.getUpdatedAt());
        return defaultPayment;
    }
    
    /**
     * Sorts payments by payment ID in descending order.
     * Single Responsibility: Only handles sorting logic.
     */
    private void sortPayments(List<Payment> payments) {
        payments.sort(Comparator.comparing(Payment::getId).reversed());
    }
    
    /**
     * Creates the final response object with data and links.
     * Single Responsibility: Only handles response creation.
     */
    private DataListPaymentResponse createResponse(List<Payment> payments, 
                                                 TransactionFilterRequest request, 
                                                 Page<FinancialTransaction> transactionPage) {
        DataListPaymentResponse response = new DataListPaymentResponse();
        response.setData(payments);
        response.setLinks(hateoasLinkService.createLinks(request, transactionPage));
        return response;
    }
} 
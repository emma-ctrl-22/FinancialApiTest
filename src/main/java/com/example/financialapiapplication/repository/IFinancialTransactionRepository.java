package com.example.financialapiapplication.repository;

import com.example.financialapiapplication.model.FinancialTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for financial transaction repository operations.
 * Follows Interface Segregation Principle - only defines transaction-related operations.
 */
public interface IFinancialTransactionRepository {
    
    /**
     * Finds transactions with filters and pagination.
     */
    Page<FinancialTransaction> findTransactionsWithFilters(
            LocalDateTime dateFrom,
            LocalDateTime dateTo,
            String userId,
            String service,
            String status,
            String reference,
            Pageable pageable);
    
    /**
     * Finds transactions by payment IDs.
     */
    List<FinancialTransaction> findByPaymentIdIn(List<String> paymentIds);
    
    /**
     * Saves a transaction.
     */
    FinancialTransaction save(FinancialTransaction transaction);
    
    /**
     * Deletes all transactions.
     */
    void deleteAll();
} 
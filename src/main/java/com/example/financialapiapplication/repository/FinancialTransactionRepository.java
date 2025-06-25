package com.example.financialapiapplication.repository;

import com.example.financialapiapplication.model.FinancialTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository implementation for financial transaction operations.
 * Follows Interface Segregation Principle and Dependency Inversion Principle.
 */
@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
    
    @Query("SELECT ft FROM FinancialTransaction ft WHERE " +
           "(:dateFrom IS NULL OR ft.transactionDate >= :dateFrom) AND " +
           "(:dateTo IS NULL OR ft.transactionDate <= :dateTo) AND " +
           "(:userId IS NULL OR ft.userId = :userId) AND " +
           "(:service IS NULL OR ft.service = :service) AND " +
           "(:status IS NULL OR ft.status = :status) AND " +
           "(:reference IS NULL OR ft.reference = :reference)")
    Page<FinancialTransaction> findTransactionsWithFilters(
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("userId") String userId,
            @Param("service") String service,
            @Param("status") String status,
            @Param("reference") String reference,
            Pageable pageable);
    
    List<FinancialTransaction> findByPaymentIdIn(List<String> paymentIds);
} 
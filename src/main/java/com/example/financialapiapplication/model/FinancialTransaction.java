package com.example.financialapiapplication.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "payment_id")
    private String paymentId;
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "service")
    private String service;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "reference")
    private String reference;
    
    @Column(name = "amount")
    private BigDecimal amount;
    
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Default constructor
    public FinancialTransaction() {}
    
    // Constructor with all fields
    public FinancialTransaction(String paymentId, String userId, String service, String status, 
                               String reference, BigDecimal amount, LocalDateTime transactionDate) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.service = service;
        this.status = status;
        this.reference = reference;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getService() {
        return service;
    }
    
    public void setService(String service) {
        this.service = service;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getReference() {
        return reference;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

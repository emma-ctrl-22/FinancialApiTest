package com.example.financialapiapplication.dto;

import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class TransactionFilterRequest {
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateFrom;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTo;
    
    private String userId;
    private String service;
    private String status;
    private String reference;
    
    @Min(value = 0, message = "Offset must be non-negative")
    private Integer offset = 0;
    
    @Min(value = 1, message = "Limit must be at least 1")
    private Integer limit = 10;
    
    // Default constructor
    public TransactionFilterRequest() {}
    
    // Constructor with all fields
    public TransactionFilterRequest(LocalDateTime dateFrom, LocalDateTime dateTo, String userId, 
                                   String service, String status, String reference, 
                                   Integer offset, Integer limit) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.userId = userId;
        this.service = service;
        this.status = status;
        this.reference = reference;
        this.offset = offset != null ? offset : 0;
        this.limit = limit != null ? limit : 10;
    }
    
    // Getters and Setters
    public LocalDateTime getDateFrom() {
        return dateFrom;
    }
    
    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    public LocalDateTime getDateTo() {
        return dateTo;
    }
    
    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
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
    
    public Integer getOffset() {
        return offset;
    }
    
    public void setOffset(Integer offset) {
        this.offset = offset != null ? offset : 0;
    }
    
    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit != null ? limit : 10;
    }
} 
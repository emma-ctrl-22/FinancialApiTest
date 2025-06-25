package com.example.financialapiapplication.config;

import com.example.financialapiapplication.model.FinancialTransaction;
import com.example.financialapiapplication.repository.FinancialTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final FinancialTransactionRepository repository;
    
    @Autowired
    public DataInitializer(FinancialTransactionRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        repository.deleteAll();
        
        // Create sample financial transactions
        LocalDateTime now = LocalDateTime.now();
        
        FinancialTransaction transaction1 = new FinancialTransaction(
                "PAY001", "USER001", "PAYMENT_SERVICE", "COMPLETED", 
                "REF001", new BigDecimal("100.50"), now.minusDays(1)
        );
        
        FinancialTransaction transaction2 = new FinancialTransaction(
                "PAY002", "USER002", "TRANSFER_SERVICE", "PENDING", 
                "REF002", new BigDecimal("250.75"), now.minusHours(6)
        );
        
        FinancialTransaction transaction3 = new FinancialTransaction(
                "PAY003", "USER001", "PAYMENT_SERVICE", "COMPLETED", 
                "REF003", new BigDecimal("75.25"), now.minusHours(2)
        );
        
        FinancialTransaction transaction4 = new FinancialTransaction(
                "PAY004", "USER003", "WITHDRAWAL_SERVICE", "FAILED", 
                "REF004", new BigDecimal("500.00"), now.minusMinutes(30)
        );
        
        FinancialTransaction transaction5 = new FinancialTransaction(
                "PAY005", "USER002", "TRANSFER_SERVICE", "COMPLETED", 
                "REF005", new BigDecimal("150.00"), now.minusMinutes(15)
        );
        
        // Save all transactions
        repository.save(transaction1);
        repository.save(transaction2);
        repository.save(transaction3);
        repository.save(transaction4);
        repository.save(transaction5);
        
        System.out.println("Sample data initialized successfully!");
    }
} 
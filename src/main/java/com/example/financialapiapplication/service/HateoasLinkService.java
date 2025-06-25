package com.example.financialapiapplication.service;

import com.example.financialapiapplication.dto.TransactionFilterRequest;
import com.example.financialapiapplication.model.FinancialTransaction;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for creating HATEOAS links.
 * Follows Single Responsibility Principle - only handles link creation.
 */
@Service
public class HateoasLinkService {
    
    /**
     * Creates HATEOAS links for the response.
     * Single Responsibility: Only handles link creation logic.
     */
    public List<Link> createLinks(TransactionFilterRequest request, Page<FinancialTransaction> page) {
        List<Link> links = new ArrayList<>();
        
        // Self link
        links.add(Link.of("/api/transactions", "self"));
        
        // Add pagination links if needed
        if (page.hasNext()) {
            int nextOffset = request.getOffset() + request.getLimit();
            String nextLink = String.format("/api/transactions?offset=%d&limit=%d", nextOffset, request.getLimit());
            links.add(Link.of(nextLink, "next"));
        }
        
        if (page.hasPrevious()) {
            int prevOffset = Math.max(0, request.getOffset() - request.getLimit());
            String prevLink = String.format("/api/transactions?offset=%d&limit=%d", prevOffset, request.getLimit());
            links.add(Link.of(prevLink, "previous"));
        }
        
        return links;
    }
} 
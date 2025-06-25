package com.example.financialapiapplication.dto;

import org.springframework.hateoas.Link;
import java.util.List;

public class DataListPaymentResponse {
    
    private List<Payment> data;
    private List<Link> links;
    
    // Default constructor
    public DataListPaymentResponse() {}
    
    // Constructor with data and links
    public DataListPaymentResponse(List<Payment> data, List<Link> links) {
        this.data = data;
        this.links = links;
    }
    
    // Constructor with only data
    public DataListPaymentResponse(List<Payment> data) {
        this.data = data;
    }
    
    // Getters and Setters
    public List<Payment> getData() {
        return data;
    }
    
    public void setData(List<Payment> data) {
        this.data = data;
    }
    
    public List<Link> getLinks() {
        return links;
    }
    
    public void setLinks(List<Link> links) {
        this.links = links;
    }
} 
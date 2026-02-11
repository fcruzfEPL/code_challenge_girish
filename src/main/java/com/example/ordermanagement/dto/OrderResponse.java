package com.example.ordermanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderResponse {

    private Long id;
    private String orderNumber;
    private String customerName;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private List<ItemResponse> items = new ArrayList<>();

    // Constructors
    public OrderResponse() {}

    public OrderResponse(Long id, String orderNumber, String customerName,
                         LocalDateTime createdAt, BigDecimal totalAmount,
                         List<ItemResponse> items) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }
}
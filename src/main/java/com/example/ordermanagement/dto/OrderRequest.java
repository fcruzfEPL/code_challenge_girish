package com.example.ordermanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

    @NotBlank(message = "Order number is required")
    private String orderNumber;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<ItemRequest> items = new ArrayList<>();

    // Constructors
    public OrderRequest() {}

    public OrderRequest(String orderNumber, String customerName, List<ItemRequest> items) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.items = items;
    }

    // Getters and Setters
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

    public List<ItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemRequest> items) {
        this.items = items;
    }
}
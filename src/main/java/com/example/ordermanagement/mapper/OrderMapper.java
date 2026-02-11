package com.example.ordermanagement.mapper;

import com.example.ordermanagement.dto.*;
import com.example.ordermanagement.entity.Item;
import com.example.ordermanagement.entity.Order;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setCustomerName(order.getCustomerName());
        response.setCreatedAt(order.getCreatedAt());
        response.setTotalAmount(order.getTotalAmount());
        response.setItems(order.getItems().stream()
                .map(this::itemToResponse)
                .collect(Collectors.toList()));
        return response;
    }

    public Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setOrderNumber(request.getOrderNumber());
        order.setCustomerName(request.getCustomerName());

        request.getItems().forEach(itemReq -> {
            Item item = itemRequestToEntity(itemReq);
            order.addItem(item);
        });

        return order;
    }

    public void updateEntity(Order order, OrderRequest request) {
        order.setOrderNumber(request.getOrderNumber());
        order.setCustomerName(request.getCustomerName());

        // Clear existing items
        order.getItems().clear();

        // Add new items
        request.getItems().forEach(itemReq -> {
            Item item = itemRequestToEntity(itemReq);
            order.addItem(item);
        });
    }

    private ItemResponse itemToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getSku(),
                item.getName(),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }

    private Item itemRequestToEntity(ItemRequest request) {
        Item item = new Item();
        item.setSku(request.getSku());
        item.setName(request.getName());
        item.setQuantity(request.getQuantity());
        item.setUnitPrice(request.getUnitPrice());
        return item;
    }
}
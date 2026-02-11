package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.OrderRequest;
import com.example.ordermanagement.dto.OrderResponse;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.exception.DuplicateOrderNumberException;
import com.example.ordermanagement.exception.ResourceNotFoundException;
import com.example.ordermanagement.mapper.OrderMapper;
import com.example.ordermanagement.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toResponse(order);
    }

    public OrderResponse create(OrderRequest request) {
        if (orderRepository.existsByOrderNumber(request.getOrderNumber())) {
            throw new DuplicateOrderNumberException(
                    "Order number already exists: " + request.getOrderNumber());
        }

        Order order = orderMapper.toEntity(request);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    public OrderResponse update(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Check if order number is being changed to one that already exists
        if (!order.getOrderNumber().equals(request.getOrderNumber()) &&
                orderRepository.existsByOrderNumber(request.getOrderNumber())) {
            throw new DuplicateOrderNumberException(
                    "Order number already exists: " + request.getOrderNumber());
        }

        orderMapper.updateEntity(order, request);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponse(updatedOrder);
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}
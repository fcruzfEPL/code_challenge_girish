package com.example.ordermanagement.service;

import com.example.ordermanagement.dto.ItemRequest;
import com.example.ordermanagement.dto.OrderRequest;
import com.example.ordermanagement.dto.OrderResponse;
import com.example.ordermanagement.entity.Order;
import com.example.ordermanagement.exception.DuplicateOrderNumberException;
import com.example.ordermanagement.exception.ResourceNotFoundException;
import com.example.ordermanagement.mapper.OrderMapper;
import com.example.ordermanagement.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderRequest orderRequest;
    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setOrderNumber("ORD-2025-0001");
        order.setCustomerName("Test Customer");

        ItemRequest itemRequest = new ItemRequest("SKU-001", "Test Item", 2, BigDecimal.valueOf(100));
        orderRequest = new OrderRequest("ORD-2025-0001", "Test Customer", Arrays.asList(itemRequest));

        orderResponse = new OrderResponse();
        orderResponse.setId(1L);
        orderResponse.setOrderNumber("ORD-2025-0001");
    }

    @Test
    void testFindAll() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        when(orderMapper.toResponse(any(Order.class))).thenReturn(orderResponse);

        List<OrderResponse> result = orderService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toResponse(order)).thenReturn(orderResponse);

        OrderResponse result = orderService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.findById(1L));
    }

    @Test
    void testCreateSuccess() {
        when(orderRepository.existsByOrderNumber(anyString())).thenReturn(false);
        when(orderMapper.toEntity(orderRequest)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toResponse(order)).thenReturn(orderResponse);

        OrderResponse result = orderService.create(orderRequest);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateDuplicateOrderNumber() {
        when(orderRepository.existsByOrderNumber(anyString())).thenReturn(true);

        assertThrows(DuplicateOrderNumberException.class, () -> orderService.create(orderRequest));
    }

    @Test
    void testDeleteSuccess() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.delete(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> orderService.delete(1L));
    }
}
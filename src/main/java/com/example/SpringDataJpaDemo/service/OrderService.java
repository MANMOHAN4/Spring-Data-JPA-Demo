package com.example.SpringDataJpaDemo.service;

import com.example.SpringDataJpaDemo.dto.CreateOrderDto;
import com.example.SpringDataJpaDemo.dto.OrderDto;
import com.example.SpringDataJpaDemo.dto.UserDto;
import com.example.SpringDataJpaDemo.entities.Order;
import com.example.SpringDataJpaDemo.entities.User;
import com.example.SpringDataJpaDemo.repository.OrderRepository;
import com.example.SpringDataJpaDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderDto createOrder(Long userId, CreateOrderDto createOrderDto) {
        User user = userRepository.findById(userId).orElseThrow();
        Order order = new Order();
        order.setProductName(createOrderDto.getProductName());

        Order savedOrder = orderRepository.save(order);
        return new OrderDto(savedOrder.getId(), savedOrder.getProductName(),
                new UserDto(savedOrder.getUser().getId(), savedOrder.getUser().getName(), savedOrder.getUser().getEmail()));
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtos = new ArrayList<>();

        orders.forEach(order -> {
            OrderDto orderDto = new OrderDto(
                    order.getId(), order.getProductName(),
                    new UserDto(order.getUser().getId(), order.getUser().getName(), order.getUser().getEmail())
            );
            orderDtos.add(orderDto);
        });

        return orderDtos;
    }
}

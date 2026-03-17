package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.OrderDto;
import com.self.springbootprojectstarter.exception.OrderNotFoundException;
import com.self.springbootprojectstarter.model.Order;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.repository.OrderRepository;
import com.self.springbootprojectstarter.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderDto dto = modelMapper.map(order, OrderDto.class);
                    dto.setUserId(order.getUser().getId());
                    return dto;
                })
                .toList();
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        OrderDto dto = modelMapper.map(order, OrderDto.class);
        dto.setUserId(order.getUser().getId());
        return dto;
    }

    public OrderDto addOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(user);
        orderRepository.save(order);
        return orderDto;
    }

    public void updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        modelMapper.map(orderDto, order);
        order.setUser(user);
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
    }
}

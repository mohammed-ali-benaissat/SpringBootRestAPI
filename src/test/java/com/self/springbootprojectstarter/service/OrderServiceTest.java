package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.OrderDto;
import com.self.springbootprojectstarter.exception.UserNotFoundException;
import com.self.springbootprojectstarter.model.Order;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.repository.OrderRepository;
import com.self.springbootprojectstarter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void addOrder_userNotFound_throwsUserNotFoundException() {
        // Arrange: user lookup fails.
        OrderDto dto = new OrderDto("phone", 10L);
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        // Act + Assert: service translates missing user to domain exception.
        assertThatThrownBy(() -> orderService.addOrder(dto))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void addOrder_returnsSavedDtoWithUserId() {
        // Arrange: user exists, mapper converts DTO to entity, repository saves.
        OrderDto dto = new OrderDto("phone", 10L);
        User user = new User();
        user.setId(10L);
        Order order = new Order();
        Order saved = new Order();
        saved.setId(5L);
        saved.setUser(user);
        OrderDto savedDto = new OrderDto("phone", null);
        savedDto.setId(5L);

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(modelMapper.map(dto, Order.class)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(saved);
        when(modelMapper.map(saved, OrderDto.class)).thenReturn(savedDto);

        // Act: create order.
        OrderDto result = orderService.addOrder(dto);

        // Assert: returned DTO has saved id and linked user id.
        assertThat(result).isEqualTo(savedDto);
        assertThat(result.getUserId()).isEqualTo(10L);
        verify(orderRepository).save(order);
    }
}

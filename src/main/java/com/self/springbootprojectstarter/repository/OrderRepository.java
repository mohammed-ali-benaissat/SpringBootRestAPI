package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    @EntityGraph(attributePaths = "user")
    List<Order> findAll();

    @Override
    @EntityGraph(attributePaths = "user")
    Optional<Order> findById(Long id);
}

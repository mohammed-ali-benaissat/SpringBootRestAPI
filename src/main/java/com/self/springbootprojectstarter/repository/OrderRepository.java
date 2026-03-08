package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

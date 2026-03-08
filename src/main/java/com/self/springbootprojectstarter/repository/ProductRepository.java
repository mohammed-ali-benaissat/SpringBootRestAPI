package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}

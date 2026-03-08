package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

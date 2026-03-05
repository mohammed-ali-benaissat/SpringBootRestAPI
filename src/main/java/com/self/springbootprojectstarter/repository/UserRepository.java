package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean findByName(String name);
    List<User> findByEmail(String email);
    List<User> findByAgeGreaterThan(int age);
    List<User> findByAgeLessThan(int age);
    List<User> findByAgeGreaterThanOrderByAgeDesc(int age);
    boolean existsByEmail(String email);
    boolean existsByName(String name);




}

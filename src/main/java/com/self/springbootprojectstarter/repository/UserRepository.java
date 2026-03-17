package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(attributePaths = "profile")
    Page<User> findAll(Pageable pageable);

    Optional<User> findByName(String name);
    List<User> findByEmail(String email);
    List<User> findByAgeGreaterThan(int age);
    List<User> findByAgeLessThan(int age);
    List<User> findByAgeGreaterThanOrderByAgeDesc(int age);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByNameAndEmailAndAge(String name, String email, int age);




}

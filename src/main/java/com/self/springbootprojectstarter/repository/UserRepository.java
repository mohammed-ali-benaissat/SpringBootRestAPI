package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

// This interface is a Repository
// It extends JpaRepository<User, Long>
//   → User = entity class
//   → Long = primary key type
//
// By extending JpaRepository, we automatically get:
// - findAll()
// - findById()
// - save()
// - delete()
// - count()
// ...and many more
public interface UserRepository extends JpaRepository<User, Long> {

    // --------------------------------------------------
    // 🔹 FETCH USERS WITH PROFILE
    // --------------------------------------------------
    @Override
    @EntityGraph(attributePaths = "profile")
    Page<User> findAll(Pageable pageable);

    /**
     * 🔥 WHAT IS @EntityGraph?
     * - Tells JPA to fetch the "profile" relationship eagerly
     * - Normally, @ManyToOne or @OneToMany relationships can be LAZY
     *   → They only load when accessed (may cause multiple DB queries)
     * - Using @EntityGraph avoids N+1 query problem
     *
     * Example:
     * userRepository.findAll(pageable)
     * will fetch each user AND their profile in a single query
     */

    // --------------------------------------------------
    // 🔹 FINDERS BY SPRING METHOD NAME
    // --------------------------------------------------

    Optional<User> findByName(String name);
    // → Finds a single user by name
    // Optional = maybe exists, maybe not

    List<User> findByEmail(String email);
    // → Finds all users with this email (could be multiple)

    List<User> findByAgeGreaterThan(int age);
    // → Returns all users older than 'age'

    List<User> findByAgeLessThan(int age);
    // → Returns all users younger than 'age'

    List<User> findByAgeGreaterThanOrderByAgeDesc(int age);
    // → Returns users older than 'age', sorted descending by age

    // --------------------------------------------------
    // 🔹 EXISTENCE CHECKS
    // --------------------------------------------------

    boolean existsByEmail(String email);
    // → Returns true if any user exists with this email

    boolean existsByName(String name);
    // → Returns true if any user exists with this name

    boolean existsByNameAndEmailAndAge(String name, String email, int age);
    // → Returns true only if a user exists with ALL these properties
}
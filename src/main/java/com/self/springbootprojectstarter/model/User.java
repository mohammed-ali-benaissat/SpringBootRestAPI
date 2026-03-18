package com.self.springbootprojectstarter.model;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

// Marks this class as a JPA Entity → maps to a database table
@Entity

// Optional: specify table name explicitly (otherwise defaults to "user")
@Table(name = "users")
public class User {

    // ============================================================
    // 🔹 PRIMARY KEY
    // ============================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Database automatically generates unique IDs (auto-increment)
    private Long id;

    // ============================================================
    // 🔹 COLUMNS
    // ============================================================

    @Column(nullable = false, length = 100)
    // User's display name cannot be null and max 100 characters
    private String name;

    @Column(unique = true, nullable = false, length = 255)
    // Email must be unique (no two users can share it)
    private String email;

    @Column(nullable = false)
    // Age is required at DB level
    private int age;

    // Default constructor required by JPA
    public User() {}

    // ============================================================
    // 🔹 RELATIONSHIPS
    // ============================================================

    // Many users can share one profile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    /**
     * 🔥 IMPORTANT:
     * - fetch = LAZY → Profile is loaded only when accessed
     * - mappedBy = "user" → means the "user" field in Order owns the relationship
     * - cascade = ALL → if User is deleted, all orders are deleted automatically
     * - orphanRemoval = true → if an Order is removed from this Set, it’s deleted from DB
     *
     * Why Set<LinkedHashSet> instead of List?
     * - Avoid duplicates
     * - LinkedHashSet preserves insertion order
     * - Good practice for @OneToMany
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();

    // ============================================================
    // 🔹 GETTERS & SETTERS
    // ============================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
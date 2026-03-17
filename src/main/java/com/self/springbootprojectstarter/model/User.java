package com.self.springbootprojectstarter.model;


import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Database-generated primary key.
    private Long id;

    @Column(nullable = false, length = 100)
    // User's display name.
    private String name;

    @Column(unique = true, nullable = false, length = 255)
    // Unique email for login/identity.
    private String email;

    @Column(nullable = false)
    // Age is required at the database level.
    private int age;

    public User() {}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    // Optional link to a profile (many users can share one profile).
    private Profile profile;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // Orders owned by this user; cascades delete.
    private Set<Order> orders = new LinkedHashSet<>();


    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    //Getters
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

    // Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

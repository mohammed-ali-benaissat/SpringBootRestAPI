package com.self.springbootprojectstarter.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

/**
 * 🔹 Profile Entity
 *
 * Teaching points:
 * 1️⃣ Represents a user's profile in the system (bio, description, etc.)
 * 2️⃣ Connected to User via a @OneToMany relationship
 * 3️⃣ Used to demonstrate entity relationships and foreign keys
 */
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    // Primary key; database generates the value automatically
    private Long id;

    @NotNull
    @Column(name = "bio", nullable = false, length = 100)
    // Short biography or description text
    private String bio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile")
    // Each profile can be linked to many users
    // "mappedBy = profile" references the 'profile' field in User entity
    private Set<User> users;

    // =================== GETTERS & SETTERS ===================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }
}
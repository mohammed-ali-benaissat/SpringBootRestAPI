package com.self.springbootprojectstarter.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    // Database-generated primary key.
    private Long id;

    @NotNull
    @Column(name = "bio", nullable = false, length = 100)
    // Short biography text.
    private String bio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile")
    // Back-reference to users linked to this profile.
    private Set<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

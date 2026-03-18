package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 🔹 ProfileRepository
 *
 * Teaching points:
 * 1️⃣ Extends JpaRepository to provide standard CRUD operations
 * 2️⃣ Can add custom queries like findByBio or existsByBio
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    // Find profiles by bio content
    List<Profile> findByBio(String bio);

    // Check if a profile with the given bio exists
    boolean existsByBio(String bio);
}
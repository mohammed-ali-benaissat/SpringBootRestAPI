package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findByBio(String bio);
    boolean existsByBio(String bio);

}

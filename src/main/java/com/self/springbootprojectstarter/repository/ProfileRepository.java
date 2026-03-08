package com.self.springbootprojectstarter.repository;

import com.self.springbootprojectstarter.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {


}

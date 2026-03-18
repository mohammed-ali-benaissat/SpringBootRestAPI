package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.exception.ProfileNotFoundException;
import com.self.springbootprojectstarter.mapper.ProfileMapper;
import com.self.springbootprojectstarter.model.Profile;
import com.self.springbootprojectstarter.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 🔹 ProfileService
 *
 * Teaching points:
 * 1️⃣ Handles business logic and validations
 * 2️⃣ Talks to repository for database operations
 * 3️⃣ Talks to mapper to convert between entity and DTO
 * 4️⃣ Provides helper method for UserService to fetch Profile entity safely
 */
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    // ------------------- GET ALL PROFILES -------------------
    public List<ProfileDto> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(profileMapper::toDto) // Map entity → DTO
                .toList();
    }

    // ------------------- GET PROFILE BY ID -------------------
    public ProfileDto getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        return profileMapper.toDto(profile);
    }

    // ------------------- CREATE PROFILE -------------------
    public ProfileDto addProfile(ProfileDto dto) {
        Profile profile = profileMapper.toEntity(dto);
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    // ------------------- UPDATE PROFILE -------------------
    public void updateProfile(Long id, ProfileDto dto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        profileMapper.updateEntity(dto, profile);
        profileRepository.save(profile);
    }

    // ------------------- DELETE PROFILE -------------------
    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        profileRepository.delete(profile);
    }

    // ------------------- HELPER FOR USER SERVICE -------------------
    public Profile getProfileEntityById(Long id) {
        // Used when creating/updating User to set the profile
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }
}
package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.exception.ProfileNotFoundException;
import com.self.springbootprojectstarter.mapper.ProfileMapper;
import com.self.springbootprojectstarter.model.Profile;
import com.self.springbootprojectstarter.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public List<ProfileDto> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(profileMapper::toDto)
                .toList();
    }

    public ProfileDto getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        return profileMapper.toDto(profile);
    }

    public ProfileDto addProfile(ProfileDto dto) {
        Profile profile = profileMapper.toEntity(dto);
        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    public void updateProfile(Long id, ProfileDto dto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        profileMapper.updateEntity(dto, profile);
        profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        profileRepository.delete(profile);
    }

    // Helper method for UserMapper
    public Profile getProfileEntityById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
    }
}

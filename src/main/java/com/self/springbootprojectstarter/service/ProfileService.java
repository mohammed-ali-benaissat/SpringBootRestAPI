package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.exception.ProfileNotFoundException;
import com.self.springbootprojectstarter.model.Profile;
import com.self.springbootprojectstarter.repository.ProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public ProfileService(ProfileRepository profileRepository, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProfileDto> getAllProfiles() {
        return profileRepository.findAll()
                .stream()
                .map(profile -> modelMapper.map(profile, ProfileDto.class))
                .toList();
    }

    public ProfileDto getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        return modelMapper.map(profile, ProfileDto.class);
    }

    public void addProfile(ProfileDto profileDto) {
        Profile profile = modelMapper.map(profileDto, Profile.class);
        profileRepository.save(profile);
    }

    public void updateProfile(Long id, ProfileDto profileDto) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        modelMapper.map(profileDto, profile);
        profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException(id));
        profileRepository.delete(profile);
    }
}

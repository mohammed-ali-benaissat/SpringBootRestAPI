package com.self.springbootprojectstarter.mapper;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.model.Profile;
import com.self.springbootprojectstarter.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final ProfileService profileService;

    public UserMapper(ModelMapper modelMapper, ProfileService profileService) {
        this.modelMapper = modelMapper;
        this.profileService = profileService;
    }

    // ------------------- ENTITY → DTO -------------------
    public UserDto toDto(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);

        // Custom mapping for nested field
        if (user.getProfile() != null) {
            dto.setProfileId(user.getProfile().getId());
        }

        return dto;
    }

    // ------------------- DTO → ENTITY -------------------
    public User toEntity(UserDto dto) {
        User user = modelMapper.map(dto, User.class);

        // Attach Profile if profileId is present
        if (dto.getProfileId() != null) {
            Profile profile = profileService.getProfileEntityById(dto.getProfileId());
            user.setProfile(profile);
        } else {
            user.setProfile(null);
        }

        return user;
    }

    // ------------------- UPDATE EXISTING ENTITY -------------------
    public void updateEntity(UserDto dto, User user) {
        modelMapper.map(dto, user);

        if (dto.getProfileId() != null) {
            Profile profile = profileService.getProfileEntityById(dto.getProfileId());
            user.setProfile(profile);
        } else {
            user.setProfile(null);
        }
    }
}
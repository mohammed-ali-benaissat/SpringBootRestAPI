package com.self.springbootprojectstarter.mapper;

import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.model.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    private final ModelMapper modelMapper;

    public ProfileMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProfileDto toDto(Profile profile) {
        return modelMapper.map(profile, ProfileDto.class);
    }

    public Profile toEntity(ProfileDto dto) {
        return modelMapper.map(dto, Profile.class);
    }

    public void updateEntity(ProfileDto dto, Profile profile) {
        modelMapper.map(dto, profile);
    }
}
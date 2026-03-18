package com.self.springbootprojectstarter.mapper;

import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.model.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * 🔹 ProfileMapper
 *
 * Teaching points:
 * 1️⃣ Converts Profile entity ↔ ProfileDto
 * 2️⃣ Keeps service/controller code clean
 * 3️⃣ updateEntity() allows updating existing entity without creating a new one
 */
@Component
public class ProfileMapper {

    private final ModelMapper modelMapper;

    public ProfileMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ------------------- ENTITY → DTO -------------------
    public ProfileDto toDto(Profile profile) {
        return modelMapper.map(profile, ProfileDto.class);
    }

    // ------------------- DTO → ENTITY -------------------
    public Profile toEntity(ProfileDto dto) {
        return modelMapper.map(dto, Profile.class);
    }

    // ------------------- UPDATE EXISTING ENTITY -------------------
    public void updateEntity(ProfileDto dto, Profile profile) {
        // Only maps non-null fields from DTO to entity
        modelMapper.map(dto, profile);
    }
}
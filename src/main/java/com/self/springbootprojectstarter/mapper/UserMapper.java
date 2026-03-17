package com.self.springbootprojectstarter.mapper;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ------------------- ENTITY → DTO -------------------
    // Maps persistence entity to API DTO, including derived fields.
    // Exposes profile relationship as an id only (avoid serializing full Profile).
    public UserDto toDto(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);

        if (user.getProfile() != null) {
            dto.setProfileId(user.getProfile().getId());
        }

        return dto;
    }

    // ------------------- DTO → ENTITY -------------------
    // Maps API input into a new entity instance.
    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    // ------------------- UPDATE EXISTING ENTITY -------------------
    // Applies non-null DTO fields onto an existing entity instance.
    public void updateEntity(UserDto dto, User user) {
        modelMapper.map(dto, user);
    }
}

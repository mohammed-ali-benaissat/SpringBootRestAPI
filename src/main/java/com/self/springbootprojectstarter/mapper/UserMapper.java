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
    public UserDto toDto(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);

        if (user.getProfile() != null) {
            dto.setProfileId(user.getProfile().getId());
        }

        return dto;
    }

    // ------------------- DTO → ENTITY -------------------
    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    // ------------------- UPDATE EXISTING ENTITY -------------------
    public void updateEntity(UserDto dto, User user) {
        modelMapper.map(dto, user);
    }
}
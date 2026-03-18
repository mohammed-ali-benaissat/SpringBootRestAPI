package com.self.springbootprojectstarter.mapper;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * 🔥 UserMapper = converts between User Entity and UserDto
 *
 * Why do we need a Mapper?
 * 1. DTOs and Entities are different objects with different responsibilities.
 *    - Entity = database model
 *    - DTO = API model (safe for clients)
 * 2. Mapping manually for each field is tedious → ModelMapper handles most fields automatically
 * 3. Some fields need **custom logic** (like relationships → profileId)
 */
@Component
public class UserMapper {

    // ModelMapper = a library that automatically copies fields from one object to another
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ============================================================
    // 🔹 ENTITY → DTO
    // ============================================================
    /**
     * Converts a User entity to a UserDto
     * - Exposes profile relationship as profileId only (do NOT expose full Profile entity)
     * - Ensures API clients get only safe, flat data
     *
     * Example flow:
     * User (entity) → UserDto (API response)
     */
    public UserDto toDto(User user) {
        // 1. Map all simple fields automatically (id, name, email, age)
        UserDto dto = modelMapper.map(user, UserDto.class);

        // 2. Handle relationship manually: Profile → profileId
        if (user.getProfile() != null) {
            dto.setProfileId(user.getProfile().getId());
        }

        return dto;
    }

    // ============================================================
    // 🔹 DTO → ENTITY
    // ============================================================
    /**
     * Converts UserDto to a new User entity
     * - Useful for creating new users
     * - Relationships (like profile) are handled separately in the Service
     */
    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    // ============================================================
    // 🔹 UPDATE EXISTING ENTITY
    // ============================================================
    /**
     * Updates an existing User entity with values from UserDto
     *
     * Key points:
     * - Only non-null fields from DTO are applied (by default ModelMapper overwrites fields)
     * - Existing entity remains managed by JPA (important for update)
     * - Relationships (like profile) are usually handled separately in the Service layer
     */
    public void updateEntity(UserDto dto, User user) {
        modelMapper.map(dto, user);
    }
}
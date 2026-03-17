package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.exception.UserAlreadyExistsException;
import com.self.springbootprojectstarter.exception.UserNotFoundException;
import com.self.springbootprojectstarter.mapper.UserMapper;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService  = profileService;
        this.userMapper = userMapper;
    }

    // ------------------- GET ALL USERS (WITH PAGINATION) -------------------
    /**
     * Returns a paginated list of users mapped to DTO
     * @param pageable pagination info (page number, size, sorting)
     * @return Page<UserDto>
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        // Optional: validate sort fields to prevent invalid sort errors
        List<String> allowedSortFields = List.of("id", "name", "email", "age");
        pageable.getSort().forEach(order -> {
            if (!allowedSortFields.contains(order.getProperty())) {
                throw new IllegalArgumentException(
                        "Invalid sort property: " + order.getProperty()
                );
            }
        });

        // Fetch paginated users and map to DTO
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    // ------------------- GET USER BY ID -------------------
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    // ------------------- CREATE USER -------------------
    public UserDto addUser(UserDto userDto) {
        validateUserDtoForCreate(userDto);
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        User user = userMapper.toEntity(userDto);

        // Handle Profile relation here in service
        if (userDto.getProfileId() != null) {
            user.setProfile(profileService.getProfileEntityById(userDto.getProfileId()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    // ------------------- UPDATE USER -------------------
    public UserDto updateUser(Long id, UserDto userDto) {
        validateUserDtoForUpdate(id, userDto);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (userDto.getEmail() != null
                && !userDto.getEmail().equals(existingUser.getEmail())
                && userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        // Map basic fields
        userMapper.updateEntity(userDto, existingUser);

        // Handle Profile relation here (only update when provided)
        if (userDto.getProfileId() != null) {
            existingUser.setProfile(profileService.getProfileEntityById(userDto.getProfileId()));
        }

        User savedUser = userRepository.save(existingUser);
        return userMapper.toDto(savedUser);
    }

    // ------------------- DELETE USER -------------------
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    // ------------------- CHECK IF USER EXISTS -------------------
    public boolean checkIfUserExists(String name){
        return userRepository.existsByName(name);
    }

    private void validateUserDtoForCreate(UserDto userDto) {
        if (userDto.getId() != null) {
            throw new IllegalArgumentException("Id must be null when creating a user");
        }
        validateAge(userDto.getAge());
    }

    private void validateUserDtoForUpdate(Long id, UserDto userDto) {
        if (userDto.getId() != null && !userDto.getId().equals(id)) {
            throw new IllegalArgumentException("Path id does not match body id");
        }
        validateAge(userDto.getAge());
    }

    private void validateAge(Integer age) {
        if (age == null || age <= 0) {
            throw new IllegalArgumentException("Age must be 1 or higher");
        }
    }
}

package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.exception.UserNotFoundException;
import com.self.springbootprojectstarter.mapper.UserMapper;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // ------------------- GET ALL USERS (WITH PAGINATION) -------------------
    /**
     * Returns a paginated list of users mapped to DTO
     * @param pageable pagination info (page number, size, sorting)
     * @return Page<UserDto>
     */
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
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    // ------------------- CREATE USER -------------------
    public UserDto addUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    // ------------------- UPDATE USER -------------------
    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Map updated fields from DTO to entity
        userMapper.updateEntity(userDto, existingUser);

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
}
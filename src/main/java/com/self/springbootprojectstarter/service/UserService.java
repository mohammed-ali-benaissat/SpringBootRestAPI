package com.self.springbootprojectstarter.service;

// DTO (what we send to client)
import com.self.springbootprojectstarter.DTO.UserDto;

// Custom exceptions (VERY IMPORTANT for clean error handling)
import com.self.springbootprojectstarter.exception.UserAlreadyExistsException;
import com.self.springbootprojectstarter.exception.UserNotFoundException;

// Mapper → converts between Entity and DTO
import com.self.springbootprojectstarter.mapper.UserMapper;

// Entity (represents database table)
import com.self.springbootprojectstarter.model.User;

// Repository (handles DB operations)
import com.self.springbootprojectstarter.repository.UserRepository;

// Lombok annotation → creates a logger (log.info, log.error, etc.)
import lombok.extern.slf4j.Slf4j;

// Pagination
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Marks this class as a Service (Spring manages it as a Bean)
import org.springframework.stereotype.Service;

// Used for transaction management (VERY IMPORTANT)
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// Tells Spring: "This is a Service class"
@Service

// Lombok → automatically creates a logger
@Slf4j
public class UserService {

    // Repository to access database
    private final UserRepository userRepository;

    // Another service → shows how services can collaborate
    private final ProfileService profileService;

    // Mapper → converts Entity ↔ DTO
    private final UserMapper userMapper;

    // Constructor Injection (BEST PRACTICE)
    public UserService(UserRepository userRepository, UserMapper userMapper, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService  = profileService;
        this.userMapper = userMapper;
    }

    // ============================================================
    // ✅ GET ALL USERS (WITH PAGINATION)
    // ============================================================

    /**
     * 🔥 IMPORTANT CONCEPT: @Transactional(readOnly = true)
     *
     * Transaction = a database operation session
     *
     * readOnly = true means:
     * - We are ONLY reading data (no insert/update/delete)
     * - Faster performance
     * - Prevents accidental modifications
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {

        /**
         * 🔥 WHY VALIDATE SORT FIELDS?
         *
         * If client sends:
         * /users?sort=unknownField
         *
         * → Spring will crash ❌
         *
         * So we protect our API by allowing only specific fields.
         */
        List<String> allowedSortFields = List.of("id", "name", "email", "age");

        pageable.getSort().forEach(order -> {
            if (!allowedSortFields.contains(order.getProperty())) {
                throw new IllegalArgumentException(
                        "Invalid sort property: " + order.getProperty()
                );
            }
        });

        /**
         * 🔥 KEY POINT:
         * Repository returns Page<User> (Entity)
         *
         * BUT we must return DTO → so we map it
         *
         * .map(userMapper::toDto) = convert each User → UserDto
         */
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    // ============================================================
    // ✅ GET USER BY ID
    // ============================================================

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {

        /**
         * 🔥 IMPORTANT:
         * findById returns Optional<User>
         *
         * If user not found → throw exception
         */
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Convert Entity → DTO
        return userMapper.toDto(user);
    }

    // ============================================================
    // ✅ CREATE USER
    // ============================================================

    public UserDto addUser(UserDto userDto) {

        /**
         * Step 1: Validate input
         */
        validateUserDtoForCreate(userDto);

        /**
         * Step 2: Business rule
         * Email must be unique
         */
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        // 🔹 Log creation attempt
        log.info("Creating user with email: {}", userDto.getEmail());


        /**
         * Step 3: Convert DTO → Entity
         */
        User user = userMapper.toEntity(userDto);

        /**
         * Step 4: Handle relationships
         *
         * VERY IMPORTANT CONCEPT:
         * DTO only contains profileId
         * BUT Entity needs full Profile object
         */
        if (userDto.getProfileId() != null) {
            user.setProfile(
                    profileService.getProfileEntityById(userDto.getProfileId())
            );
        }

        /**
         * Step 5: Save to database
         */
        User savedUser = userRepository.save(user);

        /**
         * Step 6: Convert back Entity → DTO
         */
        return userMapper.toDto(savedUser);
    }

    // ============================================================
    // ✅ UPDATE USER
    // ============================================================

    public UserDto updateUser(Long id, UserDto userDto) {

        /**
         * Step 1: Validate input
         */
        validateUserDtoForUpdate(id, userDto);

        /**
         * Step 2: Get existing user
         */
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        log.info("Updating user with id: {} and email: {}", id, userDto.getEmail());

        /**
         * Step 3: Check email uniqueness (only if changed)
         */
        if (userDto.getEmail() != null
                && !userDto.getEmail().equals(existingUser.getEmail())
                && userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        /**
         * 🔥 VERY IMPORTANT:
         * Instead of creating new object,
         * we UPDATE existing one
         *
         * Why?
         * → keeps database consistency
         */
        userMapper.updateEntity(userDto, existingUser);

        /**
         * Step 4: Update relationship if provided
         */
        if (userDto.getProfileId() != null) {
            existingUser.setProfile(
                    profileService.getProfileEntityById(userDto.getProfileId())
            );
        }

        /**
         * Step 5: Save updated entity
         */
        User savedUser = userRepository.save(existingUser);

        return userMapper.toDto(savedUser);
    }

    // ============================================================
    // ✅ DELETE USER
    // ============================================================

    public void deleteUser(Long id) {

        /**
         * WHY NOT just deleteById(id)?
         *
         * Because if user doesn't exist:
         * → deleteById does NOTHING (silent failure ❌)
         *
         * So we force check first
         */
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        log.info("Deleting user with id: {}", id);

        userRepository.delete(user);
    }

    // ============================================================
    // ✅ CHECK IF USER EXISTS
    // ============================================================

    public boolean checkIfUserExists(String name){
        return userRepository.existsByName(name);
    }

    // ============================================================
    // 🔒 VALIDATION METHODS (VERY CLEAN DESIGN)
    // ============================================================

    /**
     * 🔥 WHY VALIDATION IN SERVICE?
     *
     * Controller validation = basic (format, null, etc.)
     * Service validation = BUSINESS RULES
     */
    private void validateUserDtoForCreate(UserDto userDto) {

        // Prevent client from setting ID manually
        if (userDto.getId() != null) {
            throw new IllegalArgumentException("Id must be null when creating a user");
        }

        validateAge(userDto.getAge());
    }

    private void validateUserDtoForUpdate(Long id, UserDto userDto) {

        // Prevent mismatch between URL id and body id
        if (userDto.getId() != null && !userDto.getId().equals(id)) {
            throw new IllegalArgumentException("Path id does not match body id");
        }

        validateAge(userDto.getAge());
    }

    /**
     * Shared validation logic
     */
    private void validateAge(Integer age) {
        if (age == null || age <= 0) {
            throw new IllegalArgumentException("Age must be 1 or higher");
        }
    }
}
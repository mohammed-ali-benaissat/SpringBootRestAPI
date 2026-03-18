package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.exception.UserAlreadyExistsException;
import com.self.springbootprojectstarter.mapper.UserMapper;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * 🔹 UserServiceTest
 *
 * Teaching Points:
 *
 * 1️⃣ @ExtendWith(MockitoExtension.class)
 *    - Enables Mockito support in JUnit 5
 *    - Handles initialization of mocks and injecting them into tested class
 *
 * 2️⃣ @Mock
 *    - Creates mock instances of dependencies (UserRepository, UserMapper)
 *    - We control what these mocks return → isolates the service logic
 *
 * 3️⃣ @InjectMocks
 *    - Injects all @Mock dependencies into the UserService instance
 *    - Simulates real wiring without Spring context
 *
 * 4️⃣ Testing Strategy
 *    - Unit test focuses on **business logic**, not the database
 *    - We “mock away” external dependencies
 *    - Tests include both **happy paths** and **error scenarios**
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Mocked repository

    @Mock
    private UserMapper userMapper; // Mocked mapper

    @InjectMocks
    private UserService userService; // Service under test, dependencies injected

    // ------------------- TEST INVALID SORT -------------------
    @Test
    void getAllUsers_invalidSort_throwsIllegalArgumentException() {
        // Arrange: create a PageRequest with an invalid sort field
        // Act + Assert: service should throw before calling repository
        assertThatThrownBy(() -> userService.getAllUsers(
                PageRequest.of(0, 10, Sort.by("invalidField"))
        )).isInstanceOf(IllegalArgumentException.class);

        // Verify: repository is never called if input validation fails
        verifyNoInteractions(userRepository);
    }

    // ------------------- TEST DUPLICATE EMAIL -------------------
    @Test
    void addUser_duplicateEmail_throwsUserAlreadyExistsException() {
        // Arrange: create a UserDto and mock repository to report email exists
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        // Act + Assert: service should throw UserAlreadyExistsException
        assertThatThrownBy(() -> userService.addUser(dto))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    // ------------------- TEST SUCCESSFUL USER CREATION -------------------
    @Test
    void addUser_savesAndReturnsDto() {
        // Arrange: create DTO and mock conversions & save behavior
        UserDto dto = new UserDto("Bob", 25, "bob@example.com");
        User entity = new User();      // DTO → Entity
        User saved = new User();       // Saved entity returned by repository
        UserDto savedDto = new UserDto("Bob", 25, "bob@example.com"); // Entity → DTO

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(saved);
        when(userMapper.toDto(saved)).thenReturn(savedDto);

        // Act: call service
        UserDto result = userService.addUser(dto);

        // Assert: result matches expected mapped DTO
        assertThat(result).isEqualTo(savedDto);
        // Verify: repository.save was called with the entity
        verify(userRepository).save(entity);
    }

    // ------------------- TEST CREATE WITH CLIENT-SUPPLIED ID -------------------
    @Test
    void addUser_withId_throwsIllegalArgumentException() {
        // Arrange: DTO with ID should be rejected
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        dto.setId(99L);

        // Act + Assert
        assertThatThrownBy(() -> userService.addUser(dto))
                .isInstanceOf(IllegalArgumentException.class);

        // Repository should not be called
        verifyNoInteractions(userRepository);
    }

    // ------------------- TEST CREATE WITH INVALID AGE -------------------
    @Test
    void addUser_ageZero_throwsIllegalArgumentException() {
        // Arrange: DTO with age=0
        UserDto dto = new UserDto("Alice", 0, "alice@example.com");

        // Act + Assert
        assertThatThrownBy(() -> userService.addUser(dto))
                .isInstanceOf(IllegalArgumentException.class);

        // Repository should not be called
        verifyNoInteractions(userRepository);
    }

    // ------------------- TEST UPDATE WITH MISMATCHED ID -------------------
    @Test
    void updateUser_mismatchedId_throwsIllegalArgumentException() {
        // Arrange: DTO ID does not match path ID
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        dto.setId(99L);

        // Act + Assert
        assertThatThrownBy(() -> userService.updateUser(1L, dto))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(userRepository);
    }

    // ------------------- TEST UPDATE WITH INVALID AGE -------------------
    @Test
    void updateUser_ageZero_throwsIllegalArgumentException() {
        // Arrange: age=0
        UserDto dto = new UserDto("Alice", 0, "alice@example.com");

        // Act + Assert
        assertThatThrownBy(() -> userService.updateUser(1L, dto))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(userRepository);
    }
}
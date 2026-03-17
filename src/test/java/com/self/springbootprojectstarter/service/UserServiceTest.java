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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_invalidSort_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> userService.getAllUsers(
                PageRequest.of(0, 10, Sort.by("invalidField"))
        )).isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(userRepository);
    }

    @Test
    void addUser_duplicateEmail_throwsUserAlreadyExistsException() {
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.addUser(dto))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    @Test
    void addUser_savesAndReturnsDto() {
        UserDto dto = new UserDto("Bob", 25, "bob@example.com");
        User entity = new User();
        User saved = new User();
        UserDto savedDto = new UserDto("Bob", 25, "bob@example.com");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(saved);
        when(userMapper.toDto(saved)).thenReturn(savedDto);

        UserDto result = userService.addUser(dto);

        assertThat(result).isEqualTo(savedDto);
        verify(userRepository).save(entity);
    }

    @Test
    void addUser_withId_throwsIllegalArgumentException() {
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        dto.setId(99L);

        assertThatThrownBy(() -> userService.addUser(dto))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(userRepository);
    }

    @Test
    void addUser_ageZero_throwsIllegalArgumentException() {
        UserDto dto = new UserDto("Alice", 0, "alice@example.com");

        assertThatThrownBy(() -> userService.addUser(dto))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(userRepository);
    }

    @Test
    void updateUser_mismatchedId_throwsIllegalArgumentException() {
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        dto.setId(99L);

        assertThatThrownBy(() -> userService.updateUser(1L, dto))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(userRepository);
    }

    @Test
    void updateUser_ageZero_throwsIllegalArgumentException() {
        UserDto dto = new UserDto("Alice", 0, "alice@example.com");

        assertThatThrownBy(() -> userService.updateUser(1L, dto))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(userRepository);
    }
}

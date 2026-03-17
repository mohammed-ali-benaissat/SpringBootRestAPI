package com.self.springbootprojectstarter.controller;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.config.SecurityConfig;
import com.self.springbootprojectstarter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    void getAllUsers_returnsPage() throws Exception {
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        dto.setId(1L);
        Page<UserDto> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);

        when(userService.getAllUsers(any())).thenReturn(page);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[0].age").value(30))
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"));
    }
}

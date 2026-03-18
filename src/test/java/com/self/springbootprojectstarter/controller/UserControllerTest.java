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

/**
 * 🔹 UserControllerTest
 *
 * Teaching Points:
 * 1️⃣ Unit testing controllers in Spring Boot is usually done with @WebMvcTest.
 *    - Loads only the web layer (Controller, Jackson, Validation, etc.)
 *    - Does NOT load full application context → faster tests
 *
 * 2️⃣ @MockBean
 *    - Creates a Mockito mock for a dependency (here, UserService)
 *    - Injected into the controller automatically
 *    - Allows us to control service behavior for predictable tests
 *
 * 3️⃣ MockMvc
 *    - Simulates HTTP requests to controllers without starting a real server
 *    - Lets us assert status codes, response JSON, headers, etc.
 *
 * 4️⃣ @WithMockUser
 *    - Adds a dummy authenticated user for security-protected endpoints
 *    - Avoids having to set up real authentication for tests
 */
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class) // Include security config because controller is protected
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used to simulate HTTP calls

    @MockBean
    private UserService userService; // Mocked service to isolate controller logic

    @Test
    @WithMockUser // Simulate a logged-in user
    void getAllUsers_returnsPage() throws Exception {
        // ------------------- ARRANGE -------------------
        // Step 1: Create a dummy UserDto object to return from the service
        UserDto dto = new UserDto("Alice", 30, "alice@example.com");
        dto.setId(1L);

        // Step 2: Wrap it in a paginated Page object
        // PageImpl: concrete implementation of Spring Data Page
        Page<UserDto> page = new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1);

        // Step 3: Configure the mocked service to return this page
        // `any()` means "for any Pageable argument passed"
        when(userService.getAllUsers(any())).thenReturn(page);

        // ------------------- ACT + ASSERT -------------------
        // Step 4: Perform a GET request to /users
        mockMvc.perform(get("/users"))
                // Expect HTTP 200 OK
                .andExpect(status().isOk())
                // Expect the JSON content to match the dummy UserDto
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[0].age").value(30))
                .andExpect(jsonPath("$.content[0].email").value("alice@example.com"));

        // ✅ Teaching Note:
        // - Arrange: prepare data & mock behavior
        // - Act: perform the action (HTTP request)
        // - Assert: check results (status, JSON, etc.)
        // This is the standard **AAA testing pattern**.
    }
}
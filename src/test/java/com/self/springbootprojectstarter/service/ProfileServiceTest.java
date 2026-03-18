package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.mapper.ProfileMapper;
import com.self.springbootprojectstarter.model.Profile;
import com.self.springbootprojectstarter.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 🔹 ProfileServiceTest
 *
 * Teaching Notes:
 *
 * 1️⃣ @ExtendWith(MockitoExtension.class)
 *    - Activates Mockito in JUnit 5
 *    - Automatically initializes @Mock and @InjectMocks
 *
 * 2️⃣ @Mock
 *    - Creates fake instances of dependencies (ProfileRepository, ProfileMapper)
 *    - We can control their behavior → isolates the service
 *
 * 3️⃣ @InjectMocks
 *    - Injects the mocks into the service under test (ProfileService)
 *    - Simulates dependency injection without starting Spring context
 *
 * 4️⃣ Testing Strategy:
 *    - Unit test focuses on **service logic** only
 *    - Repository and mapper are **mocked**
 *    - Tests ensure the service handles conversion & persistence correctly
 */
@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository; // fake repository

    @Mock
    private ProfileMapper profileMapper; // fake mapper

    @InjectMocks
    private ProfileService profileService; // service under test

    // ------------------- TEST: ADD PROFILE -------------------
    @Test
    void addProfile_returnsSavedDto() {
        // ----------------- Arrange -----------------
        // DTO coming from API
        ProfileDto dto = new ProfileDto("bio");

        // Entity produced by mapper
        Profile entity = new Profile();

        // Entity after repository saves it (with ID assigned by DB)
        Profile saved = new Profile();
        saved.setId(1L); // simulate DB-generated id
        saved.setBio("bio");

        // DTO returned to client after saving
        ProfileDto savedDto = new ProfileDto("bio");
        savedDto.setId(1L);

        // Mock mapper: converts DTO → Entity
        when(profileMapper.toEntity(dto)).thenReturn(entity);

        // Mock repository: saves entity and returns "saved" entity
        when(profileRepository.save(entity)).thenReturn(saved);

        // Mock mapper: converts saved entity → DTO
        when(profileMapper.toDto(saved)).thenReturn(savedDto);

        // ----------------- Act -----------------
        // Call service method
        ProfileDto result = profileService.addProfile(dto);

        // ----------------- Assert -----------------
        // Check that returned DTO matches expected saved DTO
        assertThat(result).isEqualTo(savedDto);

        // Verify that repository.save() was called with correct entity
        verify(profileRepository).save(entity);
    }
}
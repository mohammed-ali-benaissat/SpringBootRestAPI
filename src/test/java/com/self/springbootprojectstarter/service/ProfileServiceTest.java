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

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void addProfile_returnsSavedDto() {
        // Arrange: mapper converts DTO to entity, repository returns saved entity.
        ProfileDto dto = new ProfileDto("bio");
        Profile entity = new Profile();
        Profile saved = new Profile();
        saved.setId(1L);
        saved.setBio("bio");
        ProfileDto savedDto = new ProfileDto("bio");
        savedDto.setId(1L);

        when(profileMapper.toEntity(dto)).thenReturn(entity);
        when(profileRepository.save(entity)).thenReturn(saved);
        when(profileMapper.toDto(saved)).thenReturn(savedDto);

        // Act: create profile.
        ProfileDto result = profileService.addProfile(dto);

        // Assert: returned DTO reflects the saved entity (including id).
        assertThat(result).isEqualTo(savedDto);
        verify(profileRepository).save(entity);
    }
}

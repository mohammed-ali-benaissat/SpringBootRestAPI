package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.exception.ProfileNotFoundException;
import com.self.springbootprojectstarter.exception.UserAlreadyExistsException;
import com.self.springbootprojectstarter.exception.UserNotFoundException;
import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.model.Profile;
import com.self.springbootprojectstarter.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public UserService(
            UserRepository userRepository,
            ProfileRepository profileRepository,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserDto dto = modelMapper.map(user, UserDto.class);
                    dto.setProfileId(user.getProfile() != null ? user.getProfile().getId() : null);
                    return dto;
                })
                .toList();
    }


    public void addUsers(UserDto userDto) {
        log.info("Adding new user: {}", userDto.getName());
        if (userRepository.existsByNameAndEmailAndAge(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge()
        )) {
            log.warn("User already exists: {}", userDto.getName());
            throw new UserAlreadyExistsException(userDto.getName());
        }


        User user = modelMapper.map(userDto, User.class);
        if (userDto.getProfileId() != null) {
            Profile profile = profileRepository.findById(userDto.getProfileId())
                    .orElseThrow(() -> new ProfileNotFoundException(userDto.getProfileId()));
            user.setProfile(profile);
        }
        userRepository.save(user);
        log.info("User added successfully: {}", userDto.getName());
    }


    public void updateUsers(Long index, UserDto updatedUserDto) {
        User user = userRepository.findById(index)
                .orElseThrow(() -> new UserNotFoundException(index));

        if (userRepository.existsByNameAndEmailAndAge(
                updatedUserDto.getName(),
                updatedUserDto.getEmail(),
                updatedUserDto.getAge()
        ) && !user.getId().equals(index)) {  // exclude current user
            throw new UserAlreadyExistsException(updatedUserDto.getName());
        }

        modelMapper.map(updatedUserDto, user);
        if (updatedUserDto.getProfileId() != null) {
            Profile profile = profileRepository.findById(updatedUserDto.getProfileId())
                    .orElseThrow(() -> new ProfileNotFoundException(updatedUserDto.getProfileId()));
            user.setProfile(profile);
        } else {
            user.setProfile(null);
        }

        userRepository.save(user);
    }

    public void deleteUsers(Long index) {
        User user = userRepository.findById(index)
                .orElseThrow(() -> new UserNotFoundException(index));

        userRepository.delete(user);
    }

    public boolean checkIfUserExists(String name){
        return userRepository.existsByName(name);
    }
}

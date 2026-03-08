package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.exception.UserAlreadyExistsException;
import com.self.springbootprojectstarter.exception.UserNotFoundException;
import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    public UserService(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            ProfileRepository profileRepository,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.profileRepository = profileRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }


    public void addUsers(UserDto userDto) {
        if (userRepository.existsByNameAndEmailAndAge(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge()
        )) {
            throw new UserAlreadyExistsException(userDto.getName());
        }


        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
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

//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final ModelMapper modelMapper = new ModelMapper();
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    public List<UserDto> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        List<UserDto> userDtos = new ArrayList<>();
//        for (User user : users) {
//           userDtos.add(modelMapper.map(user, UserDto.class));
//        }
//        return userDtos;
//    }

//    public UserDto getUsers(int index){
//        if(index < 0 || index > users.size()){
//            throw new UserNotFoundException(index);
//        }
//        return users.get(index);
//    }

//    public void addUsers(UserDto userDto) {
//        if (userDto.getName() == null || userDto.getName().equals("") || userDto.getAge() < 0 || userDto.getAge() == 0) {
//            throw new UserNotValidException();
//        }
//        User user = modelMapper.map(userDto, User.class);
//        userRepository.save(user);
//    }
//
//    public void updateUsers(Long index, UserDto updatedUserDto) {
//        User user = userRepository.findById(index)
//                .orElseThrow(() -> new UserNotFoundException(index));
//
//        modelMapper.map(updatedUserDto, user);
//
//        userRepository.save(user);
//    }
//
//    public void deleteUsers(Long index) {
//        if (userRepository.existsById(index)) {
//            userRepository.deleteById(index);
//        } else {
//            throw new UserNotFoundException(index);
//        }
//    }
//
//    public boolean checkIfUserExists(String name){
//        return userRepository.existsByName(name);
//    }
//
//}


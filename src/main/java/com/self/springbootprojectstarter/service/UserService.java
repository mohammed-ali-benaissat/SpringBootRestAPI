package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.exception.UserNotFoundException;
import com.self.springbootprojectstarter.exception.UserNotValidException;
import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public UserDto getUsers(int index){
//        if(index < 0 || index > users.size()){
//            throw new UserNotFoundException(index);
//        }
//        return users.get(index);
//    }

    public void addUsers(User user) {
        if (user.getName() == null || user.getName().equals("") || user.getAge() < 0 || user.getAge() == 0) {
            throw new UserNotValidException();
        }
        userRepository.save(user);
    }

    public void updateUsers(Long index, User updatedUsers) {
        if (userRepository.existsById(index)) {
            userRepository.save(updatedUsers);
        } else {
            throw new UserNotFoundException(index);
        }
    }

    public void deleteUsers(Long index) {
        if (userRepository.existsById(index)) {
            userRepository.deleteById(index);
        } else {
            throw new UserNotFoundException(index);
        }
    }

    public boolean checkIfUserExists(String name){
        return userRepository.existsByName(name);
    }

}


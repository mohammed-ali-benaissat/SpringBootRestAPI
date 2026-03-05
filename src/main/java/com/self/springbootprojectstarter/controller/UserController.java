package com.self.springbootprojectstarter.controller;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import com.self.springbootprojectstarter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


//The rest controller is like the waiter in a restaurant
// it is responsible for linking requests from to service layer
// it is important to link service to controller,
// and it is done via passing the service as a parameter in the controller constructor
// this is called D.INJECTION  OR constructor injection
// spring boot automatically injects service in the controller bcz
// service has @service and controller has @restcontroller spring treat them as beans*

//how is that

//Instead of the controller creating the service itself like this:
//UserService userService = new UserService(); ❌
//Spring injects (gives) the service to the controller automatically.
//That is Dependency Injection.

@RestController

//RequestMapping is an annotation that allow u to specify the mutual url
// and mention it once for all @PGPD
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllPeople(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //to recognize the id in the url for specific person it must be annotated with @pathVariable
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
//        User user = userService.getUsers(id);
//        return ResponseEntity.ok(user);
//    }

    //post and update requests require a body for this use @requestBody
    @PostMapping
    public ResponseEntity<User> addPerson(@RequestBody User person){
        userService.addUsers(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable Long id, @RequestBody User updatedUser){
        userService.updateUsers(id, updatedUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        userService.deleteUsers(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> checkIfUserExists(@PathVariable String name){
        return ResponseEntity.ok(userService.checkIfUserExists(name));

    }
}

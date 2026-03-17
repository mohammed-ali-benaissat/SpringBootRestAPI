package com.self.springbootprojectstarter.exception;

public class UserNotValidException extends RuntimeException{
    // Thrown when user input fails custom validation rules.
    public UserNotValidException(){
        super("person can not be created!");
    }

}

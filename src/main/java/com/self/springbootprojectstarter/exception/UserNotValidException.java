package com.self.springbootprojectstarter.exception;

public class UserNotValidException extends RuntimeException{
    public UserNotValidException(){
        super("person can not be created!");
    }

}

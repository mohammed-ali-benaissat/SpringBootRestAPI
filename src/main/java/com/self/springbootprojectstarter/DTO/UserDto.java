package com.self.springbootprojectstarter.DTO;

public class UserDto {
    private String name;
    private String email;
    private int age;

    public UserDto(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    //Getters

    public String getName(){
        return name;
    }

    public int getAge() {return age;}

    public String getEmail() {return email;}
}

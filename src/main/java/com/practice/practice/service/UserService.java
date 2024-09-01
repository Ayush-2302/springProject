package com.practice.practice.service;

import com.practice.practice.payloads.UserDto;

public interface UserService {

    public UserDto createUser(UserDto userDto);

    public UserDto getUserByEmail(String email);

}

package com.example.restws.service;

import com.example.restws.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateuser(UserDto userDto);

    UserDto getUser (String email);

    UserDto getUserDetails(String userId);
}

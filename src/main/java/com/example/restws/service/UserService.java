package com.example.restws.service;

import com.example.restws.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto getUser (String email);

    UserDto getUserDetails(Long id);
}

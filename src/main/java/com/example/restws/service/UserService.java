package com.example.restws.service;

import com.example.restws.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUser (String email);
}

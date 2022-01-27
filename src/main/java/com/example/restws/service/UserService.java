package com.example.restws.service;

import com.example.restws.dto.AddressDto;
import com.example.restws.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto getUser (String email);

    UserDto getUserDetails(Long id);

    List<UserDto> getAllUsers(int page, int limit);
}

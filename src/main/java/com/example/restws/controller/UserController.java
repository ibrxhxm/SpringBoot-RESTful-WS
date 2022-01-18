package com.example.restws.controller;

import com.example.restws.dto.UserDto;
import com.example.restws.request.EditUserRequest;
import com.example.restws.request.UserRequest;
import com.example.restws.response.UserResponse;
import com.example.restws.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserDto userDto = modelMapper.map(userRequest, UserDto.class);
        userDto = userService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(modelMapper.map(userDto, UserResponse.class));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @RequestBody @Valid EditUserRequest userRequest) {
        UserDto userDto = modelMapper.map(userRequest, UserDto.class);
        userDto.setId(id);
        userDto = userService.updateUser(userDto);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(modelMapper.map(userDto, UserResponse.class));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {
        UserResponse userResponse = modelMapper.map(userService.getUserDetails(id), UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(userResponse);
    }
}

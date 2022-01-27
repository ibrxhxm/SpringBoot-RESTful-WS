package com.example.restws.controller;

import com.example.restws.dto.UserDto;
import com.example.restws.request.EditUserRequest;
import com.example.restws.request.UserRequest;
import com.example.restws.response.AddressResponse;
import com.example.restws.response.UserResponse;
import com.example.restws.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "limit", defaultValue = "10") int limit) {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(userService.getAllUsers(page, limit).stream().map(x -> modelMapper.map(x, UserResponse.class)).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{user_id}/address", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressResponse>> getAllAddresses(@PathVariable("user_id") Long userId) {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(userService.getAllAddresses(userId).stream().map(x -> modelMapper.map(x, AddressResponse.class)).collect(Collectors.toList()));
    }
}

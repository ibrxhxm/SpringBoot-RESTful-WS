package com.example.restws.service.impl;

import com.example.restws.dto.UserDto;
import com.example.restws.entity.UserEntity;
import com.example.restws.repository.UserRepository;
import com.example.restws.service.UserService;
import com.example.restws.util.UsernameGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UsernameGenerator usernameGenerator;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UsernameGenerator usernameGenerator) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.usernameGenerator = usernameGenerator;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<UserEntity> optUser = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if(optUser.isPresent()) {
            throw new RuntimeException();
        }

        userDto.setEncryptedPassword("test");
        userDto.setUserId(usernameGenerator.generateUserId(15));

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }
}

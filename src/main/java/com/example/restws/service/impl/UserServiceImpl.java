package com.example.restws.service.impl;

import com.example.restws.dto.UserDto;
import com.example.restws.entity.UserEntity;
import com.example.restws.repository.UserRepository;
import com.example.restws.service.UserService;
import com.example.restws.util.UsernameGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UsernameGenerator usernameGenerator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UsernameGenerator usernameGenerator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.usernameGenerator = usernameGenerator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<UserEntity> optUser = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if(optUser.isPresent()) {
            throw new RuntimeException();
        }

        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setUserId(usernameGenerator.generateUserId(15));

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        Optional<UserEntity> optUserEntity = Optional.ofNullable(userRepository.findByEmail(email));
        UserEntity userEntity = optUserEntity.orElseThrow(() -> new UsernameNotFoundException("invalid user credentials"));

        return modelMapper.map(userEntity, UserDto.class)
    }
}

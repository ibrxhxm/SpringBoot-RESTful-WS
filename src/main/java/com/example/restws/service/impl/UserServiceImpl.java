package com.example.restws.service.impl;

import com.example.restws.dto.UserDto;
import com.example.restws.entity.UserEntity;
import com.example.restws.exception.ErrorMessage;
import com.example.restws.exception.UserAlreadyExistsException;
import com.example.restws.exception.UserNotFoundException;
import com.example.restws.repository.UserRepository;
import com.example.restws.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<UserEntity> optUser = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if(optUser.isPresent()) {
            throw new UserAlreadyExistsException(ErrorMessage.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        log.info(userDto.getAddresses().get(0).getCity());

        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        log.info(userEntity.getAddresses().get(0).getCountry());
        userEntity = userRepository.save(userEntity);

        log.info(userEntity.getAddresses().get(0).getCountry());

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserDto oldUserDto = abortIfUserDoesNotExist(userDto.getId());
        modelMapper.map(userDto, oldUserDto);

        return modelMapper.map(userRepository.save(modelMapper.map(oldUserDto, UserEntity.class)), UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        Optional<UserEntity> optUserEntity = Optional.ofNullable(userRepository.findByEmail(email));
        UserEntity userEntity = optUserEntity.orElseThrow(() -> new UsernameNotFoundException("invalid user credentials"));

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserDetails(Long id) {
        Optional<UserEntity> optUserEntity = userRepository.findById(id);
        UserEntity userEntity = optUserEntity.orElseThrow(() -> new UserNotFoundException(ErrorMessage.NO_RECORD_FOUND.getErrorMessage()));

        return modelMapper.map(userEntity, UserDto.class);
    }

    private UserDto abortIfUserDoesNotExist(Long id) {
        Optional<UserEntity> optUserEntity = userRepository.findById(id);

        UserEntity userEntity = optUserEntity.orElseThrow(() -> new UserAlreadyExistsException(ErrorMessage.RECORD_DOES_NOT_EXIST.getErrorMessage()));
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);

        return userRepository.findAll(pageable).getContent().stream().map(x -> modelMapper.map(x, UserDto.class)).collect(Collectors.toList());
    }
}

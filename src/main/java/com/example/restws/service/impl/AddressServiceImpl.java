package com.example.restws.service.impl;

import com.example.restws.dto.AddressDto;
import com.example.restws.entity.AddressEntity;
import com.example.restws.repository.AddressRepository;
import com.example.restws.service.AddressService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<AddressDto> getAllAddresses(Long userId) {
        Type listType = new TypeToken<List<AddressDto>>() {}.getType();
        List<AddressEntity> addressEntities = addressRepository.findAllByUserId(userId);
        return modelMapper.map(addressEntities, listType);
    }
}

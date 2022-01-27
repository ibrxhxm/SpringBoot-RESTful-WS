package com.example.restws.service;

import com.example.restws.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAllAddresses(Long userId);
}

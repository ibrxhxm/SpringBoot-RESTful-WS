package com.example.restws.entity;

import com.example.restws.dto.UserDto;

import javax.persistence.*;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30, nullable = false)
    private String street;

    @Column(length = 30, nullable = false)
    private String city;

    @Column(length = 30, nullable = false)
    private String country;

    @Column(length = 30, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDto userDto;
}

package com.example.restws.entity;

import com.example.restws.dto.UserDto;

import javax.persistence.*;

@Entity
public class AddressEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 100, nullable = false)
    private String street;

    @Column(length = 30, nullable = false)
    private String city;

    @Column(length = 30, nullable = false)
    private String country;

    @Column(length = 30, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUserDto(UserEntity user) {
        this.user = user;
    }
}

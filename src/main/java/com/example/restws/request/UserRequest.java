package com.example.restws.request;

import javax.validation.constraints.*;
import java.util.List;

public class UserRequest {

    @NotBlank(message = "{required.field}")
    @NotNull(message = "{notnull.field}")
    private String firstName;

    @NotBlank(message = "{required.field}")
    @NotNull(message = "{notnull.field}")
    private String lastName;

    @NotBlank(message = "{required.field}")
    @NotNull(message = "{notnull.field}")
    @Email
    private String email;

    @NotBlank(message = "{required.field}")
    @NotNull(message = "{notnull.field}")
    @Size(min = 1, max = 7, message = "{invalid.field}")
    private String password;

    @NotEmpty(message = "{atleast-one.field}")
    private List<AddressRequest> addresses;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AddressRequest> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequest> addresses) {
        this.addresses = addresses;
    }
}

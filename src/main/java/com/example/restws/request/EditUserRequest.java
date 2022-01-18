package com.example.restws.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditUserRequest {
    @NotBlank(message = "{required.field}")
    @NotNull(message = "{notnull.field}")
    private String firstName;

    @NotBlank(message = "{required.field}")
    @NotNull(message = "{notnull.field}")
    private String lastName;

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
}

package com.mks.membershipservice.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateMemberRequest {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email min size 2")
    @Email
    private String username;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name min size 2")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password min size 8")
    private String password;
}

package com.mks.membershipservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email min size 2")
    @Email
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password min size 8")
    private String password;
}

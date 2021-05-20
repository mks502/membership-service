package com.mks.membershipservice.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestLogout {
    @NotNull(message = "token cannot be null")
    private String token;
}

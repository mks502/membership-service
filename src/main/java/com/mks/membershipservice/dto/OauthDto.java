package com.mks.membershipservice.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OauthDto {
    private String authorizationCode;

    public OauthDto(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

}

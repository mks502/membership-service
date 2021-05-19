package com.mks.membershipservice.vo;

import lombok.Data;

@Data
public class ResponseLogin {
    private final Long memberId;
    private final String token;

    public ResponseLogin(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}

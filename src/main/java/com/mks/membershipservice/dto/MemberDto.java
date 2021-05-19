package com.mks.membershipservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
    private Long memberId;
    private String username;
    private String name;
    private String password;
    private String encryptedPassword;
}

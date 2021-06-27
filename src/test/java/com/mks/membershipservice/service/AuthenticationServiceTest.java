package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.dto.RequestLogin;
import com.mks.membershipservice.dto.ResponseLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AuthenticationServiceTest {
    private static MemberDto memberDto;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup(){
        memberDto = new MemberDto();
        memberDto.setUsername("hkd@gmail.com");
        memberDto.setName("홍길동");
        memberDto.setPassword("12345678");
        memberDto = memberService.createMember(memberDto);
    }

    @Test
    @DisplayName("로그인을 하면 인증에 필요한 토큰과 memberId를 반환해준다")
    void login() {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setUsername("hkd@gmail.com");
        requestLogin.setPassword("12345678");

        ResponseLogin response = authenticationService.login(requestLogin.getUsername(),requestLogin.getPassword());

        assertThat(response.getMemberId()).isEqualTo(memberDto.getMemberId());
        assertThat(response.getToken()).isNotNull();
    }
}

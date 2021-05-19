package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.exception.BadRequestException;
import com.mks.membershipservice.security.JwtTokenProvider;
import com.mks.membershipservice.vo.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public ResponseLogin login(String username, String password) {
        MemberDto member = memberService.getMemberByUsername(username);

        if (!isMatchPassword(password, member.getEncryptedPassword())) {
            throw new BadRequestException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createJwtToken(member.getUsername(), new ArrayList<>());
        return new ResponseLogin(member.getMemberId(), token);
    }

    private boolean isMatchPassword(String password, String memberEncryptedPassword) {
        return passwordEncoder.matches(password, memberEncryptedPassword);
    }
}

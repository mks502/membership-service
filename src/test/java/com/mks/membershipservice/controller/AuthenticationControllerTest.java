package com.mks.membershipservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.service.MemberService;
import com.mks.membershipservice.vo.RequestLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    private static MemberDto memberDto;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setup(){
        memberDto = new MemberDto();
        memberDto.setUsername("hkd@gmail.com");
        memberDto.setName("홍길동");
        memberDto.setPassword("12345678");
        memberDto = memberService.createMember(memberDto);
    }

    @Test
    @DisplayName("로그인을 하면 인증에 필요한 토큰과 memberId를 반환한다")
    public void 멤버_로그인() throws Exception {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setUsername("hkd@gmail.com");
        requestLogin.setPassword("12345678");

        ResultActions actions = mockMvc.perform(post("/api/login")
                .content(objectMapper.writeValueAsString(requestLogin))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("memberId").value(memberDto.getMemberId()))
                .andDo(print());
    }
}

package com.mks.membershipservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mks.membershipservice.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("멤버 컨트롤러를 통해 새로운 멤버를 등록한다.")
    public void 새로운_멤버_등록() throws Exception {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("mks502@naver.com");
        memberDto.setName("마규석");
        memberDto.setPassword("12345678");
        memberDto.setEncryptedPassword("encryptedPassword");

        mockMvc.perform(post("/api/users")
                .content(objectMapper.writeValueAsString(memberDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").exists())
                .andExpect(jsonPath("$.email", is("mks502@naver.com")))
                .andExpect(jsonPath("$.password", is("12345678")))
                .andExpect(jsonPath("$.name", is("마규석")))

                .andDo(print());
    }
}

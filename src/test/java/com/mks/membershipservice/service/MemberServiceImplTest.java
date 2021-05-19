package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    private static MemberDto memberDto;
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
    @DisplayName("새로운 멤버를 등록한다")
    public void 새로운_멤버_등록() throws Exception {
        MemberDto member = new MemberDto();
        member.setUsername("mks502@naver.com");
        member.setName("마규석");
        member.setPassword("12345678");

        MemberDto createMember = memberService.createMember(member);

        assertThat(createMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(createMember.getName()).isEqualTo(member.getName());
        assertThat(createMember).isNotNull();
    }

    @Test
    @DisplayName("등록되어 있는 멤버를 조회한다.")
    public void 멤버_조회() throws Exception {
        MemberDto findMember = memberService.getMemberByMemberId(memberDto.getMemberId());

        assertThat(findMember.getMemberId()).isEqualTo(memberDto.getMemberId());
        assertThat(findMember.getUsername()).isEqualTo(memberDto.getUsername());
        assertThat(findMember.getName()).isEqualTo(memberDto.getName());
    }
}

package com.mks.membershipservice.controller;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.service.MemberService;
import com.mks.membershipservice.vo.CreateMemberRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ModelMapper mapper;

    @PostMapping("/users")
    public MemberDto createMember(@RequestBody CreateMemberRequest memberRequest) {
        MemberDto memberDto = mapper.map(memberRequest, MemberDto.class);
        return memberService.createMember(memberDto);
    }
}

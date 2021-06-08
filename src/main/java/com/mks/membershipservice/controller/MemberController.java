package com.mks.membershipservice.controller;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.service.MemberService;
import com.mks.membershipservice.vo.CreateMemberRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ModelMapper mapper;

    @PostMapping("/members")
    public ResponseEntity createMember(@Valid @RequestBody CreateMemberRequest memberRequest) {
        MemberDto memberDto = mapper.map(memberRequest, MemberDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberDto));
    }
    @GetMapping("/members/{memberId}")
    public ResponseEntity getMembers(@PathVariable Long memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMemberByMemberId(memberId));
    }
}

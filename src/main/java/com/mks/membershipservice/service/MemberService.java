package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;

public interface MemberService {
    MemberDto createMember(MemberDto memberDto);

    MemberDto getMemberByMemberId(Long memberId);

    MemberDto getMemberByEmail(String email);
}

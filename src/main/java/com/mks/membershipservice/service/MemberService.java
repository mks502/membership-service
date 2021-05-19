package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    MemberDto createMember(MemberDto memberDto);

    MemberDto getMemberByMemberId(Long memberId);

    MemberDto getMemberByUsername(String username);
}

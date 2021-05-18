package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.entity.Member;
import com.mks.membershipservice.exception.NotFoundException;
import com.mks.membershipservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public MemberDto createMember(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);

        member.setEncryptedPassword(passwordEncoder.encode(memberDto.getPassword()));
        member = memberRepository.save(member);

        return mapper.map(member,MemberDto.class);
    }

    @Override
    public MemberDto getMemberByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("Member does not exist."));
        return mapper.map(member,MemberDto.class);
    }

    @Override
    public MemberDto getMemberByEmail(String email) {
        Member member = memberRepository.findOneByEmail(email).orElseThrow(() -> new NotFoundException("Member does not exist."));
        return mapper.map(member,MemberDto.class);
    }
}

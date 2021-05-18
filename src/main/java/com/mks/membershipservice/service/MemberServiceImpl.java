package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.entity.Member;
import com.mks.membershipservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    @Override
    public MemberDto createMember(MemberDto memberDto) {
        Member memberEntity = mapper.map(memberDto, Member.class);
        memberEntity = memberRepository.save(memberEntity);
        return mapper.map(memberEntity,MemberDto.class);
    }
}

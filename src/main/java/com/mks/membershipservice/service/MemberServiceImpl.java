package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.entity.Member;
import com.mks.membershipservice.exception.BadRequestException;
import com.mks.membershipservice.exception.NotFoundException;
import com.mks.membershipservice.repository.MemberRepository;
import com.mks.membershipservice.security.JwtTokenProvider;
import com.mks.membershipservice.vo.ResponseLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto memberDto = getMemberByUsername(username);
        return new User(memberDto.getUsername(),memberDto.getEncryptedPassword(),
                true,true,true,true,new ArrayList<>());
    }

    @Override
    public MemberDto createMember(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        log.info(memberDto.toString());
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
    public MemberDto getMemberByUsername(String username) {
        Member member = memberRepository.findOneByUsername(username).orElseThrow(() -> new NotFoundException("Member does not exist."));
        return mapper.map(member,MemberDto.class);
    }
}

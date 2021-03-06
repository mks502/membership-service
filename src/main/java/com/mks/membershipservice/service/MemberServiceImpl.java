package com.mks.membershipservice.service;

import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.entity.Member;
import com.mks.membershipservice.exception.BadRequestException;
import com.mks.membershipservice.exception.NotFoundException;
import com.mks.membershipservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public MemberDto createMember(MemberDto memberDto) {
        if(isExistUsername(memberDto.getUsername())){
            throw new BadRequestException("이미 존재하는 username 입니다.");
        }

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

    @Override
    public boolean isExistUsername(String username){
        return memberRepository.findOneByUsername(username).isPresent();
    }
}

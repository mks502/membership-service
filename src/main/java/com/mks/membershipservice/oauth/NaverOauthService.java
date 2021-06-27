package com.mks.membershipservice.oauth;

import com.mks.membershipservice.adapter.NaverAdapter;
import com.mks.membershipservice.config.NaverProperties;
import com.mks.membershipservice.dto.MemberDto;
import com.mks.membershipservice.dto.NaverUserDto;
import com.mks.membershipservice.dto.OauthDto;
import com.mks.membershipservice.entity.Member;
import com.mks.membershipservice.exception.UnauthorizedException;
import com.mks.membershipservice.repository.MemberRepository;
import com.mks.membershipservice.security.JwtTokenProvider;
import com.mks.membershipservice.dto.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class NaverOauthService implements OauthService {
    private final NaverAdapter naverAdapter;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final NaverProperties naverProperties;

    @Override
    @Transactional
    public ResponseLogin getOrCreateMember(OauthDto oauthDto) throws Exception {
        String authorizationCode = oauthDto.getAuthorizationCode();

        String accessToken = getOauthAccessCode(authorizationCode);

        final NaverUserDto naverUserDto = naverAdapter.getUserInfo(accessToken);

        if (naverUserDto == null) {
            throw new UnauthorizedException("Failed to get user info from naver api");
        }

        String naverId = naverUserDto.getId().toString();
        String profileImgUrl = naverUserDto.getProfileImage();
        String name = naverUserDto.getUserName();

        Member member = memberRepository.findOneByUsername(naverId).orElseGet(() -> {
            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(naverId);
            memberDto.setName(name);
            memberDto.setPassword(naverProperties.getPassword());
            memberDto.setEncryptedPassword(passwordEncoder.encode(naverProperties.getPassword()));

            Member m = mapper.map(memberDto, Member.class);
            return memberRepository.save(m);
        });

        String token = jwtTokenProvider.createJwtToken(member.getUsername(), new ArrayList<>());

        return new ResponseLogin(member.getMemberId(), token);
    }

    @Override
    public String getOauthAccessCode(String authorizationCode) throws Exception {
        return naverAdapter.getAccessToken(authorizationCode);
    }

    @Override
    public OauthType getOauthType() {
        return OauthType.NAVER;
    }

}

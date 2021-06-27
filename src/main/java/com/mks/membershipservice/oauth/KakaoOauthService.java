package com.mks.membershipservice.oauth;

import com.mks.membershipservice.adapter.KakaoAdapter;
import com.mks.membershipservice.config.KakaoProperties;
import com.mks.membershipservice.dto.KakaoUserDto;
import com.mks.membershipservice.dto.MemberDto;
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
public class KakaoOauthService implements OauthService {
    private final KakaoAdapter kakaoAdapter;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final KakaoProperties kakaoProperties;

    @Override
    @Transactional
    public ResponseLogin getOrCreateMember(OauthDto oauthDto) throws Exception {
        String authorizationCode = oauthDto.getAuthorizationCode();
        String accessToken = getOauthAccessCode(authorizationCode);

        final KakaoUserDto kakaoUserDto = kakaoAdapter.getUserInfo(accessToken);

        if (kakaoUserDto == null) {
            throw new UnauthorizedException("Failed to get user info from kakao api");
        }

        String kakaoId = kakaoUserDto.getId().toString();
        String profileImgUrl = kakaoUserDto.getProfileImage();
        String name = kakaoUserDto.getUserName();

        Member member = memberRepository.findOneByUsername(kakaoId).orElseGet(() -> {
            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(kakaoId);
            memberDto.setName(name);
            memberDto.setPassword(kakaoProperties.getPassword());
            memberDto.setEncryptedPassword(passwordEncoder.encode(kakaoProperties.getPassword()));

            Member m = mapper.map(memberDto, Member.class);
            return memberRepository.save(m);
        });

        String token = jwtTokenProvider.createJwtToken(member.getUsername(), new ArrayList<>());

        return new ResponseLogin(member.getMemberId(), token);
    }

    @Override
    public String getOauthAccessCode(String authorizationCode) {
        return kakaoAdapter.getAccessToken(authorizationCode);
    }

    @Override
    public OauthType getOauthType() {
        return OauthType.KAKAO;
    }

}

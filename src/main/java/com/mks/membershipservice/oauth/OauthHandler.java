package com.mks.membershipservice.oauth;

import com.mks.membershipservice.adapter.KakaoAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthHandler {
    private final OauthService oauthService;
    private final KakaoAdapter kakaoAdapter;

    @GetMapping("/login/kakao")
    public ResponseEntity kakaoLoginHandler(String code) throws Exception {
        String kakaoAccessToken = getKakaoAccessToken(code);
        return ResponseEntity.status(HttpStatus.OK).body(oauthService.getOrCreateMember(kakaoAccessToken));
    }

    private String getKakaoAccessToken(String code) throws Exception {
        String kakaoAccessToken = kakaoAdapter.getAccessToken(code);
        return kakaoAccessToken;
    }

}

package com.mks.membershipservice.oauth;

import com.mks.membershipservice.dto.OauthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OauthHandler {

    private final OauthServiceFactory oauthServiceFactory;

    @GetMapping("/login/{provider}")
    public ResponseEntity oauthLoginHandler(@RequestParam(value = "code") String code, @PathVariable String provider) throws Exception {
        log.info("oauth 로그인 성공시 인증완료 코드값 : "+ code);
        OauthType oauthType = OauthType.valueOf(provider.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK).body(oauthServiceFactory.getOauthService(oauthType).getOrCreateMember(new OauthDto(code)));
    }

}

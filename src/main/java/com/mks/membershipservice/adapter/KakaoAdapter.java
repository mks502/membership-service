package com.mks.membershipservice.adapter;

import com.mks.membershipservice.config.KakaoProperties;
import com.mks.membershipservice.dto.KakaoUserDto;
import com.mks.membershipservice.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoAdapter {

	private final RestTemplate restTemplate;
	private final KakaoProperties kakaoProperties;

    // 카카오 로그인을 통해 인가받은 코드로 access_token을 발급 받는다.
    public String getAccessToken(String code) throws Exception {
        String accessToken = "";
        String restApiKey = kakaoProperties.getRestApiKey();
        String redirectURI = kakaoProperties.getRedirectUrl();
        String kakaoAuthUrl = kakaoProperties.getKakaoAuthUrl();

        RestTemplate restTemplate = new RestTemplate();
        String reqUrl = "/oauth/token";
        URI uri = URI.create(kakaoAuthUrl + reqUrl);

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", restApiKey);
        parameters.set("redirect_uri", redirectURI);
        parameters.set("code", code);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        accessToken = (String) responseBody.get("access_token");
        log.info("kakao accessToken : " + accessToken);
        return accessToken;
    }

    // 토큰을 이용해 사용자 정보 가져오기
    public KakaoUserDto getUserInfo(String kakaoToken) {
        log.info("kakao accessToken :"+ kakaoToken);
        final URI requestUrl = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me")
                .build(true)
                .toUri();
        
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + kakaoToken);

        final HttpEntity httpEntity = new HttpEntity(httpHeaders);
        final ResponseEntity<KakaoUserDto> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, KakaoUserDto.class);
        log.info("카카오 로그인 상태 코드 : "+responseEntity.getStatusCode());
        if (! responseEntity.getStatusCode().equals( HttpStatus.OK) ) {
            throw new UnauthorizedException("Failed to get User Info from kakao");
        }
        return responseEntity.getBody();
    }
}

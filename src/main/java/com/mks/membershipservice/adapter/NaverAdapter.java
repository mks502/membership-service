package com.mks.membershipservice.adapter;

import com.mks.membershipservice.config.NaverProperties;
import com.mks.membershipservice.dto.NaverUserDto;
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
public class NaverAdapter {

    private final RestTemplate restTemplate;
    private final NaverProperties naverProperties;

    // 네이버 로그인을 통해 인가받은 코드로 access_token을 발급 받는다.
    public String getAccessToken(String authorizationCode) {
        String accessToken = "";
        String restApiKey = naverProperties.getRestApiKey();
        String redirectURI = naverProperties.getRedirectUrl();
        String naverAuthUrl = naverProperties.getAuthUrl();
        String client_secret = naverProperties.getClientSecret();

        RestTemplate restTemplate = new RestTemplate();
        String reqUrl = "/oauth2.0/token";
        URI uri = URI.create(naverAuthUrl + reqUrl);

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", restApiKey);
        parameters.set("client_secret", client_secret);
        parameters.set("redirect_uri", redirectURI);
        parameters.set("code", authorizationCode);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        accessToken = (String) responseBody.get("access_token");
        log.info("naver accessToken : " + accessToken);
        return accessToken;
    }

    // 토큰을 이용해 사용자 정보 가져오기
    public NaverUserDto getUserInfo(String naverToken) {
        log.info("naver accessToken :" + naverToken);
        final URI requestUrl = UriComponentsBuilder.fromHttpUrl("https://openapi.naver.com/v1/nid/me")
                .build(true)
                .toUri();

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + naverToken);

        final HttpEntity httpEntity = new HttpEntity(httpHeaders);
        final ResponseEntity<NaverUserDto> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, NaverUserDto.class);
        log.info("네이버 로그인 상태 코드 : " + responseEntity.getStatusCode());
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new UnauthorizedException("Failed to get User Info from Naver");
        }
        return responseEntity.getBody();
    }

}
